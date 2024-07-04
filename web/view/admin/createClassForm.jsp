<%@ page import="java.util.Vector" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="entity.Student" %>
<%@ page import="entity.Lecture" %>
<%@ page import="entity.Subject" %><%--
  Created by IntelliJ IDEA.
  User: tuann
  Date: 6/10/2023
  Time: 4:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    Vector<Student> listAllStudent = (Vector<Student>) request.getAttribute("listAllStudent");
    Vector<Lecture> listAllLecture = (Vector<Lecture>) request.getAttribute("listAllLecture");
    Vector<Subject> listAllSubject = (Vector<Subject>) request.getAttribute("listAllSubject");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    String errorWhenCreateClass = (String) request.getSession().getAttribute("errorWhenCreateClass");
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link href="${pageContext.request.contextPath}/css/tailwind.css" rel="stylesheet">
    <title>Create New Class</title>
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
    <jsp:include page="../partials/dataTableCSS.jsp"></jsp:include>
</head>

<body>
<jsp:include page="/view/partials/navbar.jsp"/>
<div class="flex justify-center mt-20 h-screen ">
    <form action="classes/add" class="w-full max-w-lg custom-form" id="data-form" method="post">
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
                    <span class="font-medium">Error When Create:</span>
                    <ul class="mt-1.5 ml-4 list-disc list-inside">
                        <li>Maximum Student In Class is 30</li>
                        <li>Minimum Student In Class is 15</li>
                    </ul>
                </div>
            </div>
        </div>
        <%}%>

        <div class="flex relative overflow-x-auto  sm:rounded-lg">
            <div class="md:w-1/5">
                Class Name
                <input
                        class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        type="text" name="ClassName" id="ClassName" required>
            </div>
            <div class="md:w-1/5">
                Subject Name

                <select class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        name="SubjectName" style="width: 234px;" required>
                    <% for (Subject subject : listAllSubject) {%>
                    <option value="<%=subject.getId()%>"><%=subject.getName()%>
                    </option>
                    <%}%>
                </select>
            </div>
            <div class="md:w-1/5">
                Lecture ID:
                <select class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        name="LectureID" style="width: 234px;" required>
                    <% for (Lecture lecture : listAllLecture) {%>
                    <option value="<%=lecture.getId()%>"><%=lecture.getId()%>
                    </option>
                    <%}%>
                </select>
            </div>
            <div class="md:w-1/5">
                Start date:
                <input
                        class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        type="date" name="StartDateSubject" id="StartDateSubject" style="width: 234px;"
                        required>
            </div>
            <div class="md:w-1/5">
                End date:
                <input
                        class="mb-5 w-50 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        type="date" name="EndDateSubject" id="EndDateSubject" style="width: 234px;"
                        required>
            </div>


        </div>

        <div class="relative overflow-x-auto shadow-md sm:rounded-lg container m-auto ">

            <p class="text-center font-bold">Question Bank</p>
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
                    <th>Role</th>
                    <th>
                        Action
                    </th>
                </tr>
                </thead>
                <tbody>
                <% for (Student student : listAllStudent) {

                %>
                <tr>

                    <td><%=student.getId()%>
                    </td>
                    <td><%=student.getLastname() + " " + student.getFirstname()%>
                    </td>
                    <td>Student
                    </td>
                    <td>
                        <input type="checkbox" name="StudentIDToGet" value="<%=student.getId()%>"
                               onclick="limitCheckboxSelection(this)">
                    </td>
                </tr>
                <% } %>
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

</html>