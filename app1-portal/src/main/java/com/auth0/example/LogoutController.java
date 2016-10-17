package com.auth0.example;

import com.auth0.NonceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(LogoutController.class);

    protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        logger.debug("LogoutController");
        if (req.getSession() != null) {
            req.getSession().invalidate();
        }
        NonceUtils.removeNonceFromStorage(req);
        res.sendRedirect(getServletConfig().getInitParameter("on_logout_redirect_to"));
    }

}
