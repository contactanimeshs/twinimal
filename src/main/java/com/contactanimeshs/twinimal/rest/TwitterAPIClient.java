package com.contactanimeshs.twinimal.rest;

import com.contactanimeshs.twinimal.model.twitter.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author Animesh (@contactanimeshs)
 */

@FeignClient(value = "token", url = "https://api.twitter.com/2/")
public interface TwitterAPIClient {

    @PostMapping("oauth2/token")
    TwitterTokenAPIResponse getAccessToken(@RequestHeader MultiValueMap<String, String> requestHeader, @RequestBody MultiValueMap<String, String> requestBody);

    @PostMapping("tweets")
    SendTweetResponse sendTweet(@RequestHeader("Authorization") String bearerToken, @RequestBody SendTweetRequest requestBody);

    @GetMapping("users/me")
    GetUsersMeAPIResponse getUserDetails(@RequestHeader("Authorization") String bearerToken);

    @GetMapping("users/{userId}")
    GetUserProfileAPIResponse getUserProfile(@RequestHeader("Authorization") String bearerToken, @PathVariable String userId, @RequestParam("user.fields") String userFields);

    @GetMapping("tweets/search/recent")
    GetPreviousTweetsResponse getTweets(@RequestHeader("Authorization") String bearerToken, @RequestParam("query") String query, @RequestParam("max_results") String maxResult, @RequestParam("tweet.fields") String tweetFields);

    @GetMapping("tweets/search/recent")
    GetPreviousTweetsResponse getTweetsByPaginationToken(@RequestHeader("Authorization") String bearerToken, @RequestParam("query") String query, @RequestParam("max_results") String maxResult, @RequestParam("tweet.fields") String tweetFields, @RequestParam(value = "next_token", required = false) String nextToken);

    @DeleteMapping("tweets/{tweetId}")
    DeleteTweetResponse deleteTweet(@RequestHeader("Authorization") String bearerToken, @PathVariable String tweetId);

    @GetMapping("users/{userId}/bookmarks")
    GetBookmarksResponse getBookmarks(@RequestHeader("Authorization") String bearerToken, @PathVariable String userId, @RequestParam("tweet.fields") String tweetFields, @RequestParam("user.fields") String userFields, @RequestParam("max_results") String maxResult);

    @GetMapping("users/{userId}/bookmarks")
    Object addBookmark(@RequestHeader("Authorization") String bearerToken, @PathVariable String userId, @RequestBody AddRemoveBookmarkRequestBody requestBody);

    @DeleteMapping("users/{userId}/bookmarks/{tweetId}")
    Object removeBookmark(@RequestHeader("Authorization") String bearerToken, @PathVariable String userId, @PathVariable String tweetId);
}
