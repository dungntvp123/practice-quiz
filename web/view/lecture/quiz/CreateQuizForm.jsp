<%@ page import="entity.Test" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="utility.group.Pair" %><%--
  Created by IntelliJ IDEA.
  User: tuann
  Date: 6/10/2023
  Time: 4:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    entity.Class currentClass = (entity.Class) request.getAttribute("class");
    Test test = (Test) request.getAttribute("quiz");
    Vector<Map<String, Object>> bank = (Vector<Map<String, Object>>) request.getAttribute("bank");
    Vector<Pair<Map<String, Object>,Double>> sol = new Vector<>();
    Vector<Pair<Integer,Double>> inQuiz = (Vector<Pair<Integer,Double>>) request.getAttribute("inQuiz");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link href="${pageContext.request.contextPath}/css/tailwind.css" rel="stylesheet">
    <title>Create Quiz</title>
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
    <jsp:include page="../../partials/dataTableCSS.jsp"></jsp:include>
</head>

<body>
<jsp:include page="/view/partials/navbar.jsp"/>
<div class="flex justify-center mt-20 h-screen ">
    <form class="w-full max-w-lg custom-form" id="data-form" method="post">
        <%--Hidden--%>
        <% for (Pair<Integer,Double> i : inQuiz) { %>
        <%-- Insert HTML here --%>
        <input type="hidden" name="questions" value="<%=i.getFirst()%>">
        <% } %>
        <h1 class="text-center">Quiz for class: <%=currentClass.getName()%>
        </h1>
        <div class="flex relative overflow-x-auto  sm:rounded-lg">
            <div class="md:w-1/5">
                Quiz Name:
                <input
                        class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        type="text" name="name" value="<%=test == null ? "" : test.getName() + " (1)"%>" required>
            </div>
            <div class="md:w-1/5">
                Time:
                <input
                        class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        type="number" name="duration" placeholder="Input in minute" min="1"
                        value="<%=test == null ? 0 : test.getDuration()%>" required>
            </div>
            <div class="md:w-1/5">
                Start date:
                <input
                        class="mb-5 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        type="datetime-local" name="StartDate" id="StartDate" style="width: 220px;"
                        value="<%=test == null ? "" : sdf.format(test.getStartTime())%>" required>
            </div>
            <div class="md:w-1/5">
                End date:
                <input
                        class="mb-5 w-50 block w-0\.5 bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500 "
                        type="datetime-local" name="EndDate" id="EndDate" style="width: 220px;"
                        value="<%=test == null ? "" : sdf.format(test.getFinishedTime())%>" required>
            </div>
            <div class="md:w-1/5 flex justify-center items-center" style="margin-right: 50px;">
                <div class="block min-h-6 pl-7">
                    <label>
                        <input id="allow-review" name="allow-review" type="checkbox" />
                        <label for="allow-review" class="cursor-pointer select-none text-slate-700">Allow Review</label>
                    </label>
                </div>
            </div>



        </div>

        <div class="relative overflow-x-auto shadow-md sm:rounded-lg container m-auto grid grid-cols-2 gap-2">
            <div class="tile ">
                <p class="text-center font-bold">Question Bank</p>
                <table id="bank-table" class="stripe hover table-auto"
                       style="width:100%; padding-top: 1em;  padding-bottom: 1em;">
                    <thead class="text-gl text-white uppercase bg-blue-600">
                    <tr>
                        <th scope="col">
                            ID
                        </th>
                        <th scope="col">
                            Chap
                        </th>
                        <th scope="col">
                            Description
                        </th>
                        <th scope="col">
                            Edit
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Map<String, Object> question : bank) {
                        if (!inQuiz.isEmpty() && question.get("id") == inQuiz.get(0).getFirst()) {
                            sol.add(new Pair<>(question,inQuiz.get(0).getSecond()));
                            inQuiz.remove(0);
                            continue;
                        }
                    %>
                    <tr>
                        <td><%=question.get("id")%>
                        </td>
                        <td><%=question.get("chapter")%>
                        </td>
                        <td><%=question.get("description")%>
                        </td>
                        <td>
                            <button type="button"
                                    class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full"
                                    onclick="bankToSol()">
                                +
                            </button>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
            <div class="tile ">
                <p class="text-center font-bold">Questions In Quiz</p>
                <table id="sol-table" class="stripe hover table-auto"
                       style="width:100%; padding-top: 1em;  padding-bottom: 1em; margin-top: 42px;">
                    <thead class="text-gl text-white uppercase bg-blue-600">
                    <tr>
                        <th scope="col">
                            ID
                        </th>
                        <th scope="col">
                            Chap
                        </th>
                        <th scope="col">
                            Description
                        </th>
                        <th scope="col">
                            Edit
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Pair<Map<String, Object>,Double> pair : sol) {
                        Map<String,Object> question = pair.getFirst();
                    %>
                    <tr>
                        <td><%=question.get("id")%>
                        </td>
                        <td><%=question.get("chapter")%>
                        </td>
                        <td><%=question.get("description")%>
                        </td>
                        <td class="grid grid-cols-2 gap-2">
                            <input type="number" min="0" name="coefficient" value="<%=pair.getSecond()%>" class="gx-4 gy-2" style="width: 20px;" step="any" required>
                            <button class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-full"
                                    type="button" onclick="solToBank()">-
                            </button>
                        </td>
                    </tr>
                    <% } %>

                    </tbody>
                </table>
            </div>
        </div>
        <button
                class="appearance-none block w-1/5 bg-blue-200 text-blue-700 border border-blue-500 rounded py-3 px-4 mb-3 mt-10 leading-tight focus:outline-none focus:bg-blue"
                type="submit" onclick="submitForm()"> Submit
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