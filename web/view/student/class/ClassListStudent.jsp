<%@ page import="entity.Class" %>
<%@ page import="utility.group.Pair" %>
<%@ page import="java.util.Vector" %><%--
    Document   : StudentHome
    Created on : Jun 3, 2023, 5:58:51 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Vector<Pair<Class, String>> classOfStudent = (Vector<Pair<entity.Class, String>>) request.getAttribute("classOfStudent");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Class List</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">
    </head>
    <body>
        <jsp:include page="/view/partials/navbar.jsp"/>
        <nav class="bg-white border border-gray-300 font-semibold p-4 mb-10">
            Dashboard
        </nav>
        <div class="relative max-w-sm mx-auto">
            <form action="">
                <% if (request.getParameter("subject") != null){%>
                <input type="hidden" name="subject" value="<%=request.getParameter("subject")%>">
                <%}%>
                <input class="w-full py-2 px-4 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500" type="search" placeholder="Search" name="query" value="<%=request.getParameter("query") != null ? request.getParameter("query") : ""%>">
                <button class="absolute inset-y-0 right-0 flex items-center px-4 text-gray-700 bg-gray-100 border border-gray-300 rounded-r-md hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                    <svg class="h-5 w-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" clip-rule="evenodd" d="M14.795 13.408l5.204 5.204a1 1 0 01-1.414 1.414l-5.204-5.204a7.5 7.5 0 111.414-1.414zM8.5 14A5.5 5.5 0 103 8.5 5.506 5.506 0 008.5 14z" />
                    </svg>
                </button>
            </form>
        </div>
        <div class="flex flex-wrap">
            <%for (Pair<Class, String> pair : classOfStudent){
                Class c = pair.getFirst();
                String teacher = pair.getSecond();
            %>
            <div class="w-full sm:w-1/2 md:w-1/3 lg:w-1/4 xl:w-1/4 p-4">
                <a href="quizzes?class=<%=c.getId()%>" class="block p-4 bg-white border border-gray-300 rounded shadow hover:bg-gray-100">
                    <h4 class="text-lg font-semibold flex items-center hover:text-blue-500">
                        <i class="fas fa-graduation-cap mr-2"></i>
                        <%=c.getName()%>
                    </h4>
                    <p class="flex items-center">
                        <i class="fas fa-info-circle mr-2"></i>
                        <%="Lecture: "+teacher%>
                    </p>
                    <p class="flex items-center">
                        <i class="fas fa-info-circle mr-2"></i>
                        <%="From: "+c.getStartClass().toString()+" to "+c.getFinishedClass().toString()%>
                    </p>
                </a>
            </div>
            <%}%>
        </div>


    </body>
</html>
