<%-- 
    Document   : error
    Created on : Dec 18, 2016, 1:30:29 AM
    Author     : NoneDark
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>There was an error</h1>
        <p style="color: red">${pageContext.exception.message}</p>
        <p><a href="index.jsp">Return</a></p>
    </body>
</html>
