package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import dao.*;
import entity.*;
import entity.Class;
import entity.Test;
import entity.extended.QuestionSelections;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static utility.WebRedirectionUtil.dispatch;
import static utility.WebRedirectionUtil.send403;

@WebServlet(name = "TeacherTestStatisticsController", value = "/teacher/stats")
public class TeacherTestStatisticsController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int testID = Integer.parseInt(request.getParameter("quizID"));
        int classID = Integer.parseInt(request.getParameter("classID"));
        //Verify
        User current = (User) request.getSession().getAttribute("user");
        Lecture lecture = LectureDAO.getInstance().getLecture(current.getId());
        //Are you in that class?
        Class clazz = ClassDAO.getInstance().getClassFromId(classID);
        if (clazz.getLectureId() != lecture.getId()){
            send403(request,response);
            return;
        }
        //Is that test in that class?
        Test test = TestDAO.getInstance().getTest(testID);
        if (test.getClassId() != clazz.getId()){
            send403(request,response);
            return;
        }
        //Student Test
        Vector<StudentTest> studentTests = StudentTestDAO.getInstance().getStudentTestsFromTest(testID);
        System.out.println(studentTests.size());
        //Map Each Student Test to the Details
        Map<StudentTest, Vector<StudentTestDetail>> studentTestMap = new HashMap<>();
        Map<Integer, User> userMap = new HashMap<>();
        for (StudentTest studentTest : studentTests) {
            Vector<StudentTestDetail> stdList = StudentTestDAO.getInstance().getStudentTestDetailsOfTest(studentTest.getId());
            studentTestMap.put(studentTest,stdList);
            //List of all users who took the test.
            User student = UserDAO.getInstance().getStudentUserFromId(studentTest.getStudentId());
            userMap.put(studentTest.getStudentId(),student);
        }
        //Get Questions from Test

        Vector<Question> testQuestion = QuestionDAO.getInstance().getTestQuestion(testID);
        //Wrap with Selections
        Vector<QuestionSelections> questions = new Vector<>();
        for (Question question : testQuestion) {
            questions.add(new QuestionSelections(question));
        }
        testQuestion = null; //De-Referencing
        //Test Detail
        Vector<TestDetail> coefficient = TestDAO.getInstance().getTestDetails(testID);
        //Map Question to Test Details
        Map<Integer,TestDetail> mapQuestionTestDetail = new HashMap<>();
        for (TestDetail testDetail : coefficient) {
            mapQuestionTestDetail.put(testDetail.getQuestionId(),testDetail);
        }
        //Pump to request
        request.setAttribute("studentTestMap",studentTestMap);
        request.setAttribute("userMap",userMap);
        request.setAttribute("questions",questions);
        request.setAttribute("mapQuestionTestDetail",mapQuestionTestDetail);
        dispatch(request,response,"/view/lecture/quiz/LectureTestStatistics.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}