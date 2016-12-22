<%-- 
    Document   : register
    Created on : Dec 17, 2016, 11:06:30 PM
    Author     : NoneDark
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Page</title>
    </head>
    <body>
        <form action="loginprocess.jsp" method="post"> 
            <div class="container">
              <label><b>Username</b></label>
              <input type="text" placeholder="Enter Username" name="username" required>

              <label><b>Password</b></label>
              <input type="password" placeholder="Enter Password" name="password" required>

              <button type="submit">Login</button>
            </div>
        </form>
        <h1>Hello World!</h1>
    </body>
</html>
