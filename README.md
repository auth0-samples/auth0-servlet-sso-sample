## Using Auth0 with Servlets and JSP technology for Single Sign On (SSO) between two websites

This is a companion sample for the [Auth0 Servlet](https://github.com/auth0/auth0-servlet) library.
Please refer to that library and documentation for further information specific to the library itself.

Perhaps the best way to learn how to use this library is to study the [Auth0 Servlet Sample](https://github.com/auth0-samples/auth0-servlet-sample) source code together with its README file. The contents of this sample build upon the Auth0 Servlet Sample.

This Auth0 Servlet SSO Sample uses plain Java Servlet / JSP technology to demonstrate Single Sign-On (SSO). The sample consists of two demo websites that have been developed to demonstrate the Auth0 SSO capabilities between a main "portal" website and a Partner website that depends on the main "portal" site for SSO authentication.

### Contents

The included demo websites are:

app1.com - main "portal" website

app2.com - partner website

The aim of this solution is to provide simple, no-frills sample developers can follow to understand the orchestration
required to achieve SSO using Auth0 using Java, without having to also cope with understanding additional libraries or frameworks.

#### Sequence Diagrams

* [New SSO Session Flow](sequence-diagrams/new-sso-session.md)
* [Existing SSO Session Flow](sequence-diagrams/existing-sso-session.md)


### Setup

Create an [Auth0 Account](https://auth0.com) (if not already done so - free!).


#### From the Auth0 Dashboard

Create an application - for the purposes of this sample - `app1`
This will represent the Portal site

Ensure you add the following to the settings.

Allowed Callback URLs:

```
http://localhost:3099/callback
http://app1.com:3099/callback
```

Ensure you add the following to the settings.

Allowed Logout URLs:

```
http://localhost:3099/logout
http://app1.com:3099/logout
```

Ensure you switch ON the following setting.

`Use Auth0 instead of the IdP to do Single Sign On`



Create an application - for the purposes of this sample - `app2`
This will represent the Partner site

Ensure you add the following to the settings.

Allowed Callback URLs:

```
http://localhost:4000/callback
http://app2.com:4000/callback
```

Ensure you add the following to the settings.

Allowed Logout URLs:

```
http://localhost:4000/logout
http://app2.com:4000/logout
```

Ensure you switch ON the following setting.

`Use Auth0 instead of the IdP to do Single Sign On`


Define a `database connection` and ensure app1 and app2 both reference this `connnection`


Go to `Users` and select `CREATE USER`

Create a user with a username/password of your choosing, and ensure the database connection defined above is
associated with that user.

OPTIONAL: Setup a Social Connection, for example, Google, and ensure app1 and app2 both reference this social connection.


Ok, that's all the Auth0 Dashboard setup done!


#### Update your host's file

Update your `hosts` file so you can reference the apps by name, rather than localhost / 127.0.0.1

```
127.0.0.1    app1.com
127.0.0.1    app2.com
```


---

## License

The MIT License (MIT)

Copyright (c) 2016 AUTH10 LLC

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
