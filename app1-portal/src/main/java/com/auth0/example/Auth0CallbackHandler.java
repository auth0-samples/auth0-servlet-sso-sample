package com.auth0.example;

import com.auth0.Auth0ServletCallback;
import com.auth0.QueryParamUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Custom Auth0ServletCallback to handle SSO interaction
 * both for portal logins and handling partner site logins
 */
public class Auth0CallbackHandler extends Auth0ServletCallback {

    protected List trustedExternalReturnUrls;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        final String trustedExternalReturnUrlsStr = readParameter("auth0.trustedExternalReturnUrls", config);
        trustedExternalReturnUrls = Arrays.asList(trustedExternalReturnUrlsStr.split("\\s*,\\s*"));
    }

    @Override
    protected void onSuccess(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        final String externalReturnUrl = getExternalReturnUrl(req);
        if (externalReturnUrl != null) {
            resp.sendRedirect(externalReturnUrl);
        } else {
            resp.sendRedirect(req.getContextPath() + redirectOnSuccess);
        }
    }

    @Override
    protected void onFailure(HttpServletRequest req, HttpServletResponse res, Exception ex) throws ServletException, IOException {
        ex.printStackTrace();
        final String externalReturnUrl = getExternalReturnUrl(req);
        if (externalReturnUrl != null) {
            // redirect back to partner site
            final String redirectExternalOnFailLocation = externalReturnUrl + "?error=callbackError";
            res.sendRedirect(redirectExternalOnFailLocation);
        } else {
            // redirect back to "callback failure" location of this app
            final String redirectOnFailLocation = req.getContextPath() + this.redirectOnFail;
            res.sendRedirect(redirectOnFailLocation);
        }
    }

    @Override
    protected boolean isValidState(final HttpServletRequest req) {
        final boolean isNonceValid = super.isValidState(req);
        final String externalReturnUrl = getExternalReturnUrl(req);
        final boolean isTrustedExternalReturnUrl = (externalReturnUrl == null) ||
                trustedExternalReturnUrls.contains(externalReturnUrl);
        return isNonceValid && isTrustedExternalReturnUrl;
    }

    protected String getExternalReturnUrl(final HttpServletRequest req) {
        final String stateFromRequest = req.getParameter("state");
        if (stateFromRequest == null) {
            throw new IllegalStateException("state missing in request");
        }
        return QueryParamUtils.parseFromQueryParams(stateFromRequest, "externalReturnUrl");
    }

}
