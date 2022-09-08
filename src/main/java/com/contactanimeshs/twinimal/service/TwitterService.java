package com.contactanimeshs.twinimal.service;

import com.contactanimeshs.twinimal.model.twitter.SendTweetRequest;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Animesh (@contactanimeshs)
 */

public interface TwitterService {

    String fetchAccessToken(String code);
    void createHomeContent(HttpServletRequest request, HttpServletResponse response, Model model);

    void createBookmarkContent(HttpServletRequest request, HttpServletResponse response, Model model);

    void createTweet(HttpServletRequest request, HttpServletResponse response, SendTweetRequest sendTweetRequest, Model model) throws IOException;
    void deleteTweetById(HttpServletRequest request, HttpServletResponse response, Model model, String tweetId) throws IOException ;

    void deleteBookmarkById(HttpServletRequest request, HttpServletResponse response, Model model, String tweetId);
}
