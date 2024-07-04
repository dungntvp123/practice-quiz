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
    entity.Class targetClass = (entity.Class) request.getAttribute("targetClass");
    User user = (User) request.getSession().getAttribute("user");
%>
<!DOCTYPE html>
<html lang="en" xmlns:x-transition="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Create Notification</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">
</head>
<body>
<jsp:include page="/view/partials/navbar.jsp"></jsp:include>
<div class="w-full overflow-x-hidden border-t flex flex-col">
    <main class="w-full flex-grow p-6">
        <h1 class="text-4xl text-black pb-6">Create Notification for Class <%=targetClass.getName()%></h1>
        <h4 class="text-l text-black pb-6">Publisher: <%=user.getUsername()%></h4>
        <h4 class="text-l text-black pb-6">Class: <%=targetClass.getName()%></h4>
        <form action="" method="post">
            <input type="hidden" name="class" value="<%=targetClass.getId()%>">
            <input type="hidden" name="action" value="add">
            <div>
                <div>
                    <label for="title">Title</label>
                </div>
                <input type="text" name="title" id="title" placeholder="Enter title" maxlength="50"></div>
            <div>
                <div><label for="content">Content</label></div>
                <textarea name="content" id="content" cols="100" rows="10" maxlength="1000"></textarea></div>
            <button class="px-4 py-1 text-white font-light tracking-wider bg-gray-900 rounded" type="submit">Submit</button>
        </form>
    </main>
</div>
</body>
</div>
</html>
