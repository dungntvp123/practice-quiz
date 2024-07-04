<%@ page import="entity.User" %>
<%@ page import="java.util.Vector" %>
<%@ page import="entity.Notification" %>
<%@ page import="dao.NotificationDAO" %><%--
    Document   : userImg
    Created on : May 12, 2023, 11:58:14 AM
    Author     : tuann
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%
    Notification notification = (Notification) request.getAttribute("notification");
    User publisher = (User) request.getAttribute("publisher");
    entity.Class currentClass = (entity.Class) request.getAttribute("currentClass");
%>
<!DOCTYPE html>
<html lang="en" xmlns:x-transition="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Notification Detail</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">
</head>
<body>
<jsp:include page="../partials/navbar.jsp"></jsp:include>
    <div class="w-full overflow-x-hidden border-t flex flex-col">
        <main class="w-full flex-grow p-6">
            <h1 class="text-3xl text-black pb-6"><%=notification.getTitle()%></h1>
            <h4 class="text-l text-black font-bold pb-6">Class: <%=currentClass.getName()%></h4>
            <h4 class="text-l text-black font-bold pb-6">Published by: <%=publisher.getUsername()%></h4>
            <h4 class="text-l text-black font-bold pb-6">Published at: <%=notification.getTimeOfPublish()%></h4>
            <% for (String line : notification.getContent().split("\n")){%>
            <p><%=line%></p>
            <%}%>
        </main>
    </div>
</body>
</div>
</html>
