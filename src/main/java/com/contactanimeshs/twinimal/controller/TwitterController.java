package com.contactanimeshs.twinimal.controller;

import com.contactanimeshs.twinimal.model.twitter.SendTweetRequest;
import com.contactanimeshs.twinimal.service.TwitterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Animesh (@contactanimeshs)
 *
 * This controller handles the endpoints responsible for the functionality and UI of Twinimal like:
 * Create a new tweet, view previous tweets, delete a tweet etc.
 */

@Slf4j
@CrossOrigin(origins = { "http://127.0.0.1:3000" }, allowedHeaders = {"*"}, allowCredentials = "true", exposedHeaders = { "Set-Cookie" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.DELETE })
@Controller
public class TwitterController {

    @Autowired
    TwitterService twitterService;

    @GetMapping("/homepage")
    public void homepage(@RequestParam(value = "state", required = false) String state, @RequestParam(value = "code", required = false) String code, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (null == state && null == code) {
            response.sendRedirect(request.getContextPath() + "login");
        } else {
            request.getSession().setAttribute("code", code);
            request.getSession().setAttribute("state", state);
            response.sendRedirect(request.getContextPath() + "home");
        }
    }

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (null == request.getSession().getAttribute("state") || null == request.getSession().getAttribute("code")) {
            response.sendRedirect(request.getContextPath() + "login");
            return "login";
        }

        twitterService.createHomeContent(request, response, model);
        return "home";
    }

    @GetMapping("/bookmarks")
    public String bookmarks(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (null == request.getSession().getAttribute("state") || null == request.getSession().getAttribute("code")) {
            response.sendRedirect(request.getContextPath() + "login");
            return "login";
        }

        twitterService.createBookmarkContent(request, response, model);
        return "bookmarks";
    }



    @PostMapping("/tweet")
    public String createTweet(HttpServletRequest request, HttpServletResponse response, SendTweetRequest sendTweetRequest, Model model) throws IOException {

        if (null == request.getSession().getAttribute("state") && null == request.getSession().getAttribute("state") && null == request.getSession().getAttribute("bearerToken")) {
            response.sendRedirect(request.getContextPath() + "login");
            return "login";
        }

        twitterService.createTweet(request, response, sendTweetRequest, model);
        response.sendRedirect(request.getContextPath() + "home");
        return "home";
    }

    @PostMapping("/delete")
    public String deleteTweet(HttpServletRequest request, HttpServletResponse response, Model model, String tweetId) throws IOException {

        if (null == request.getSession().getAttribute("state") && null == request.getSession().getAttribute("code") && null == request.getSession().getAttribute("bearerToken")) {
            response.sendRedirect(request.getContextPath() + "login");
            return "login";
        }

        twitterService.deleteTweetById(request, response, model, tweetId);
        response.sendRedirect(request.getContextPath() + "home");
        return "home";
    }

    @PostMapping("/deletebookmark")
    public String deleteBookmark(HttpServletRequest request, HttpServletResponse response, Model model, String tweetId) throws IOException {

        if (null == request.getSession().getAttribute("state") && null == request.getSession().getAttribute("code") && null == request.getSession().getAttribute("bearerToken")) {
            response.sendRedirect(request.getContextPath() + "login");
            return "login";
        }

        twitterService.deleteBookmarkById(request, response, model, tweetId);
        response.sendRedirect(request.getContextPath() + "bookmarks");
        return "bookmarks";
    }
}