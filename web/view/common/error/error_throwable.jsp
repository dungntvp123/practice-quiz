<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 30-May-23
  Time: 4:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Throwable throwable = (Throwable) request
            .getAttribute("jakarta.servlet.error.exception");
    Integer statusCode = response.getStatus();
    String servletName = (String) request
            .getAttribute("jakarta.servlet.error.servlet_name");
    StackTraceElement cause = throwable.getStackTrace()[0];
%>
<html>
<head>
    <title>500 Internal Server Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">
</head>
<body>
<section class="flex items-center h-full p-16 dark:bg-gray-900 dark:text-gray-100">
    <div class="container flex flex-col items-center justify-center px-5 mx-auto my-8">
        <div class="max-w-md text-center">
            <h2 class="mb-8 font-extrabold text-9xl dark:text-gray-600">
                <span class="sr-only">Error</span><%=statusCode%>
            </h2>
            <p class="text-2xl font-semibold md:text-3xl"><%=throwable.getClass().getName()%>
            </p>
            <p class="mt-4 dark:text-gray-400">
                <strong>Servlet:</strong> <%=servletName%>
            </p>
            <p class="mt-4 dark:text-gray-400">
                <strong>Message:</strong> <%=throwable.getMessage()%>
            </p>
            <p class="mt-4 dark:text-gray-400">
                at
                <span class="text-red-700">
                    <%=cause.getClassName() + "." + cause.getMethodName() + "()"%>
                </span>
            </p>
            <p class="text-red-700">
                <%=cause.getFileName()%>, Line <%=cause.getLineNumber()%>.
            </p>
            <p class="mt-4 mb-8 dark:text-gray-400">
                See console for full stack trace.
            </p>
            <a href="${pageContext.request.contextPath}/mainpage"
               class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 border border-blue-700 rounded">
                Back to Home
            </a>
        </div>
    </div>
</section>
</body>
</html>
