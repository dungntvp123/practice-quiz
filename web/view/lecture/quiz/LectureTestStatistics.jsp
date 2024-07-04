<%@ page import="java.util.Vector" %>
<%@ page import="java.util.Map" %>
<%@ page import="entity.extended.QuestionSelections" %>
<%@ page import="javax.rmi.CORBA.Util" %>
<%@ page import="utility.Utility" %>
<%@ page import="entity.*" %>
<%@ page import="utility.group.Pair" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 27-Jun-23
  Time: 4:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    Map<StudentTest, Vector<StudentTestDetail>> studentTestMap = (Map<StudentTest, Vector<StudentTestDetail>>) request.getAttribute("studentTestMap");
    Map<Integer, User> userMap = (Map<Integer, User>) request.getAttribute("userMap");
    Vector<QuestionSelections> questions = (Vector<QuestionSelections>) request.getAttribute("questions");
    Map<Integer, TestDetail> mapQuestionTestDetail = (Map<Integer, TestDetail>) request.getAttribute("mapQuestionTestDetail");
    Map<Integer, int[]> mapUserToResults = new HashMap<>();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" class="antialiased">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Quiz Statistics</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link href="${pageContext.request.contextPath}/css/tailwind.css" rel=" stylesheet">
    <!--Replace with your tailwind.css once created-->
    <!--Regular Datatables CSS-->
    <link href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" rel="stylesheet">
    <!--Responsive Extension Datatables CSS-->
    <link href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css" rel="stylesheet">

    <style>
        /*Overrides for Tailwind CSS */

        /*Form fields*/
        .dataTables_wrapper select,
        .dataTables_wrapper .dataTables_filter input {
            color: #4a5568;
            /*text-gray-700*/
            padding-left: 1rem;
            /*pl-4*/
            padding-right: 1rem;
            /*pl-4*/
            padding-top: .5rem;
            /*pl-2*/
            padding-bottom: .5rem;
            /*pl-2*/
            line-height: 1.25;
            /*leading-tight*/
            border-width: 2px;
            /*border-2*/
            border-radius: .25rem;
            border-color: #edf2f7;
            /*border-gray-200*/
            background-color: #edf2f7;
            /*bg-gray-200*/
        }

        /*Row Hover*/
        table.dataTable.hover tbody tr:hover,
        table.dataTable.display tbody tr:hover {
            background-color: #ebf4ff;
            /*bg-indigo-100*/
        }

        /*Pagination Buttons*/
        .dataTables_wrapper .dataTables_paginate .paginate_button {
            font-weight: 700;
            /*font-bold*/
            border-radius: .25rem;
            /*rounded*/
            border: 1px solid transparent;
            /*border border-transparent*/
        }

        /*Pagination Buttons - Current selected */
        .dataTables_wrapper .dataTables_paginate .paginate_button.current {
            color: #fff !important;
            /*text-white*/
            box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .1), 0 1px 2px 0 rgba(0, 0, 0, .06);
            /*shadow*/
            font-weight: 700;
            /*font-bold*/
            border-radius: .25rem;
            /*rounded*/
            background: #667eea !important;
            /*bg-indigo-500*/
            border: 1px solid transparent;
            /*border border-transparent*/
        }

        /*Pagination Buttons - Hover */
        .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
            color: #fff !important;
            /*text-white*/
            box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .1), 0 1px 2px 0 rgba(0, 0, 0, .06);
            /*shadow*/
            font-weight: 700;
            /*font-bold*/
            border-radius: .25rem;
            /*rounded*/
            background: #667eea !important;
            /*bg-indigo-500*/
            border: 1px solid transparent;
            /*border border-transparent*/
        }

        /*Add padding to bottom border */
        table.dataTable.no-footer {
            border-bottom: 1px solid #e2e8f0;
            /*border-b-1 border-gray-300*/
            margin-top: 0.75em;
            margin-bottom: 0.75em;
        }

        /*Change colour of responsive icon*/
        table.dataTable.dtr-inline.collapsed > tbody > tr > td:first-child:before,
        table.dataTable.dtr-inline.collapsed > tbody > tr > th:first-child:before {
            background-color: #667eea !important;
            /*bg-indigo-500*/
        }
    </style>


</head>

<body class="bg-gray-100 text-gray-900 tracking-wider leading-normal">


<div class="">
    <div class="p-8 mt-6 lg:mt-0 rounded shadow bg-gray-100">
        <h2 class="text-2xl font-bold leading-7 text-gray-900 sm:truncate sm:text-3xl sm:tracking-tight">Answers Review</h2>
        <div class="p-8 mt-6 lg:mt-0 rounded shadow bg-white">
            <table id="answers-review" class="stripe hover text-center" style="width:100%">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Total Score</th>
                    <%
                        for (int i = 0; i < questions.size(); i++) {
                            QuestionSelections question = questions.get(i);
                    %>
                    <th title="<%=Utility.reformat(question)%>">Q<%=i + 1%>
                    </th>
                    <%}%>
                </tr>
                </thead>
                <tbody>
                <% for (StudentTest studentTest : studentTestMap.keySet()) {
                    Vector<StudentTestDetail> studentTestDetails = studentTestMap.get(studentTest);
                    User user = userMap.get(studentTest.getStudentId());
                    //Later statistics
                    int correct = 0;
                    int partial = 0;
                    int incorrect = 0;
                %>
                <tr class="">
                    <td><%=user.getUsername()%>
                    </td>
                    <td><%=studentTest.getTotalScore()%>
                    </td>
                    <% for (QuestionSelections question : questions) {
                        TestDetail testDetail = mapQuestionTestDetail.get(question.getId());
                        StudentTestDetail std = Utility.getTestDetailOfQuestion(studentTestDetails, testDetail);
                        if (std == null) {
                            System.out.println("Null STD is found!");
                            std = new StudentTestDetail(-1, -1, "", 0);
//            continue;
                        }
                        //Count corrects answer
                        int correctsAnswers = 0;
                        int correctPicks = 0;
                        for (Selection selection : question.getSelections()) {
                            if (selection.getCoefficent() > 0) {
                                correctsAnswers++;
                                if (std.getSelected().contains(selection.getCharId())) {
                                    correctPicks++;
                                }
                            }
                        }
                        //Determine Color
                        String color;
                        if (correctPicks == 0) {
                            color = "bg-red-500";
                            incorrect++;
                        } else if (correctPicks != correctsAnswers) {
                            color = "bg-yellow-500";
                            partial++;
                        } else {
                            color = "bg-green-500";
                            correct++;
                        }

                    %>
                    <td data-order="<%=std.getScore()%>" class="<%=color%> border border-gray-700">
                        <p><%=std.getSelected().isEmpty() ? "None Picked" : std.getSelected()%>
                        </p>
                        <p><%=correctPicks%>/<%=correctsAnswers%>
                        </p>
                        <p><%=std.getScore()%>
                        </p>
                    </td>
                    <%
                        }
                        //Map Result to User
                        int correctPercentage = (int) (((double) correct / (correct + partial + incorrect)) * 100D);
                        int partialPercentage = (int) (((double) partial / (correct + partial + incorrect)) * 100D);
                        int[] results = {correctPercentage, partialPercentage, 100 - correctPercentage - partialPercentage};
                        mapUserToResults.put(user.getId(), results);
                    %>
                </tr>
                <%}%>
                </tbody>

            </table>
        </div>
        <h2 class="text-2xl font-bold leading-7 text-gray-900 sm:truncate sm:text-3xl sm:tracking-tight">Score
            Statistics</h2>
        <div class="p-8 mt-6 lg:mt-0 rounded shadow bg-white">
            <table id="scoring-review" class="stripe hover text-center " style="width:100%">
                <thead class="bg-blue-400">
                <tr>
                    <th>Name</th>
                    <th>Time</th>
                    <th>Score</th>
                </tr>
                </thead>
                <tbody>
                <% for (StudentTest studentTest : studentTestMap.keySet()) {
                    Vector<StudentTestDetail> studentTestDetails = studentTestMap.get(studentTest);
                    User user = userMap.get(studentTest.getStudentId());
                    int[] results = mapUserToResults.get(user.getId());
                    long seconds = Math.round(((studentTest.getFinishedTime().getTime() - studentTest.getStartTime().getTime()) / 1000.0)) ;
                    System.out.println(seconds);
                    long hours = seconds / 3600;
                    System.out.println(hours);
                    seconds -= hours * 3600;
                    long minutes = seconds / 60;
                    System.out.println(minutes);
                    seconds -= minutes * 60;
                %>
                <tr>
                    <td><%=user.getUsername()%>
                    </td>
                    <td><%=hours%>:<%=minutes%>:<%=seconds%></td>
                    <td class="inline">
                        <span><%=studentTest.getTotalScore()%></span>
                        <div class="relative pt-1">
                            <div title="<%=results[2]%>% Incorrect"
                                 class="overflow-hidden h-2 mb-4 text-xs flex rounded bg-red-500">
                                <div title="<%=results[0]%>% Correct" style="width: <%=results[0]%>%"
                                     class="shadow-none flex flex-col text-center whitespace-nowrap text-white justify-center bg-green-500">
                                </div>
                                <div title="<%=results[1]%>% Partially Correct" style="width: <%=results[1]%>%"
                                     class="shadow-none flex flex-col text-center whitespace-nowrap text-white justify-center bg-yellow-500">
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
                <%}%>
                </tbody>

            </table>
        </div>
    </div>
</div>


<!-- jQuery -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<!--Datatables -->
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
<script>
    $(document).ready(function () {

        var answers = $('#answers-review').DataTable({
            scrollX: true,
        })
            .columns.adjust()
            .responsive.recalc();
    });
    $(document).ready(function () {

        var scoring = $('#scoring-review').DataTable({
            responsive: true,
        })
            .columns.adjust()
            .responsive.recalc();
    });
</script>

</body>

</html>