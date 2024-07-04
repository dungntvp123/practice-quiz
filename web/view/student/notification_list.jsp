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
    Vector<Notification> notifications = (Vector<Notification>) request.getAttribute("notifications");
    entity.Class targetClass = (entity.Class) request.getAttribute("targetClass");
%>
<!DOCTYPE html>
<html lang="en" xmlns:x-transition="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Notification List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">
</head>
<body>
<jsp:include page="/view/partials/navbar.jsp"></jsp:include>
    <div class="w-full overflow-x-hidden border-t flex flex-col">
        <main class="w-full flex-grow p-6">
            <h1 class="text-4xl text-black pb-6">Latest Notifications of Class <%=targetClass.getName()%></h1>
            <ol class="list-decimal">
                <%for (Notification notification : notifications) {%>
                <li><a href="${pageContext.request.contextPath}/user/notification?id=<%=notification.getId()%>"
                       class="no-underline hover:underline text-blue-700"><%=notification.getTitle()%> (<%=notification.getTimeOfPublish()%>)</a>
                </li>
                <%}%>
            </ol>
        </main>
    </div>
</body>
</div>
</html>
