<%-- 
    Document   : sendOTP
    Created on : May 14, 2023, 7:02:38 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OTP</title>
    </head>
    <body>

        <%String email1 = (String) request.getAttribute("email");%>

        <h1>Your OTP was sent! Please check your mail</h1>
        <div id ='body' hidden="true">
            <%@include file="../../partials/emailforgot.jsp" %>
        </div>
    </body>
</html>

<script src="https://smtpjs.com/v3/smtp.js">
</script>

<script>
    setTimeout(() => {
        Email.send({
        SecureToken : "994f3815-b54a-4486-91ba-c04b8b4308e6",
        To : "<%=email1%>",
        From : "tridungtin8@gmail.com",
        Subject : "Change Password",
        Body : document.getElementById("body").outerHTML
        }).then(
            message => alert(message)
        );    

        alert("your email was sent");
    }, 1000);
</script>
