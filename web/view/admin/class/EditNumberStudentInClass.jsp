<%@ page import="java.util.Vector" %>
<%@ page import="entity.Student" %>
<%@ page import="entity.StudentClassDetail" %>
<%@ page import="utility.Check" %><%--
  Created by IntelliJ IDEA.
  User: tuann
  Date: 7/16/2023
  Time: 4:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%

    Vector<Student> listAllStudent = (Vector<Student>) request.getAttribute("listAllStudent");
    Vector<StudentClassDetail> listAllStudentInClassNeedToUpdate = (Vector<StudentClassDetail>) request.getAttribute("listAllStudentInClassNeedToUpdate");
    String id = (String) request.getAttribute("ClassWithIdToChange");
    String errorWhenCreateClass = (String) request.getSession().getAttribute("ErrorUpdateNumberOfstudent");
%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link href="${pageContext.request.contextPath}/css/tailwind.css" rel="stylesheet">
    <title>Modify Class Students</title>
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
    <form action="${pageContext.request.contextPath}/admin/UpdateStudentInClass" class="w-full max-w-lg custom-form" id="data-form" method="post">
        <input type="hidden" name="ClassWithIdToChange" value="<%=id%>">
        <%if (errorWhenCreateClass != null) {%>
        <div class="flex relative overflow-x-auto  sm:rounded-lg">
            <div class="flex p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400"
                 role="alert">
                <svg class="flex-shrink-0 inline w-4 h-4 mr-3 mt-[2px]" aria-hidden="true"
                     xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                    <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
                </svg>
                <span class="sr-only">Danger</span>
                <div>
                    <span class="font-medium">Error When Update:</span>
                    <ul class="mt-1.5 ml-4 list-disc list-inside">
                        <li>Maximum Student In Class is 30</li>
                        <li>Minimum Student In Class is 15</li>

                    </ul>
                </div>
            </div>
        </div>
        <%}%>

        <div class="relative overflow-x-auto shadow-md sm:rounded-lg container m-auto ">

            <p class="text-center font-bold">Student</p>


            <table id="src-table" class="stripe hover table-auto"
                   style="width:100%; padding-top: 1em;  padding-bottom: 1em;">
                <thead class="text-gl text-white uppercase bg-blue-600">
                <tr>
                    <th scope="col">
                        Student ID

                    </th>
                    <th scope="col">
                        Student Name

                    </th>

                    <th scope="col">
                        Action
                    </th>


                </tr>
                </thead>
                <tbody>
                <% for (Student std : listAllStudent) { %>
                <tr>
                    <td><%=std.getId()%>
                    </td>
                    <td><%=std.getFirstname() + " " + std.getLastname()%>
                    </td>
                    <td>
                        <%if (Check.Check(listAllStudentInClassNeedToUpdate, std.getId()) == true) { %>
                        <input type="checkbox" name="StudentIDToGet" value="<%=std.getId()%>" checked>
                        <%} else {%>

                        <input type="checkbox" name="StudentIDToGet" value="<%=std.getId()%>">
                        <%}%>
                    </td>
                </tr>
                <%}%>
                </tbody>
            </table>


        </div>
        <button
                class="appearance-none block w-1/5 bg-blue-200 text-blue-700 border border-blue-500 rounded py-3 px-4 mb-3 mt-10 leading-tight focus:outline-none focus:bg-blue"
                id="btnSubmitCreateClassForm" type="submit" onclick="submitForm()"> Submit
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
<script>
    let bankTable = $('#src-table').DataTable({
        responsive: true,
        bPaginate: false,
        searching: false
    })
        .columns.adjust()
        .responsive.recalc();
</script>
<script>

</script>
</html>
