<%@ page import="java.util.Vector" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="entity.Student" %>
<%@ page import="entity.Lecture" %>
<%@ page import="entity.Subject" %>
<%@ page import="entity.Class" %><%--
  Created by IntelliJ IDEA.
  User: tuann
  Date: 6/10/2023
  Time: 4:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Vector<Class> listAllClass = (Vector<Class>) request.getAttribute("classes");

%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link href="${pageContext.request.contextPath}/css/tailwind.css" rel="stylesheet">
    <title>Class List</title>
    <style>
        .custom-form {
            max-width: 1200px;
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
    <!--Regular Datatables CSS-->
    <link href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" rel="stylesheet">
    <!--Responsive Extension Datatables CSS-->
    <link href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css" rel="stylesheet">
    <jsp:include page="/view/partials/dataTableCSS.jsp"></jsp:include>
</head>

<body>
<jsp:include page="/view/partials/navbar.jsp"/>
<div class="flex justify-center mt-20 h-screen ">
    <form action="CreateClass" class="w-full max-w-lg custom-form" id="data-form" method="post">


        <div class="flex relative overflow-x-auto  sm:rounded-lg">

        </div>

        <div class="relative overflow-x-auto shadow-md sm:rounded-lg container m-auto ">

            <p class="text-center font-bold">Class List</p>
            <table id="bank-table" class="stripe hover table-auto"
                   style="width:100%; padding-top: 1em;  padding-bottom: 1em;">
                <thead class="text-gl text-white uppercase bg-blue-600">
                <tr>
                    <th scope="col">
                        Class ID
                    </th>
                    <th scope="col">
                        Class Name
                    </th>

                    <th>
                        Status
                    </th>
                    <th>
                        Action
                    </th>
                </tr>
                </thead>
                <tbody>
                <% for (Class classInfor : listAllClass) {

                %>
                <tr>

                    <td><%=classInfor.getId()%>
                    </td>
                    <td><%= classInfor.getName()%>
                    </td>
                    <% if (classInfor.getStatus() == 0) {%>
                    <td>
                        Disable
                    </td>
                    <% } %>
                    <% if (classInfor.getStatus() == 1) {%>
                    <td>
                        Enable
                    </td>
                    <% } %>
                    <td>
                        <a href="ShowClassDetail?IdOfClassToView=<%=classInfor.getId()%>">Edit</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>


        </div>
        <button
                class="appearance-none block w-1/5 bg-blue-200 text-blue-700 border border-blue-500 rounded py-3 px-4 mb-3 mt-10 leading-tight focus:outline-none focus:bg-blue"
                id="btnSubmitCreateClassForm" type="submit" onclick="submitForm()" disabled> Submit
        </button>
    </form>
</div>


</body>
<!--Datatables -->
<!-- jQuery -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<!--Datatables -->
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
<script src="${pageContext.request.contextPath}/js/create-quiz.js"></script>

</html>