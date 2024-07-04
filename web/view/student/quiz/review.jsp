<%@ page import="java.util.Map" %>
<%@ page import="java.util.Vector" %>
<%@ page import="entity.extended.QuestionSelections" %>
<%@ page import="entity.*" %>
<%@ page import="utility.Utility" %><%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11-Jul-23
  Time: 7:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Map<TestDetail, QuestionSelections> testDetailQuestionMap = (Map<TestDetail, QuestionSelections>) request.getAttribute("testDetailQuestionMap");
    Test test = (Test) request.getAttribute("test");
    StudentTest studentTest = (StudentTest) request.getAttribute("studentTest");
    Vector<StudentTestDetail> stdList = (Vector<StudentTestDetail>) request.getAttribute("stdList");
%>
<html>
<head>
    <title>Review Quiz</title>
    <link href="${pageContext.request.contextPath}/css/tailwind.css" rel=" stylesheet">
</head>
<body>
<jsp:include page="/view/partials/navbar.jsp"></jsp:include>
<div class="container w-full md:w-4/5 xl:w-3/5 mx-auto px-2 rounded shadow bg-gray-100 mt-3">
    <h1 class="font-bold text-3xl text-black pb-6">Quiz Review</h1>
    <p>Test Name: <%=test.getName()%>
    </p>
    <p>Start: <%=studentTest.getStartTime()%>
    </p>
    <p>Finished: <%=studentTest.getFinishedTime()%>
    </p>
    <p>Total Score: <%=studentTest.getTotalScore()%>
    </p>
</div>
<% for (TestDetail td : testDetailQuestionMap.keySet()) {
    QuestionSelections q = testDetailQuestionMap.get(td);
    StudentTestDetail std = Utility.getTestDetailOfQuestion(stdList, td);
    double sum = 0;
%>
<div class="container w-full md:w-4/5 xl:w-3/5 mx-auto px-2 rounded shadow bg-gray-100 mt-3">
    <p><strong>Question Title</strong>: <%=q.getDescription()%></p>
    <% for (Selection s : q.getSelections()){
        sum += s.getCoefficent();
        if (s.getCoefficent() == 0){
    %>
    <p><%=s.getCharId()%>. <%=s.getDescription()%></p>
    <%} else {%>
    <p class="text-green-500 font-bold"><%=s.getCharId()%>. <%=s.getDescription()%></p>
    <%}%>
    <%}%>
    <p>Your choices: <%= std != null ? std.getSelected() : "None"%></p>
    <p>Scores: <%= std != null ? std.getScore() : "0"%></p>
</div>
<%}%>
</body>
</html>
