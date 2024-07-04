<%@ page import="com.google.gson.JsonObject" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.Map" %><%--
    Document   : QuesBank
    Created on : Jun 9, 2023, 8:51:46 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%
    Vector<Map<String, Object>> questionBank = (Vector<Map<String, Object>>) request.getAttribute("questionBank");
    String del = request.getParameter("del");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Question Bank</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">
    <!--Regular Datatables CSS-->
    <link href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" rel="stylesheet">
    <!--Responsive Extension Datatables CSS-->
    <link href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css" rel="stylesheet">
    <jsp:include page="../../partials/dataTableCSS.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/view/partials/navbar.jsp"/>

<h2 class="text-3xl font-bold p-4 mt-4 text-center">Question Bank</h2>
<div id="notif-param">
    <%if ("success".equals(del)) {%>
    <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative" role="alert">
        <span class="block sm:inline">Successfully removed Question <%=request.getParameter("target")%></span>
        <span class="absolute top-0 bottom-0 right-0 px-4 py-3">
    </span>
    </div>
    <%} else if ("fail_invalid".equals(del)){%>
    <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
        <span class="block sm:inline">Question <%=request.getParameter("target")%> is in used by at least 1 quiz, therefore you cannot remove this Question.</span>
        <span class="absolute top-0 bottom-0 right-0 px-4 py-3">
    </span>
    </div>
    <%} else if ("fail_nochanges".equals(del)){%>
    <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
        <span class="block sm:inline">Question <%=request.getParameter("target")%> was not removed.</span>
        <span class="absolute top-0 bottom-0 right-0 px-4 py-3">
  </span>
    </div>
    <%} else if ("fail_notyours".equals(del)){%>
    <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
        <span class="block sm:inline">Question <%=request.getParameter("target")%> was not removed since that question does not belong to you.</span>
        <span class="absolute top-0 bottom-0 right-0 px-4 py-3">
  </span>
    </div>
    <%}%>
</div>

<div class="flex justify-end">
    <a class="mr-4 mt-4 mb-4 bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full"
       href="${pageContext.request.contextPath}/teacher/newQuestion"><i class="fa-solid fa-circle-plus"></i> New
        Question</a>
</div>

<div class="relative overflow-x-auto shadow-md sm:rounded-lg ml-4 mr-4">
    <table id="bank-table" class="stripe hover" style="width:100%; padding-top: 1em;  padding-bottom: 1em;">
        <thead class="text-gl text-white uppercase bg-blue-600">
        <tr>
            <th scope="col">
                ID
            </th>
            <th scope="col">
                Subject Name
            </th>
            <th scope="col">
                Chapter
            </th>
            <th scope="col">
                Description
            </th>
            <th scope="col">
                <span class="sr-only">Edit</span>
            </th>
        </tr>
        </thead>
        <tbody>
        <%for (Map<String, Object> question : questionBank) {%>


        <tr class="bg-white border-b">
            <th class="font-medium text-gray-900">
                <%=question.get("id")%>
            </th>
            <td>
                <%=question.get("subject")%>
            </td>
            <td>
                <%=question.get("chapter")%>
            </td>
            <td>
                <%=question.get("description")%>
            </td>
            <td class="text-right">
                <a href="${pageContext.request.contextPath}/teacher/newQuestion?questionID=<%=question.get("id")%>"
                   class="font-medium text-blue-500 hover:underline">Clone</a>
                <%if ((int) question.get("isIn") == 1) {%>
                <a href="${pageContext.request.contextPath}/QuestionRemove?questionID=<%=question.get("id")%>"
                   class="font-medium text-red-500 hover:underline">Remove</a>
                <%} else {%>
                <p class="font-medium text-gray-500 hover:underline">Remove</p>
                <%}%>
            </td>
        </tr>

        <%}%>
        </tbody>
    </table>
</div>

</body>
<!-- jQuery -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<!--Datatables -->
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
<script>

    $(document).ready(function () {

        var table = $('#bank-table').DataTable({
            responsive: true
        })
            .columns.adjust()
            .responsive.recalc();
    });

</script>
</html>
