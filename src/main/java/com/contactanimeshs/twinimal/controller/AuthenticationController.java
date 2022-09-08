package com.contactanimeshs.twinimal.controller;

import com.contactanimeshs.twinimal.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Animesh (@contactanimeshs)
 */

@Slf4j
@Controller
public class AuthenticationController {

    /**
     * This controller is responsible for handling 'Login with Twitter' flow and logout and basic application routing
     * <p>
     * This manual way might not be the best way to do it, but I still haven't been able to configure Spring Security OAUTH2.0 with Twitter. After Successful Auth, it was routing me back to the Login page
     * There are no documentation or articles available for this integration unlike for other social logins like Facebook(Meta), Google or GitHub
     * <p>
     * I am working on the Spring Security flow and will update the logic if it gets successful.
     * Other than that this flow is working like charm
     */

    @Autowired
    AuthenticationService authService;

    @GetMapping("/")
    public void manageRouting(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.manageRouting(request, response);
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

        return authService.login(request, response, model);
    }

    @GetMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.logout(request, response);
    }

}
