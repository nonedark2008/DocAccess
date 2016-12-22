<%-- 
    Document   : index
    Created on : Dec 17, 2016, 10:45:11 PM
    Author     : NoneDark
--%>

<%@page import="javax.naming.*, docaccess.*" %>
<%@page import="docaccess.DocAccessInterfaceRemote.UserSession"%>
<%@page import="docaccess.DocAccessInterfaceRemote.UserException"%>
<%@ page errorPage="error.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="jquery-1.11.0.min.js"></script>
<script src="docaccess_js.js"></script>

<%!
    DocAccessInterfaceRemote ejbRef = null;
%>
<%
    InitialContext ic = null;
    Cookie[] cookies = request.getCookies();
    Cookie c = null;
    UserSession ses = null;
    int i;
    String disableLoginField = "";
    String disableLoginButton = "";
    String username=null;
    for (i = 0; i < cookies.length; ++i) {
        if (cookies[i].getName().equals("session")) {
            c = cookies[i];
            break;
        }
    }

    if (c != null) {
        ic = new InitialContext();
        ejbRef =(DocAccessInterfaceRemote)ic.lookup("docaccess.DocAccessInterfaceRemote");
        out.println("Session cookie: " + c.getValue());
        ses = new UserSession(); 
        ses.setSessionToken(c.getValue());
        try {
            username = ejbRef.getUsername(ses);
            disableLoginButton = "disabled";
            disableLoginField = "disabled=\"disable\"";
        } catch(UserException ex) {
            ses = null;
        }
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body onload="">
       
    <form name="login_form" method="post" action="login.jsp">
         <div class="container">
            <label><b>Username</b></label>
            <input type="text" placeholder="Enter Username" name="username"
                   <%if (ses != null) out.print("value='"+username+"'"); %>
                   required <%=disableLoginField%>>

            <label><b>Password</b></label>
            <input type="password" placeholder="Enter Password" name="password" required <%=disableLoginField%>>

            <button type="submit" <%=disableLoginButton%>>Login</button>
            <% 
                if (ses != null) {
                    out.println("<input type='button' value='Logout' onclick='logout()'>");
                    out.println("");
                }
            %>
            <input type="button" value="Register" onclick="post('newuser.jsp', {session:getCookie('session')})">
          </div> 
    </form>
    <div class="container">
    <% 
        if (ses != null) {
            out.print("<p><div id='docOwner'>Lock owner: " + ejbRef.getDocOwner(ses) + "</div>");
            out.println("<input type='button' value='Lock' onclick='lockDoc()'>");
            out.println("<input type='button' value='Unlock' onclick='unlockDoc()'>");
            out.println("<input type='button' value='Reload' onclick='reloadDoc()'>");
            out.println("<input type='button' value='Save' onclick='saveDoc()'>");
        }
    %>
    </div>
    <div id="result" style="display: none"></div>
    
    <textarea id="DocData" rows="4" cols="50"><%if (ses != null) {out.print(ejbRef.getDocument(ses));}%></textarea> 
        <h1>Hello World!</h1>
    </body>
</html>
