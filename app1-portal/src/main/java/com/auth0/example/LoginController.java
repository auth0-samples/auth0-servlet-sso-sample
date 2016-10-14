package com.auth0.example;

import com.auth0.NonceUtils;
import com.auth0.QueryParamUtils;
import com.auth0.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginController extends BaseController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
    }

    private void removeExternalReturnUrlFromState(final HttpServletRequest req) {
        // explicitly remove any externalReturnUrl if in state storage - this is the Portal login
        // we want to ensure no implicit redirection to Partner site is possible post authentication
        final String previousState = SessionUtils.getState(req);
        final String updatedState = QueryParamUtils.removeFromQueryParams(previousState, "externalReturnUrl");
        SessionUtils.setState(req, updatedState);
    }

    protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        logger.debug("Performing login");
        // add Nonce to storage
        NonceUtils.addNonceToStorage(req);
        removeExternalReturnUrlFromState(req);
        setupRequest(req);
        // for this sample only, this just allows configuration to
        // use Lock Widget or Auth0.js for login presentation
        if (customLogin) {
            req.getRequestDispatcher("/WEB-INF/jsp/loginCustom.jsp").forward(req, res);
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, res);
        }
    }

}
