package com.auth0.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(LogoutController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("LogoutController");
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
        response.sendRedirect(getServletConfig().getInitParameter("on_logout_redirect_to"));
    }

}
