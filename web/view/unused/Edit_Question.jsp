<%@ page import="entity.Question" %>
<%@ page import="entity.ExtendedQuestion" %>
<%@ page import="java.util.Vector" %>
<%@ page import="entity.Selection" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">
    <title>JSP Page</title>
    <style>
        .highlight {
            background-color: #c8e6c9; /* Replace with your desired highlight color */
        }

        .custom-form {
            max-width: 800px;
        }
    </style>
</head>
<%
    ExtendedQuestion question = (ExtendedQuestion) request.getAttribute("question");
    Vector<Selection> selections = (Vector<Selection>) request.getAttribute("selections");
    int correctAnswers = (int) request.getAttribute("correctAnswers");
%>
<body>
<div class="flex justify-center mt-8 h-screen">
    <form class="w-full max-w-lg custom-form" id="main-form" method="post">
        <input type="hidden" name="correct-answers" value="" id="correct-answers">
        <div class="flex flex-wrap -mx-3 mb-2">
            <div class="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                    Chapter
                </label>
                <input class="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                       id="grid-city" type="number" placeholder="Select No of chapter"
                       value="<%=question.getChapter()%>">
            </div>
            <div class="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                    Type of question
                </label>
                <div class="relative" id="question-info-box">
                    <select class="block appearance-none w-full bg-gray-200 border border-gray-200 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                            name="choice" id="grid-typeQuestion">
                        <option value="0">Multiple choice</option>
                        <option value="1">One choice</option>
                    </select>
                    <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                        <svg class="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                            <path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/>
                        </svg>
                    </div>
                </div>
            </div>
        </div>
        <div class="flex flex-wrap -mx-3 mb-6">
            <div class="w-full px-3 mb-6 md:mb-0">
                <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                       for="grid-questionName">
                    Question Name
                </label>
                <div class="relative" id="">
                    <input class="appearance-none block w-full bg-gray-200 text-gray-700 border border-red-500 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white"
                           id="grid-questionName" type="text" placeholder="Jane" name="question-title"
                           value="<%=question.getDescription()%>" required>
                    <button class="absolute right-0 top-0 mt-3 mr-4" id="addAnswerButton" type="button">Add Answer
                    </button>

                </div>
                <p class="text-red-500 text-xs italic">Please fill out this field.</p>
            </div>
        </div>

        <div class="flex flex-wrap -mx-3 mb-6">
            <div class="w-full px-3">
                <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                       for="grid-submitButton">
                    Submit
                </label>
                <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                        id="grid-submitButton" type="submit">Submit
                </button>
            </div>
        </div>

    </form>
</div>
</body>
<script src="${pageContext.request.contextPath}/js/create_answer.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.0.min.js"></script>
<script>
    <% for (int i = 0; i < selections.size(); i++) {
        Selection selection = selections.get(i);
    %>
    addAnswerOnInit('<%=selection.getDescription()%>','<%=selection.getCoefficent()%>',<%=selection.getCoefficent()!=0%>)
    <%}%>
</script>
<script>
    const x = <%=correctAnswers%>
    if (x > 1) {
        document.getElementById("grid-typeQuestion").value = 0
    } else {
        document.getElementById("grid-typeQuestion").value = 1
    }
</script>
</html>
