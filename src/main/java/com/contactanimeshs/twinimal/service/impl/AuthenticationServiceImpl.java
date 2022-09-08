package com.contactanimeshs.twinimal.service.impl;

import com.contactanimeshs.twinimal.constants.TwitterConstants;
import com.contactanimeshs.twinimal.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Animesh (@contactanimeshs)
 */

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    Environment environment;

    @Override
    public void manageRouting(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("Welcome to Twinimal!");

        if (null == request.getSession().getAttribute("state") && null == request.getSession().getAttribute("state") && null == request.getSession().getAttribute("bearerToken")) {
            log.info("New session. Routing to Login page");
            response.sendRedirect(request.getContextPath() + "login");
        } else {
            log.info("Already logged in. Routing to Home page");
            response.sendRedirect(request.getContextPath() + "homepage");
        }
    }

    @Override
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

        log.info("Login service - START");

        if (null != request.getSession().getAttribute("bearerToken")) {
            response.sendRedirect(request.getContextPath() + "homepage");
            return "home";
        }

        String authUrl = formURLforTwitterOAUTH2();
        model.addAttribute("authUrl", authUrl);

        log.info("Login service - END");

        return "login";
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Login service - START");
        log.info("Invalidating the session");

        request.getSession().invalidate();

        log.info("Login service - END");

        response.sendRedirect("/");
    }

    public String formURLforTwitterOAUTH2() {

        String clientId = environment.getProperty(TwitterConstants.TWITTER_CLIENT_ID_KEY);
        String state = environment.getProperty(TwitterConstants.TWITTER_CLIENT_STATE);
        String codeVerifier = environment.getProperty(TwitterConstants.TWITTER_CLIENT_CODE_VERIFIER);
        String redirectUrl = environment.getProperty(TwitterConstants.TWITTER_CLIENT_REDIRECT_URL);
        String scopes = environment.getProperty(TwitterConstants.TWITTER_CLIENT_SCOPES);

        return environment.getProperty(TwitterConstants.TWITTER_CLIENT_AUTH_URL) + "&client_id=" + clientId + "&redirect_uri=" + redirectUrl + "&scope=" + scopes + "&state=" + state + "&code_challenge=" + codeVerifier + "&code_challenge_method=plain";
    }
}
