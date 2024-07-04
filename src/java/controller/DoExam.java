/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.InformalDAO;
import dao.QuestionDAO;
import dao.RecorderDAO;
import dao.SelectionDAO;
import dao.StudentDAO;
import dao.StudentTestDAO;
import dao.TestDAO;
import entity.Question;
import entity.Recorder;
import entity.Selection;
import entity.StudentTest;
import entity.StudentTestDetail;
import entity.TestDetail;
import entity.User;
import entity.Test;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import utility.Utility;

/**
 *
 * @author Asus
 */
@WebServlet(name = "DoExam", urlPatterns = {"/student/doexam"})
public class DoExam extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

        }
    }

    void dispatch(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
        RequestDispatcher disp = request.getRequestDispatcher(url);
        disp.forward(request, response);
    }// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer testid = Integer.parseInt(request.getParameter("testid"));
        Test t = TestDAO.getInstance().getTest(testid);
        long duration = t.getDuration();

        User user = (User) request.getSession().getAttribute("user");
        int studentid = StudentDAO.getInstance().getStudent(user.getId()).getId();
        StudentTest studentTest = StudentTestDAO.getInstance().getStudentTest(studentid, testid);
        String fintime = null;
        if (studentTest.getId() != 0) {
            response.sendRedirect("/SWP/student/quizzes");
        } else {
            
            if (request.getSession().getAttribute("test") == null) {
                List<Question> list = QuestionDAO.getInstance().loadQuestion(testid);
                Map<Question, List<Selection>> test = SelectionDAO.getInstance().loadSelection(list);
                InformalDAO.getInstance().listAll(list);

                request.getSession().setAttribute("test", test);
                long start = System.currentTimeMillis();
                long expectedTimeOut = start + duration * 1000;
                Timestamp finishedtime = new Timestamp(expectedTimeOut);
                studentTest.setStartTime(new Timestamp(start));
                studentTest.setStudentId(studentid);
                studentTest.setTestId(testid);
                request.getSession().setAttribute("studentTest", studentTest);
                Timestamp finishedTime = finishedtime.before(t.getFinishedTime()) ? finishedtime : t.getFinishedTime();
                fintime = Long.toString(finishedTime.getTime());
                request.getSession().setAttribute("fintime", finishedTime.getTime());

            }
            Cookie[] cookies = request.getCookies();
            boolean con = true;
            for (Cookie c : cookies) {
                if (c.getName().equals("fintime")) {
                    fintime = c.getValue();
                    if(System.currentTimeMillis() > Long.parseLong(fintime)) {
                        doPost(request, response);
                        con = false;
                        break;
                    }
                }
            }
            
            if (con) {
                request.getSession().setAttribute("finishedtime", Utility.changDateFormat(new Timestamp(Long.parseLong(fintime))));
                dispatch(request, response, "/view/student/quiz/takequiz.jsp");
            }
        }

//        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Timestamp finishedTime = new Timestamp(System.currentTimeMillis());
        User user = (User) request.getSession().getAttribute("user");
        StudentTest studentTest = (StudentTest) request.getSession(false).getAttribute("studentTest");
        studentTest.setFinishedTime(finishedTime);
        Map<Question, List<Selection>> test = (Map) request.getSession(false).getAttribute("test");

        StudentTestDAO.getInstance().setStudentTest(studentTest);
        RecorderDAO.getInstance().newRecord(new Recorder(user.getId(), "created", 5, finishedTime));

        request.getSession(false).removeAttribute("test");
        request.getSession(false).removeAttribute("studentTest");
        List<Question> questions = new ArrayList(test.keySet());
        if (cookies == null) {

        } else {
            double score = 0;
            for (Cookie cookie : cookies) {
                if (Utility.isNumber(cookie.getName())) {
                    Question question = questions.get(Integer.parseInt(cookie.getName()) - 1);

                    TestDetail td = TestDAO.getInstance().getTestDetail(studentTest.getTestId(), question.getId());
                    double point = Utility.calculateCoefficent(cookie.getValue(), SelectionDAO.getInstance().getCorrectAnswer(question.getId())) * td.getCoefficient() * 10;
                    score += point;
                    StudentTestDetail std = new StudentTestDetail(StudentTestDAO.getInstance().getStudentTest(studentTest.getStudentId(), studentTest.getTestId()).getId(), td.getId(), cookie.getValue(), point);
                    StudentTestDAO.getInstance().setStudentTestDetail(std);
                }
            }
            studentTest.setTotalScore((double) Math.round(score * 10) / 10.0);
            StudentTestDAO.getInstance().updateStudentTest(studentTest);
            dispatch(request, response, "/utils/invalidCookie.jsp");
        }
//        processRequest(request, response);
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
