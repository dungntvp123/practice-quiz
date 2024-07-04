/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.QuestionDAO;
import dao.SelectionDAO;
import dao.TestDAO;
import entity.Question;
import entity.Selection;
import entity.TestDetail;
import entity.User;
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
import utility.WebRedirectionUtil;

/**
 *
 * @author Asus
 */
@WebServlet(name = "LecturePreviewQuizController", urlPatterns = {"/teacher/preview-quiz"})
public class LecturePreviewQuizController extends HttpServlet {

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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        Integer testid = Integer.parseInt(request.getParameter("quizID"));
        entity.Test t = TestDAO.getInstance().getTest(testid);
        long duration = t.getDuration();

        List<Question> list = QuestionDAO.getInstance().loadQuestion(testid);
        Map<Question, List<Selection>> test = SelectionDAO.getInstance().loadSelection(list);
        request.getSession().setAttribute("test", test);
        request.getSession().setAttribute("testid", testid);
        long start = System.currentTimeMillis();
        long expectedTimeOut = start + duration * 1000;
        Timestamp finishedtime = new Timestamp(expectedTimeOut);
        request.getSession().setAttribute("finishedtime", Utility.changDateFormat((finishedtime.before(t.getFinishedTime()) ? finishedtime : t.getFinishedTime())));
        WebRedirectionUtil.dispatch(request, response, "/view/lecture/quiz/preview_quiz.jsp");
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
        Map<Question, List<Selection>> test = (Map) request.getSession(false).getAttribute("test");
        Integer testid = (Integer) request.getSession(false).getAttribute("testid");
        request.getSession(false).removeAttribute("test");
        request.getSession(false).removeAttribute("testid");
        List<Question> questions = new ArrayList(test.keySet());
        if (cookies == null) {

        } else {
            double score = 0;
            for (Cookie cookie : cookies) {
                if (Utility.isNumber(cookie.getName())) {
                    Question question = questions.get(Integer.parseInt(cookie.getName()) - 1);

                    TestDetail td = TestDAO.getInstance().getTestDetail(testid, question.getId());
                    double point = Utility.calculateCoefficent(cookie.getValue(), SelectionDAO.getInstance().getCorrectAnswer(question.getId())) * td.getCoefficient() * 10;
                    score += point;

                }

            }

            request.setAttribute("score", (double)Math.round(score * 10) / 10.0);
            WebRedirectionUtil.dispatch(request, response, "/utils/invalidPreviewCookie.jsp");
        }
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
