<%-- 
    Document   : login
    Created on : Dec 17, 2016, 11:25:01 PM
    Author     : NoneDark
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.*, docaccess.*" %>
<%@page import="docaccess.DocAccessInterfaceRemote.UserSession"%>
<%@page import="docaccess.DocAccessInterfaceRemote.UserException"%>
<%!
    DocAccessInterfaceRemote ejbRef = null;
%>
<%
    InitialContext ic = null;
    ic = new InitialContext();
    ejbRef =(DocAccessInterfaceRemote)ic.lookup("docaccess.DocAccessInterfaceRemote");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <%
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UserSession ses = ejbRef.authorizeUser(username, password);
            Cookie cookie = new Cookie("session", ses.getSessionToken());
            cookie.setMaxAge(60*60*24);
            response.addCookie(cookie);
            response.sendRedirect("index.jsp");
        %>
        <c:redirect url="index.jsp"/>
        <h1>Hello World!</h1>
    </body>
</html>
