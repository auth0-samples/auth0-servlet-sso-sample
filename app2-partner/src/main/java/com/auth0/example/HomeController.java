package com.auth0.example;

import com.auth0.NonceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeController extends BaseController {

    private static final Logger logger = LogManager.getLogger(HomeController.class);

    protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        logger.info("HomeController page");
        // add Nonce to storage
        NonceUtils.addNonceToStorage(req);
        setupRequest(req);
        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, res);
    }

}
