    <%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="entity.extended.ScoreHistory" %>
<%@ page import="java.util.Vector" %>
<%
    Vector<ScoreHistory> history = (Vector<ScoreHistory>) request.getAttribute("history");
%>
<!DOCTYPE html>
<html lang="en" class="antialiased">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Score History</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link href="${pageContext.request.contextPath}/css/tailwind.css" rel=" stylesheet">
    <!--Replace with your tailwind.css once created-->


    <!--Regular Datatables CSS-->
    <link href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" rel="stylesheet">
    <!--Responsive Extension Datatables CSS-->
    <link href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css" rel="stylesheet">

    <jsp:include page="../../partials/dataTableCSS.jsp"></jsp:include>



</head>

<body class="bg-gray-100 text-gray-900 tracking-wider leading-normal">
<jsp:include page="../../partials/navbar.jsp"></jsp:include>

<!--Container-->
<div class="container w-full md:w-4/5 xl:w-3/5  mx-auto px-2">

    <!--Title-->
    <h1 class="flex items-center font-sans font-bold break-normal text-indigo-500 px-2 py-8 text-xl md:text-2xl">
        Score History
    </h1>


    <!--Card-->
    <div id='recipients' class="p-8 mt-6 lg:mt-0 rounded shadow bg-white">


        <table id="example" class="stripe hover" style="width:100%; padding-top: 1em;  padding-bottom: 1em;">
            <thead>
            <tr>
                <th data-priority="1">ID</th>
                <th data-priority="2">Test Name</th>
                <th data-priority="3">Class</th>
                <th data-priority="4">Subject</th>
                <th data-priority="5">Start</th>
                <th data-priority="6">Finish</th>
                <th data-priority="7">Score</th>
                <th data-priority="8">Review</th>
            </tr>
            </thead>
            <tbody>
            <%for (ScoreHistory score : history){%>
            <tr>
                <td><%=score.getID()%></td>
                <td><%=score.getTestName()%></td>
                <td><%=score.getClassName()%></td>
                <td><%=score.getSubject()%></td>
                <td><%=score.getStartDate().toString()%></td>
                <td><%=score.getFinishDate().toString()%></td>
                <td><%=score.getScore()%></td>
                <td><a href="${pageContext.request.contextPath}/student/quizzes/review?id=<%=score.getID()%>" class="no-underline hover:underline text-blue-500">Review</a></td>
            </tr>
            <%}%>
            </tbody>

        </table>


    </div>
    <!--/Card-->


</div>
<!--/container-->





<!-- jQuery -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<!--Datatables -->
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
<script>
    $(document).ready(function() {

        var table = $('#example').DataTable({
            responsive: true
        })
            .columns.adjust()
            .responsive.recalc();
    });
</script>

</body>

</html>

