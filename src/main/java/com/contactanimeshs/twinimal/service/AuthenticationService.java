package com.contactanimeshs.twinimal.service;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Animesh (@contactanimeshs)
 */

public interface AuthenticationService {

    void manageRouting(HttpServletRequest request, HttpServletResponse response) throws IOException;
    String login(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException;
    void logout(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
