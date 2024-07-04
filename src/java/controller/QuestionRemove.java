/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.LectureDAO;
import java.io.IOException;
import java.io.PrintWriter;

import dao.QuestionDAO;
import dao.RecorderDAO;
import entity.Lecture;
import entity.Question;
import entity.Recorder;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

/**
 *
 * @author ACER NITRO
 */
@WebServlet(name="QuestionRemove", urlPatterns={"/QuestionRemove"})
public class QuestionRemove extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("questionID"));
        //Verify the ability to remove question
        Question question = QuestionDAO.getInstance().getQuestion(id);
        User user = (User) request.getSession().getAttribute("user");
        Timestamp current = new Timestamp(System.currentTimeMillis());
        Lecture lecture = LectureDAO.getInstance().getLecture(user.getId());
        if (question == null){
            response.sendRedirect(request.getContextPath() +  "/teacher/bank?del=fail_nochanges&target="+id);
        } else if (question.getLectureid() != lecture.getId()) {
            response.sendRedirect(request.getContextPath() +  "/teacher/bank?del=fail_notyours&target="+id);
        } else if (!QuestionDAO.getInstance().isQuestionInUsed(id)){
            int n = QuestionDAO.getInstance().removeQuestion(id);
            RecorderDAO.getInstance().newRecord(new Recorder(user.getId(), "removed", 2, current));
            if (n != 0){
                response.sendRedirect(request.getContextPath() +  "/teacher/bank?del=success&target="+id);
            } else {
                response.sendRedirect(request.getContextPath() +  "/teacher/bank?del=fail_nochanges&target="+id);
            }
        } else {
            response.sendRedirect(request.getContextPath() +  "/teacher/bank?del=fail_invalid&target="+id);
        }

    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
