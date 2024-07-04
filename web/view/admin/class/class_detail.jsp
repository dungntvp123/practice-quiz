<%@ page import="java.util.Vector" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="entity.*" %>
<%@ page import="entity.Class" %>
<%@ page import="dao.SubjectDAO" %><%--
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
    Vector<StudentClassDetail> studentInClass = (Vector<StudentClassDetail>) request.getAttribute("studentInClass");
    Class classDetail = (Class) request.getAttribute("classDetail");
    Subject subject = SubjectDAO.getInstance().getSubject(classDetail.getSubjectId());

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link href="${pageContext.request.contextPath}/css/tailwind.css" rel="stylesheet">
    <title>Class <%=classDetail.getName()%> Details</title>
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
    <form action="UpdateClassDetail" class="w-full max-w-lg custom-form" id="data-form" method="post">

        <input type="number" class="hidden" value="<%=classDetail.getId()%>" name="ClassID">
        <div class="flex relative overflow-x-auto  sm:rounded-lg">
            <div class="md:w-1/6">
                Class Name
                <%if (classDetail.getStatus() == 1) {%>
                <input
                        class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        type="text" name="ClassName" id="ClassName" style="width: 195px;" readonly
                        value="<%=classDetail.getName()%>">
                <%} else {%>
                <input
                        class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        type="text" name="ClassName" id="ClassName" style="width: 195px;"
                        value="<%=classDetail.getName()%>">
                <%}%>
            </div>
            <div class="md:w-1/6">
                Subject Name
                <%if (classDetail.getStatus() == 1) {%>
                <select class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 pointer-events-none"
                        name="SubjectName" style="width: 195px;" required>

                    <% for (Subject lsitsubject : listAllSubject) {%>
                    <option value="<%=lsitsubject.getId()%>" <%=((lsitsubject.getId() == subject.getId()) ? "selected" : "")%>><%=lsitsubject.getName()%>
                    </option>
                    <%}%>
                </select>
                <%} else {%>

                <select class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        name="SubjectName" style="width: 195px;" required>

                    <% for (Subject lsitsubject : listAllSubject) {%>
                    <option value="<%=lsitsubject.getId()%>" <%=((lsitsubject.getId() == subject.getId()) ? "selected" : "")%>><%=lsitsubject.getName()%>
                    </option>
                    <%}%>
                </select>
                <%}%>
            </div>
            <div class="md:w-1/6">
                Lecture ID:
                <select class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        name="LectureID" style="width: 195px;" required>

                    <% for (Lecture lecture : listAllLecture) {%>
                    <option value="<%=lecture.getId()%>" <%=((lecture.getId() == classDetail.getLectureId()) ? "selected" : "")%>><%=lecture.getId()%>
                    </option>
                    <%}%>
                </select>
            </div>
            <div class="md:w-1/6">
                Start date:
                <%if (classDetail.getStatus() == 1) { %>
                <input
                        class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        type="date" name="StartDateSubject" id="StartDateSubject" readonly
                        value="<%=classDetail.getStartClass()%>" style="width: 195px;"
                        required>
                <%} else {%>
                <input
                        class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        type="date" name="StartDateSubject" id="StartDateSubject"
                        value="<%=classDetail.getStartClass()%>" style="width: 195px;"
                        required>
                <%}%>
            </div>
            <div class="md:w-1/6">
                End date:
                <%if (classDetail.getStatus() == 1) { %>
                <input
                        class="mb-5 w-50 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        type="date" name="EndDateSubject" id="EndDateSubject"
                        value="<%=classDetail.getFinishedClass()%>" style="width: 195px;" readonly
                        required>
                <%} else {%>

                <input
                        class="mb-5 w-50 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        type="date" name="EndDateSubject" id="EndDateSubject"
                        value="<%=classDetail.getFinishedClass()%>" style="width: 195px;"
                        required>
                <%}%>
            </div>
            <div class="md:w-1/6">
                Status:
                <select class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        name="Status" style="width: 195px;" required>

                    <option value="1" <%=((classDetail.getStatus() == 1) ? "selected" : "")%>>
                        Enable
                    </option>
                    <option value="0" <%=((classDetail.getStatus() == 0) ? "selected" : "")%>>
                        Disable
                    </option>

                </select>
            </div>


        </div>

        <div class="relative overflow-x-auto shadow-md sm:rounded-lg container m-auto ">

            <p class="text-center font-bold">Class Detail</p>
            <%if(classDetail.getStatus()==0){%>
            <a class="text-center font-bold"
               href="ShowUpdateNumberStudent?ClassWithIdToChange=<%=classDetail.getId()%>">UpdateNumber</a>
            <%}%>
            <table id="bank-table" class="stripe hover table-auto"
                   style="width:100%; padding-top: 1em;  padding-bottom: 1em;">
                <thead class="text-gl text-white uppercase bg-blue-600">
                <tr>
                    <th scope="col">
                        Student ID

                    </th>
                    <th scope="col">
                        Action
                    </th>


                </tr>
                </thead>
                <tbody>
                <% for (StudentClassDetail student : studentInClass) {

                %>
                <tr>

                    <td><%=student.getStudentId()%>
                        <input type="hidden" value="<%=student.getStudentId()%>" name="IdOfStudentToChange">
                    </td>
                    <td>
                        <select name="statusOfStudentInClass">
                            <option value="1" <%=((student.getStatus() == 1) ? "selected" : "")%>>
                                Enable
                            </option>
                            <option value="0" <%=((student.getStatus() == 0) ? "selected" : "")%>>
                                Disable
                            </option>
                        </select>

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
<script src="${pageContext.request.contextPath}/js/create-quiz.js"></script>
<script>

</script>
</html>