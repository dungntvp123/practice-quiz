<%--
  Created by IntelliJ IDEA.
  User: tuann
  Date: 6/12/2023
  Time: 8:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="entity.Subject" %>

<%@ page import="java.util.Vector" %>
<%@ page import="entity.Selection" %>
<%@ page import="entity.Question" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">
  <title>Add Question</title>
  <style>
    .highlight {
      background-color: #c8e6c9; /* Replace with your desired highlight color */
    }
    .custom-form{
      max-width: 800px;
    }
  </style>
</head>
  <% Vector<Subject> listSubject = (Vector<Subject>) request.getAttribute("listSubject");
  //Null?
    Vector<Selection> selections = (Vector<Selection>) request.getAttribute("selections");
    if (selections == null){
      selections = new Vector<>();
    }
    Question question = (Question) request.getAttribute("question");
    int selectedSubject = listSubject.isEmpty() ? 0 : listSubject.get(0).getId();
    if (question != null){
      selectedSubject = question.getSubjectId();
    }
    Subject targetSubject = null;
  %>
<body>
<div class="flex justify-center items-center h-screen">
  <form class="w-full max-w-lg custom-form" id="main-form" method="post">
    <input type="hidden" name="correct-answers" value="" id="correct-answers">
    <div class="flex flex-wrap -mx-3 mb-2">
      <div class="w-full md:w-1/3 px-3 mb-6 md:mb-0">
        <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
          Subject Name
        </label>
          <select class= "block appearance-none w-full bg-gray-200 border border-gray-200 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
               id="grid-SubjectName" type="text" name="subject" onchange="changeSubject(this)">
              <%for(Subject sub : listSubject){%>
              <option value="<%=sub.getId()%>" data-chapter="<%=sub.getNumOfChapter()%>" <%if (sub.getId() == selectedSubject){targetSubject=sub;%>selected<%}%>><%=sub.getName()%></option>
              <%}%>
          </select>
      </div>
      <div class="w-full md:w-1/3 px-3 mb-6 md:mb-0">
        <label id="chapter-label" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
          Chapter
        </label>
          <input class="block appearance-none w-full bg-gray-200 border border-gray-200 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                 id="grid-Chapter" type="number" placeholder="Select chapter (1-<%=targetSubject.getNumOfChapter()%>)" name="chapter" min="1" max="<%=targetSubject.getNumOfChapter()%>" value="<%=question == null ? "" : question.getChapter()%>" required>

      </div>
      <div class="w-full md:w-1/3 px-3 mb-6 md:mb-0">
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
          <textarea class="appearance-none block w-full bg-gray-200 text-gray-700 border border-red-500 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white"
                    id="grid-questionName" type="text" placeholder="Jane" name="question-title" required><%=question != null ? question.getDescription() : ""%></textarea>
            <span id="error"></span>
          <button class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded" id="addAnswerButton" type="button">Add Answer</button>
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
        <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" id="grid-submitButton" type="submit">Submit</button>
      </div>
    </div>

  </form>
</div>



<script src="${pageContext.request.contextPath}/js/create_answer.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.7.0.min.js"></script>
<script>
  <% for (int i = 0; i < selections.size(); i++) {
  Selection selection = selections.get(i);
%>
  addAnswerOnInit('<%=selection.getDescription().replace("'","\\'")%>','<%=selection.getCoefficent()%>',<%=selection.getCoefficent()!=0%>)
  <%}%>

</script>
</body>
</html>
