<%-- 
    Document   : ViewLectureTestStatistic
    Created on : Jun 17, 2023, 10:10:16 PM
    Author     : Asus
--%>
<%@ page import="java.util.Vector, java.sql.Timestamp" %>
<%@ page import="entity.Test, utility.Utility, dao.StudentDAO, dao.StudentTestDAO" %>
<%@ page import="entity.Student, entity.StudentTest" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Statistics</title>
    </head>
    <body>
        <%
            Integer classid = Integer.parseInt((String) request.getParameter("classID"));
            Integer testid = Integer.parseInt((String) request.getParameter("quizID"));
            Vector<Student> students = StudentDAO.getInstance().getStudents(classid);
        %>
        <table border="1">
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Student ID</th>
                    <th>Student Name</th>
                    <th>Score</th>
                    <th>Time</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%for(Student student : students) {
                    StudentTest studentTest = StudentTestDAO.getInstance().getStudentTest(student.getId(), testid);
                %>
                <tr>
                    <td><%=students.indexOf(student)%></td>
                    <td><%=student.getId()%></td>
                    <td><%=student.getFirstname() + " " + student.getLastname()%></td>
                    <td><%=studentTest.getTotalScore()%></td>
                    <td>
                        <%if (studentTest.getId() != 0) {%>
                        <%=Utility.timeToString2((studentTest.getFinishedTime().getTime() - studentTest.getStartTime().getTime())/1000, 1)%></td>
                        <% } else {%>
                        00:00
                        <% } %>
                        <td><a href="#">Remove</a></td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>
