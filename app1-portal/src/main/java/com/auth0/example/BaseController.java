package com.auth0.example;

import com.auth0.Auth0User;
import com.auth0.SessionUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * BaseController that pulls in required attributes from web.xml
 */
public class BaseController extends HttpServlet {

    protected String domain;
    protected String clientId;
    protected String clientSecret;
    protected String loginCallback;
    protected String logoutEndpoint;
    protected String connection;
    protected String loginRedirectOnSuccess;
    protected boolean customLogin;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.domain = config.getServletContext().getInitParameter("auth0.domain");
        this.clientId = config.getServletContext().getInitParameter("auth0.client_id");
        this.clientSecret = config.getServletContext().getInitParameter("auth0.client_secret");
        this.connection = config.getServletContext().getInitParameter("auth0.connection");
        this.loginCallback = config.getServletContext().getInitParameter("auth0.login_callback");
        this.logoutEndpoint = config.getServletContext().getInitParameter("auth0.logout_endpoint");
        this.loginRedirectOnSuccess = config.getServletContext().getInitParameter("auth0.login_redirect_on_success");
        this.customLogin = Boolean.valueOf(config.getServletContext().getInitParameter("auth0.custom_login"));
    }

    /**
     * required attributes needed in request for view presentation
     */
    protected void setupRequest(final HttpServletRequest req) {
        // null if no user exists..
        final Auth0User user = SessionUtils.getAuth0User(req);
        req.setAttribute("user", user);
        req.setAttribute("domain", domain);
        req.setAttribute("clientId", clientId);
        req.setAttribute("loginCallback", loginCallback);
        req.setAttribute("loginRedirectOnSuccess", loginRedirectOnSuccess);
        req.setAttribute("state", SessionUtils.getState(req));
        req.setAttribute("logoutEndpoint", logoutEndpoint);
        req.setAttribute("connection", connection);
        req.setAttribute("authenticated",req.getAttribute("user") != null);
        req.setAttribute("customLogin", customLogin);
    }

}
