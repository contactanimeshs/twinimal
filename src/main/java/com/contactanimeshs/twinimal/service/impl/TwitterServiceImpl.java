package com.contactanimeshs.twinimal.service.impl;

import com.contactanimeshs.twinimal.constants.TwitterConstants;
import com.contactanimeshs.twinimal.rest.TwitterAPIClient;
import com.contactanimeshs.twinimal.service.TwitterService;
import com.contactanimeshs.twinimal.model.twitter.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Animesh (@contactanimeshs)
 */

@Slf4j
@Service
public class TwitterServiceImpl implements TwitterService {

    @Autowired
    Environment environment;

    @Autowired
    TwitterAPIClient restClient;

    @Override
    public String fetchAccessToken(String code) {

        String clientId = environment.getProperty(TwitterConstants.TWITTER_CLIENT_ID_KEY);
        String clientSecret = environment.getProperty(TwitterConstants.TWITTER_CLIENT_SECRET_KEY);
        String redirectUrl = environment.getProperty(TwitterConstants.TWITTER_CLIENT_REDIRECT_URL);
        String codeVerifier = environment.getProperty(TwitterConstants.TWITTER_CLIENT_CODE_VERIFIER);

        String clientCredentialsString = clientId + ":" + clientSecret;
        String base64encoded = Base64.getEncoder().encodeToString(clientCredentialsString.getBytes());

        String authorizationToken = "Basic " + base64encoded;
        String contentType = "application/x-www-form-urlencoded";
        String grantType = "authorization_code";

        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

        bodyValues.add("code", code);
        bodyValues.add("grant_type", grantType);
        bodyValues.add("code_verifier", codeVerifier);
        bodyValues.add("client_id", clientId);
        bodyValues.add("redirect_uri", redirectUrl);

        MultiValueMap<String, String> headerValues = new LinkedMultiValueMap<>();
        headerValues.add("Authorization", authorizationToken);
        headerValues.add("Content-Type", contentType);

        TwitterTokenAPIResponse tokenAPIResponse = restClient.getAccessToken(headerValues, bodyValues);

        return "Bearer " + tokenAPIResponse.getAccess_token();
    }

    @Override
    public void createHomeContent(HttpServletRequest request, HttpServletResponse response, Model model) {

        String bearerToken;

        if (null == request.getSession().getAttribute("bearerToken")) {
            String code = request.getSession().getAttribute("code").toString();

            bearerToken = fetchAccessToken(code);

            request.getSession().setAttribute("bearerToken", bearerToken);
        } else {
            bearerToken = request.getSession().getAttribute("bearerToken").toString();
        }

        /* Getting user details */
        String twitterUsername = getUserDetails(bearerToken, model, request);

        /* Getting previous tweets */
        getPreviousTweets(bearerToken, twitterUsername, request, model);

    }

    @Override
    public void createBookmarkContent(HttpServletRequest request, HttpServletResponse response, Model model) {

        String bearerToken;

        if (null == request.getSession().getAttribute("bearerToken")) {
            String code = request.getSession().getAttribute("code").toString();

            bearerToken = fetchAccessToken(code);

            request.getSession().setAttribute("bearerToken", bearerToken);
        } else {
            bearerToken = request.getSession().getAttribute("bearerToken").toString();
        }

        /* Getting user details */
        String twitterUsername = getUserDetails(bearerToken, model, request);

        /* Getting bookmarked tweets */
        getBookmarkedTweets(bearerToken, twitterUsername, request, model);
    }

    @Override
    public void createTweet(HttpServletRequest request, HttpServletResponse response, SendTweetRequest sendTweetRequest, Model model) {

        log.info("Creating a new tweet: " + sendTweetRequest.getText());
        String bearerToken = request.getSession().getAttribute("bearerToken").toString();
        SendTweetResponse tweetResponse = restClient.sendTweet(bearerToken, sendTweetRequest);
        model.addAttribute("tweet", tweetResponse);

        /* Adding text & id of recently tweeted tweet to the session after it was successfully posted for our custom logic below */
        request.getSession().setAttribute("recenttweet", tweetResponse.getData().getText());
        request.getSession().setAttribute("recenttweetid", tweetResponse.getData().getId());
    }

    @Override
    public void deleteTweetById(HttpServletRequest request, HttpServletResponse response, Model model, String tweetId) {
        String bearerToken = request.getSession().getAttribute("bearerToken").toString();
        DeleteTweetResponse deleteTweetResponse = restClient.deleteTweet(bearerToken, tweetId);

        log.info("Tweet successfully deleted: " + deleteTweetResponse.getData());
    }

    private String getUserDetails(String bearerToken, Model model, HttpServletRequest request) {

        GetUsersMeAPIResponse userDetailsResponse = restClient.getUserDetails(bearerToken);
        GetUserProfileAPIResponse userProfileAPIResponse = restClient.getUserProfile(bearerToken, userDetailsResponse.getData().getId(), "profile_image_url%2Cdescription%2Clocation%2Centities");

        model.addAttribute("username", "@" + userDetailsResponse.getData().getUsername());
        model.addAttribute("name", userDetailsResponse.getData().getName());
        model.addAttribute("photo", userProfileAPIResponse.getData().getProfile_image_url());

        String twitterUsername = userDetailsResponse.getData().getUsername();
        String twitterDisplayName = userDetailsResponse.getData().getName();
        String twitterUserId = userDetailsResponse.getData().getId();

        /* Adding user account details to the session */
        request.getSession().setAttribute("userId", twitterUserId);
        request.getSession().setAttribute("displayName", twitterDisplayName);
        request.getSession().setAttribute("username", "@" + twitterUsername);

        return twitterUsername;
    }

    private Model getPreviousTweets(String bearerToken, String twitterUsername, HttpServletRequest request, Model model) {

        GetPreviousTweetsResponse previousTweets = restClient.getTweets(bearerToken, "from:" + twitterUsername, "100", "created_at");

        /*
         * This custom logic is written because Twitter v2 API Search is delayed by 10 seconds.
         * Hence, it fails to reflect recently posted tweets until 10 seconds
         * */

        List<DataFromPreviousTweetsAPI> previousTweetsData = new ArrayList<>();

        if (null != request.getSession().getAttribute("recenttweet")) {
            String recentlyPostedTweetId = request.getSession().getAttribute("recenttweetid").toString();
            String recentlyPostedTweetText = request.getSession().getAttribute("recenttweet").toString();

            if (previousTweets.getData().get(0).getId() != recentlyPostedTweetId) {
                DataFromPreviousTweetsAPI recentTweet = new DataFromPreviousTweetsAPI();
                recentTweet.setId(recentlyPostedTweetId);
                recentTweet.setText(recentlyPostedTweetText);
                previousTweetsData.add(recentTweet);

                request.getSession().removeAttribute("recenttweet");
                request.getSession().removeAttribute("recenttweetid");
            }
        }

        if (null == previousTweets) {
            model.addAttribute("previoustweets", previousTweetsData);
            return model;
        }

        /* Filtering out reply tweets & retweets*/

        if (previousTweets.getMeta().getResult_count() > 0) {
            for (DataFromPreviousTweetsAPI previousTweet : previousTweets.getData()) {
                if (!previousTweet.getText().startsWith("@") && !previousTweet.getText().startsWith("RT")) {
                    previousTweetsData.add(previousTweet);
                }
            }
        }

        model.addAttribute("previoustweets", previousTweetsData);
        return model;
    }

    private Model getBookmarkedTweets(String bearerToken, String twitterUsername, HttpServletRequest request, Model model) {


        String userId = request.getSession().getAttribute("userId").toString();

        GetBookmarksResponse response = restClient.getBookmarks(bearerToken, userId, "attachments,author_id,created_at,id,in_reply_to_user_id,text", "name,profile_image_url,username,verified", "20");

        List<GetBookmarksModel> finalResponseList = new ArrayList<>();

        List<DataInGetBookmarks> bookmarksList = response.getData();

        bookmarksList.forEach(dataInBookmark -> {

            GetBookmarksModel bookmarkDetails = new GetBookmarksModel();

            String authorId = dataInBookmark.getAuthor_id();

            bookmarkDetails.setId(dataInBookmark.getId());
            bookmarkDetails.setText(dataInBookmark.getText());
            bookmarkDetails.setAuthor_id(dataInBookmark.getAuthor_id());

            GetUserProfileAPIResponse authorProfile = restClient.getUserProfile(bearerToken, authorId, "name,profile_image_url,url,username,verified");

            bookmarkDetails.setAuthor_name(authorProfile.getData().getName());
            bookmarkDetails.setAuthor_username("@"+authorProfile.getData().getUsername());
            bookmarkDetails.setAuthor_profilepic(authorProfile.getData().getProfile_image_url());
            String tweetUrl = "https://twitter.com/"+authorProfile.getData().getUsername()+"/status/"+dataInBookmark.getId();
            bookmarkDetails.setUrl(tweetUrl);

            finalResponseList.add(bookmarkDetails);
        });

        model.addAttribute("bookmarks", finalResponseList);
        return model;
    }

    @Override
    public void deleteBookmarkById(HttpServletRequest request, HttpServletResponse response, Model model, String tweetId) {
        String bearerToken = request.getSession().getAttribute("bearerToken").toString();
        String userId = request.getSession().getAttribute("userId").toString();
        restClient.removeBookmark(bearerToken, userId, tweetId);
        log.info("Bookmark successfully deleted");
    }

}
