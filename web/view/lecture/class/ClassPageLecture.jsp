<%@ page import="entity.User" %>
<%@ page import="java.util.Vector, java.sql.Timestamp" %>
<%@ page import="entity.Test, utility.Utility" %>
<%@ page import="org.apache.commons.lang.time.DurationFormatUtils" %>
<%@ page import="entity.Notification" %><%--
    Document   : TeacherClass
    Created on : Jun 8, 2023, 9:47:17 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%
    entity.Class targetClass = (entity.Class) request.getAttribute("class");
    User self = (User) request.getSession().getAttribute("user");
    Vector<Test> tests = (Vector<Test>) request.getAttribute("tests");
    String del = request.getParameter("del");
    Vector<Notification> notifications = (Vector<Notification>) request.getAttribute("notifications");
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><%=targetClass.getName()%></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">
</head>
<body>
<jsp:include page="../../partials/navbar.jsp"/>
<nav class="bg-white border border-gray-300 font-semibold p-4 text-3xl">
    <%=targetClass.getName()%>
</nav>

<div class="grid grid-cols-10 gap-4">
    <div class="col-span-6 mx-4 my-8">
        <div class="flex justify-end">
            <a href="${pageContext.request.contextPath}/teacher/edit_quiz?classID=<%=targetClass.getId()%>"
               class="text-right bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full"><i
                    class="fa-solid fa-circle-plus"></i> New Quiz</a>
        </div>
        <%for (Test test : tests) {%>
        <button class="accordion w-full bg-white rounded-md shadow px-6 py-4 text-left flex justify-between items-center mt-4 text-blue-500 text-lg font-semibold border"
                onclick="toggleAccordion(this)">
            <span><%=test.getName()%></span>
            <i class="fas fa-chevron-down"></i>
        </button>
        <div class="panel overflow-hidden max-h-0 transition-all duration-500">
            <div class="flex flex-col md:flex-row justify-between items-center p-6">
                <p class="mb-4 md:mb-0 md:mr-4 flex-1">
                    ID: <%=test.getId()%><br>
                    Time: <%=Utility.changDateFormat(test.getStartTime())%>
                    - <%=Utility.changDateFormat(test.getFinishedTime())%><br>
                    Duration: <%=DurationFormatUtils.formatDuration(test.getDuration()*1000,"HH:mm:ss",true)%>
                </p>
                <%Timestamp cur = new Timestamp(System.currentTimeMillis());%>
                <%if (cur.before(test.getStartTime())) {%>
                <a class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                   href="${pageContext.request.contextPath}/teacher/preview-quiz?&quizID=<%=test.getId()%>">Preview
                    <i class="fa-solid fa-wand-magic-sparkles"></i> </a>
                <a class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                   href="${pageContext.request.contextPath}/teacher/edit_quiz?classID=<%=targetClass.getId()%>&quizID=<%=test.getId()%>">Clone
                    <i class="fa-solid fa-wand-magic-sparkles"></i> </a>
                <a class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
                   href="${pageContext.request.contextPath}/teacher/remove_quiz?classID=<%=targetClass.getId()%>&quizID=<%=test.getId()%>">Remove
                    <i class="fa-solid fa-trash"></i> </a>
                <% } else {%>
                <a class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                   href="${pageContext.request.contextPath}/teacher/stats?classID=<%=targetClass.getId()%>&quizID=<%=test.getId()%>">View
                    Detail</a>
                <% } %>
            </div>
        </div>
        <% } %>


    </div>

    <div class="col-span-4 mx-4 my-8">
        <div class="p-4 bg-white rounded-lg shadow-md border-2">
            <h2 class="text-lg font-medium text-blue-500 mb-2"><i class="fa-solid fa-book"></i> Class Information</h2>
            <div class="mb-2 p-2 bg-gray-100 rounded-lg">
                <span class="font-medium text-gray-800">Name:</span> <span><%=targetClass.getName()%></span>
            </div>
            <div class="mb-2 p-2 bg-gray-100 rounded-lg">
                <span class="font-medium text-gray-800">Start Date:</span>
                <span><%=targetClass.getStartClass().toString()%></span>
            </div>
            <div class="mb-2 p-2 bg-gray-100 rounded-lg">
                <span class="font-medium text-gray-800">End Date:</span>
                <span><%=targetClass.getFinishedClass().toString()%></span>
            </div>
            <div class="p-2 bg-gray-100 rounded-lg">
                <span class="font-medium text-gray-800">Lecturer:</span> <span><%=self.getUsername()%></span>
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/teacher/bank"
           class="mt-4 inline-block bg-green-500 hover:bg-green-700 text-white text-center font-bold py-2 w-full rounded">Go
            to Question Bank <i class="fa-solid fa-building-columns"></i></a>
        <a href="${pageContext.request.contextPath}/teacher/notifications?class=<%=targetClass.getId()%>"
           class="mt-4 inline-block bg-red-500 hover:bg-red-700 text-white text-center font-bold py-2 w-full rounded">View All Notifications <i class="fa-solid fa-building-columns"></i></a>
        <div class="mt-4">
            <h2 class="text-2xl text-black pb-6">Latest Notification</h2>
            <ol class="list-decimal">
                <%for (Notification notification : notifications) {%>
                <li><a href="${pageContext.request.contextPath}/user/notification?id=<%=notification.getId()%>"
                       class="no-underline hover:underline text-blue-700"><%=notification.getTitle()%> (<%=notification.getTimeOfPublish()%>)</a></li>
                <%}%>
            </ol>
        </div>
        <%-- Notification --%>
        <div id="notif-param">
            <%if ("success".equals(del)) {%>
            <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative" role="alert">
                <span class="block sm:inline">Successfully removed Quiz <%=request.getParameter("target")%></span>
                <span class="absolute top-0 bottom-0 right-0 px-4 py-3">
    </span>
            </div>
            <%} else if ("fail_nochanges".equals(del)) {%>
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
                <span class="block sm:inline">Quiz <%=request.getParameter("target")%> was not removed.</span>
                <span class="absolute top-0 bottom-0 right-0 px-4 py-3">
  </span>
            </div>
            <%} else if ("fail_started".equals(del)) {%>
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
                <span class="block sm:inline">Quiz <%=request.getParameter("target")%> was not removed since the start time has been surpassed.</span>
                <span class="absolute top-0 bottom-0 right-0 px-4 py-3">
  </span>
            </div>
            <%} else if ("fail_notyours".equals(del)) {%>
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
                <span class="block sm:inline">Quiz <%=request.getParameter("target")%> was not removed since that quiz does not belong to you.</span>
                <span class="absolute top-0 bottom-0 right-0 px-4 py-3">
  </span>
            </div>
            <%}%>
        </div>
    </div>

</div>

<script>

    function toggleAccordion(button) {
        const content = button.nextElementSibling;
        const icon = button.querySelector('i');
        button.classList.toggle('active');
        if (content.style.maxHeight) {
            content.style.maxHeight = null;
            icon.classList.replace('fa-chevron-up', 'fa-chevron-down');
        } else {
            content.style.maxHeight = content.scrollHeight + "px";
            icon.classList.replace('fa-chevron-down', 'fa-chevron-up');
        }
    }
</script>
</body>
</html>
