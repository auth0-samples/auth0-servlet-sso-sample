package com.auth0.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeController extends BaseController {

    private static final Logger logger = LogManager.getLogger(HomeController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.debug("HomeController");
        setupRequest(req);
        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, res);
    }
}
