<%-- 
    Document   : invalidCookie
    Created on : Jun 7, 2023, 10:25:33 AM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%double score = (double) request.getAttribute("score");%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    </body>
    <script>
        alert('Preview Result: <%=score%>/10.0');
        let cookies = decodeURIComponent(document.cookie).split(";");

        cookies.forEach(x => {
            x = x.trim();
            document.cookie = x + "; expires=Thu, 18 Dec 2013 12:00:00 UTC; path=/";
        });
        console.log(decodeURIComponent(document.cookie));
        window.location.replace("<%=request.getContextPath()%>/mainpage");
    </script>
</html>
