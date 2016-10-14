<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/css/jquery.growl.css"/>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap-theme.css">
    <link rel="stylesheet" type="text/css" href="/css/signin.css">
    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="/js/jquery.growl.js" type="text/javascript"></script>
    <script src="http://cdn.auth0.com/w2/auth0-6.8.js"></script>
</head>
<body>
<div class="container">
    <script type="text/javascript">
        // hide the page in case there is an SSO session (to avoid flickering)
        $('body').hide();
    </script>
    <div class="container">
        <div class="form-signin">
            <h2 class="form-signin-heading">Portal Login</h2>
            <label for="email" class="sr-only">Email address</label>
            <input type="email" id="email" class="form-control" placeholder="Email address" required="" autofocus="">
            <label for="password" class="sr-only">Password</label>
            <input type="password" id="password" class="form-control" placeholder="Password" required="">
            <button id="signin-db" class="btn btn-lg btn-primary btn-block">Sign in</button>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            var auth0 = new Auth0({
                domain: '${domain}',
                clientID: '${clientId}',
                callbackURL: '${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, '')}${loginCallback}'
            });
            auth0.getSSOData(function (err, data) {
                if (data && data.sso === true) {
                    // have SSO session
                    console.log('SSO: an Auth0 SSO session already exists');
                    <c:choose>
                        <c:when test="${authenticated}">
                               var loggedIn = true;
                               var loggedInUserId = '${user.userId}';
                        </c:when>
                        <c:otherwise>
                                var loggedIn = false;
                                var loggedInUserId = '';
                        </c:otherwise>
                    </c:choose>
                    if (!loggedIn || (loggedInUserId !== data.lastUsedUserID)) {
                        // have SSO session but no valid local session - auto-login user
                        auth0.login({
                            scope: 'openid name email picture',
                            state: '${state}',
                            connection: '${connection}'
                        }, function (err) {
                            // this only gets called if there was a login error
                            console.error('Portal Auto LoginController Error: ' + err);
                        });
                    } else {
                        // have SSO session and valid user - send to successfully authenticated landing page
                        window.location = '${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, '')}${loginRedirectOnSuccess}';
                    }
                } else {
                    <c:choose>
                        <c:when test="${authenticated}">
                            console.log('No SSO session but authenticated locally');
                            // have local session (autenticated locally), but no SSO session exists so log them out locally too - will force login page
                            window.location = '${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, '')}${logoutEndpoint}';
                        </c:when>
                        <c:otherwise>
                            // have no SSO session and are not authenticated locally - present Lock widget LoginController box
                            $('body').show();
                            $.growl({title: "Welcome!", message: "Please log in"});
                            $('#signin-db').on('click', function() {
                                auth0.login({
                                    connection: '${connection}',
                                    username: $('#email').val(),
                                    password: $('#password').val(),
                                    sso: true,
                                    // change scopes to whatever you like
                                    // claims are added to JWT id_token - openid profile gives everything
                                    scope: 'openid roles user_id name nickname email picture',
                                    state: '${state}'
                                }, function (err) {
                                    // this only gets called if there was a login error
                                    console.error('Portal LoginController Error: ' + err);
                                });
                            });

                        </c:otherwise>
                    </c:choose>
                }
            });
        });
    </script>
</div>
</body>
</html>
