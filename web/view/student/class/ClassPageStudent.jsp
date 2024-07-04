<%@ page import="entity.User, dao.StudentDAO, dao.StudentTestDAO" %>
<%@ page import="java.util.Vector, java.sql.Timestamp" %>
<%@ page import="entity.Test, utility.Utility, entity.StudentTest" %>
<%@ page import="org.apache.commons.lang.time.DurationFormatUtils" %>
<%@ page import="entity.Notification" %><%--
    Document   : TeacherClass
    Created on : Jun 8, 2023, 9:47:17 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%
    entity.Class targetClass = (entity.Class) request.getAttribute("class");
    User teacher = (User) request.getAttribute("teacher");
    Vector<Test> tests = (Vector<Test>) request.getAttribute("tests");
    HttpSession ses = request.getSession();
    User user = (User) ses.getAttribute("user");
    int studentid = StudentDAO.getInstance().getStudent(user.getId()).getId();
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
                <div class="inline-block text-gray-700 mb-4">
                    <label for="countries" class="block mb-2 text-lg font-medium text-blue-500">Quizzes Filter</label>
                    <select id="countries"
                            class="bg-gray-50 border border-gray-300 text-gray-900 text-lg rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5">
                        <option value="1">All Quizzes</option>
                        <option value="2">On Going</option>
                        <option value="3">Completed</option>
                    </select>
                </div>
                <%for (Test test : tests) {%>
                <button class="accordion w-full bg-white rounded-md shadow px-6 py-4 text-left flex justify-between items-center mt-4 text-blue-500 text-lg font-semibold border"
                        onclick="toggleAccordion(this)">
                    <%StudentTest studentTest = StudentTestDAO.getInstance().getStudentTest(studentid, test.getId());%>
                    <span><%=test.getName()%></span>
                    <i class="fas fa-chevron-down"></i>
                </button>
                <div class="panel overflow-hidden max-h-0 transition-all duration-500">
                    <div class="flex flex-col md:flex-row justify-between items-center p-6">
                        <p class="mb-4 md:mb-0 md:mr-4 flex-1">
                            ID: <%=test.getId()%><br>
                            Time: <%=Utility.changDateFormat(test.getStartTime())%> - <%=Utility.changDateFormat(test.getFinishedTime())%><br>
                            Duration: <%=DurationFormatUtils.formatDuration(test.getDuration()*1000,"HH:mm:ss",true)%><br>
                            <%if (studentTest.getId() != 0) {%>
                            Total Score: <%=studentTest.getTotalScore()%>/10.0<br>
                            Total Time: <%=DurationFormatUtils.formatDuration(studentTest.getFinishedTime().getTime() - studentTest.getStartTime().getTime(),"HH:mm:ss",true)%>
                            <% } %>
                        </p>
                        <%if (studentTest.getId() == 0) {%>
                        <%Timestamp cur = new Timestamp(System.currentTimeMillis());
                        if (cur.after(test.getStartTime()) && cur.before(test.getFinishedTime())) { %>
                        <a class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" href="${pageContext.request.contextPath}/student/doexam?testid=<%=test.getId()%>">Take Quiz <i
                                class="fa-solid fa-pen"></i></a>
                            <% } else if (cur.before(test.getStartTime())) { %>
                        <div class="py-2 px-4 rounded" style="color: black; background-color: #dddddd">Not yet</div>
                        <% } else if (cur.after(test.getFinishedTime())) { %>
                        <div class="py-2 px-4 rounded" style="color: red; background-color: #dddddd">Missing</div>
                        <% } %>
                        <% } else { %>
                        <div class="py-2 px-4 rounded" style="color: green; background-color: #dddddd">Done</div>
                        <% } %>
                    </div>
                </div>
                <%}%>


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
                        <span class="font-medium text-gray-800">Lecturer:</span> <span><%=teacher.getUsername()%></span>
                    </div>
                </div>
                <a href="${pageContext.request.contextPath}/student/history"
                   class="mt-4 inline-block bg-green-500 hover:bg-green-700 text-white text-center font-bold py-2 w-full rounded">See Quiz History <i class="fa-solid fa-timeline"></i></a>
                <a href="${pageContext.request.contextPath}/student/notifications?class=<%=targetClass.getId()%>"
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
