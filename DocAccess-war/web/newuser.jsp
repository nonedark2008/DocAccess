<%-- 
    Document   : newuser
    Created on : Dec 18, 2016, 1:05:00 PM
    Author     : NoneDark
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script src="jquery-1.11.0.min.js"></script>
<script src="docaccess_js.js"></script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
           <p>
           <label><b>Username</b></label>
           <input type="text" placeholder="Enter Username" id="username" required>
           </p>
           <p>
           <label><b>Password</b></label>
           <input type="password" placeholder="Enter Password" id="password" required>
           </p>
           <p>
           <label><b>Confirm password</b></label>
           <input type="password" placeholder="Repeate Password" id="password2" required>
           </p>
           <input type="button" value="Submit" onclick="submit_click()" />
        </div>
        <div id="result"></div>
    </body>
</html>

<script type="text/javascript">
    function submit_click() {
        username = $("#username").val();
        password = $("#password").val();
        password2 = $("#password2").val();
        $("#result").hide().empty();
        if (password !== password2) {
            $("#result").append("Password does not match").append(password).append(password2).fadeIn("fast").delay(2000).fadeOut("fast");
            return;
        }
        $.post("operations.jsp", {type:"register", username:username,password:password},
        function(data){
            
            if (data.indexOf("Success") !== -1 ) {
                window.location.href = "index.jsp";
                return;
            }
            $("#result").empty().append(data);
            $("#result").hide().fadeIn('fast').delay(2000).fadeOut('fast');
       });
    }
</script>
