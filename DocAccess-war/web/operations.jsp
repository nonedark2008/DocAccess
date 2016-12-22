<%@page import="javax.naming.*, docaccess.*" %>
<%@page import="docaccess.DocAccessInterfaceRemote.UserSession"%>
<%@page import="docaccess.DocAccessInterfaceRemote.UserException"%>
<%@ page errorPage="error.jsp"%>
<%!
    DocAccessInterfaceRemote ejbRef = null;
%>
<%
    InitialContext ic = null;
    ic = new InitialContext();
    ejbRef =(DocAccessInterfaceRemote)ic.lookup("docaccess.DocAccessInterfaceRemote");
%>
<%
    String sesToken = request.getParameter("session");
    UserSession ses = new UserSession(); ses.setSessionToken(sesToken);
    
    String t = request.getParameter("type");
    if (t.equals("save")) {
        ejbRef.saveDocument(ses, request.getParameter("data"));
        out.print("<h1>Saved</h1>");
    } else if (t.equals("lock")) {
        ejbRef.setDocumentLock(ses, true);
    } else if (t.equals("unlock")) {
        ejbRef.setDocumentLock(ses, false);
    } else if (t.equals("reload")) {
        out.print(ejbRef.getDocument(ses));
    } else if (t.equals("register")) {
        ejbRef.createUser(request.getParameter("username"), request.getParameter("password"));
        out.print("Success");
    } else if (t.equals("docowner")) {
        out.print(ejbRef.getDocOwner(ses));
    }
%>