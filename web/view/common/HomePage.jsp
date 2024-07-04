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
    User currentUser = (User) request.getSession().getAttribute("user");
    Vector<Notification> notifications = (Vector<Notification>) request.getAttribute("notifications");
%>
<!DOCTYPE html>
<html lang="en" xmlns:x-transition="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">
</head>
<body>
<jsp:include page="../partials/navbar.jsp"></jsp:include>
<div class="py-4">
        <% if (currentUser == null){%>
    <h1 class="text-3xl text-black pb-6">Welcome, Guest!</h1>
    <a class="px-4 py-1 text-white font-light tracking-wider bg-gray-900 rounded"
       href="${pageContext.request.contextPath}/login">Login</a>
        <%} else if (currentUser.getRole() == User.TEACHER){ %>
    <h1 class="text-3xl text-black pb-6">Welcome, Lecture <%=currentUser.getUsername()%>!</h1>
    <a class="px-4 py-1 text-white font-light tracking-wider bg-green-400 rounded"
       href="${pageContext.request.contextPath}/teacher/subjects">Subject List</a>
    <a class="px-4 py-1 text-white font-light tracking-wider bg-green-400 rounded"
       href="${pageContext.request.contextPath}/teacher/classes">View All Classes</a>
    <a class="px-4 py-1 text-white font-light tracking-wider bg-green-400 rounded"
       href="${pageContext.request.contextPath}/teacher/bank">Question Bank</a>
        <%} else if (currentUser.getRole() == User.STUDENT){%>
    <h1 class="text-3xl text-black pb-6">Welcome, Student <%=currentUser.getUsername()%>!</h1>
    <a class="px-4 py-1 text-white font-light tracking-wider bg-green-400 rounded"
       href="${pageContext.request.contextPath}/student/subjects">Subject List</a>
    <a class="px-4 py-1 text-white font-light tracking-wider bg-green-400 rounded"
       href="${pageContext.request.contextPath}/student/classes">View All Classes</a>
    <div class="mt-5">
        <h2 class="text-2xl text-black pb-6">Latest Notification</h2>
        <ol class="list-decimal">
            <%for (Notification notification : notifications) {%>
            <li><a href="${pageContext.request.contextPath}/user/notification?id=<%=notification.getId()%>"
                   class="no-underline hover:underline text-blue-700"><%=notification.getTitle()%> (<%=notification.getTimeOfPublish()%>)</a></li>
            <%}%>
        </ol>
    </div>
        <%}%>
</body>
</div>
</html>
