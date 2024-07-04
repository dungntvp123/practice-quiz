package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import dao.ClassDAO;
import dao.QuestionDAO;
import dao.StudentTestDAO;
import dao.TestDAO;
import entity.*;
import entity.Class;
import entity.Test;
import entity.extended.QuestionSelections;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static utility.WebRedirectionUtil.*;

@WebServlet(name = "StudentQuizReviewController", value = "/student/quizzes/review")
public class StudentQuizReviewController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //Checks
        String id = request.getParameter("id");
        if (id == null) {
            send404(request, response);
            return;
        }
        User user = (User) request.getSession().getAttribute("user");
        StudentTest studentTest = StudentTestDAO.getInstance().getStudentTest(Integer.parseInt(id));
        Test test = TestDAO.getInstance().getTest(studentTest.getTestId());
        Vector<TestDetail> testDetails = TestDAO.getInstance().getTestDetails(test.getId());
        //Are you in that class?
        Class cl = ClassDAO.getInstance().getClassFromId(test.getClassId());
        if (cl == null) {
            send403(request, response);
            return;
        }
        if (!ClassDAO.getInstance().isStudentUserInClass(user.getId(), cl.getId())) {
            send403(request, response);
            return;
        }
        //Can you review it?
        if (!test.getAllowReview()) {
            send403(request, response);
            return;
        }
        //Map
        Map<TestDetail, QuestionSelections> testDetailQuestionMap = new HashMap<>();
        for (TestDetail td : testDetails) {
            testDetailQuestionMap.put(td, new QuestionSelections(QuestionDAO.getInstance().getQuestion(td.getQuestionId())));
        }
        //STD
        Vector<StudentTestDetail> stdList = StudentTestDAO.getInstance().getStudentTestDetailsOfTest(studentTest.getId());
        //Set Att
        request.setAttribute("studentTest", studentTest);
        request.setAttribute("test", test);
        request.setAttribute("testDetailQuestionMap", testDetailQuestionMap);
        request.setAttribute("stdList", stdList);
        dispatch(request, response, "/view/student/quiz/review.jsp");
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