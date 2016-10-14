package com.auth0.example;

import com.auth0.NonceUtils;
import com.auth0.QueryParamUtils;
import com.auth0.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class PartnerLoginController extends BaseController {

    private static final Logger logger = LogManager.getLogger(PartnerLoginController.class);

    protected List trustedExternalReturnUrls;

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.debug("Performing partner login");

        final String externalReturnUrl = req.getParameter("externalReturnUrl");
        if (externalReturnUrl == null) {
            res.getWriter().write("Missing required external return URL query param.");
            res.setStatus(400);
            res.flushBuffer();
            return;
        } else  if (!isTrustedExternalReturnUrl(externalReturnUrl)) {
            res.getWriter().write("Cannot redirect to untrusted URL: " + externalReturnUrl);
            res.setStatus(400);
            res.flushBuffer();
            return;
        }
        final String previousState = (SessionUtils.getState(req) != null) ? SessionUtils.getState(req) : "";
        final String updatedState = QueryParamUtils.addOrReplaceInQueryParams(previousState, "externalReturnUrl", externalReturnUrl);
        SessionUtils.setState(req, updatedState);

        // add Nonce to storage
        NonceUtils.addNonceToStorage(req);
        setupRequest(req);
        // for this sample only, this just allows configuration to
        // use Lock Widget or Auth0.js for login presentation
        if (customLogin) {
            req.getRequestDispatcher("/WEB-INF/jsp/partnerLoginCustom.jsp").forward(req, res);
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/partnerLogin.jsp").forward(req, res);
        }
    }



    protected boolean isTrustedExternalReturnUrl (final String url) {
        if (trustedExternalReturnUrls == null) {
            final String trustedExternalReturnUrlsStr = getServletContext().getInitParameter("auth0.trustedExternalReturnUrls");
            trustedExternalReturnUrls = Arrays.asList(trustedExternalReturnUrlsStr.split("\\s*,\\s*"));
        }
        return trustedExternalReturnUrls.contains(url);
    }

}

