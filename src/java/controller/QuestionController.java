/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.InformalDAO;
import dao.LectureDAO;
import dao.QuestionDAO;
import dao.RecorderDAO;
import dao.SelectionDAO;
import dao.SubjectDAO;
import entity.*;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

import java.util.Vector;
import static utility.WebRedirectionUtil.dispatch;
import static utility.WebRedirectionUtil.send403;

/**
 *
 * @author ACER NITRO
 */
@WebServlet(name = "QuestionController", urlPatterns = {"/teacher/newQuestion"})
public class QuestionController extends HttpServlet {

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
        Vector<Subject> listSubject = SubjectDAO.getInstance().getSubject("");
        request.setAttribute("listSubject", listSubject);
        //Clone?
        String questionID = request.getParameter("questionID");
        try {
            int qid = Integer.parseInt(questionID);
            ExtendedQuestion question = QuestionDAO.getInstance().getQuestionFromID(qid);
            if (question != null) {
                //utility.Check if you can clone
                User user = (User) request.getSession().getAttribute("user");
                Lecture lecture = LectureDAO.getInstance().getLecture(user.getId());
                if (question.getLectureid() != lecture.getId()) {
                    System.out.println(question.getTeacherID() + " - " + lecture.getId());
                    send403(request, response);
                    return;
                }
                //Allow
                Vector<Selection> selections = QuestionDAO.getInstance().getSelectionsOfQuestion(qid);
                request.setAttribute("selections", selections);
                request.setAttribute("question", question);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        dispatch(request, response, "/view/lecture/question/Add_Question_To_Bank.jsp");
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
        Integer chapter = Integer.parseInt(request.getParameter("chapter"));
        Integer subjectid = Integer.parseInt(request.getParameter("subject"));
        String description = request.getParameter("question-title");
        String[] answers = request.getParameterValues("answerForThisQuestion");
        String[] weight = request.getParameterValues("Weight");
        
        User user = (User) request.getSession(false).getAttribute("user");
        Timestamp current = new Timestamp(System.currentTimeMillis());
        Integer lectureid = LectureDAO.getInstance().getLecture(user.getId()).getId();
        int questionid = QuestionDAO.getInstance().getQuestionInsertId(new Question(subjectid, description, chapter, lectureid, 1));
        RecorderDAO.getInstance().newRecord(new Recorder(user.getId(), "created", 2, current));
        for (int i = 0; i < answers.length; ++i) {
            SelectionDAO.getInstance().addSelection(new Selection(questionid, Double.parseDouble(weight[i]) / 100.0, answers[i], "" + (char) ('A' + i)));
        }

        response.sendRedirect(request.getContextPath() + "/teacher/bank");
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
