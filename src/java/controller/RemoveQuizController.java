package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import dao.ClassDAO;
import dao.InformalDAO;
import dao.LectureDAO;
import dao.RecorderDAO;
import dao.TestDAO;
import entity.Lecture;
import entity.Question;
import entity.Recorder;
import entity.Test;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utility.WebRedirectionUtil;

import static utility.WebRedirectionUtil.sendErrorWithMessage;

@WebServlet(name = "RemoveQuizController", value = "/teacher/remove_quiz")
public class RemoveQuizController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        int quizID = Integer.parseInt(request.getParameter("quizID"));
        int classID = Integer.parseInt(request.getParameter("classID"));

        Test test = TestDAO.getInstance().getTest(quizID);
        User user = (User) request.getSession().getAttribute("user");
        Timestamp current = new Timestamp(System.currentTimeMillis());
        Lecture lecture = LectureDAO.getInstance().getLecture(user.getId());
        entity.Class currentClass = ClassDAO.getInstance().getClassFromId(classID);
        //Class does not exist? (Bad param)
        if (currentClass == null){
            sendErrorWithMessage(request,response,400,"Bad Request");
        }
        //Does that quiz belongs to you?
        else if (currentClass.getId() != test.getClassId() || currentClass.getLectureId() != lecture.getId()){
            response.sendRedirect(request.getContextPath()+"/teacher/quizzes?class="+classID+"&del=fail_notyours&target="+quizID);
        }
        //Is it after start time?
        else if (new Date().after(test.getStartTime())){
            response.sendRedirect(request.getContextPath()+"/teacher/quizzes?class="+classID+"&del=fail_started&target="+quizID);
        }
        // Okay, let's delete!
        else {
            int n = TestDAO.getInstance().removeTest(test.getId());
            RecorderDAO.getInstance().newRecord(new Recorder(user.getId(), "removed", 4, current));
            //Did nothing?
            if (n==0){
                response.sendRedirect(request.getContextPath()+"/teacher/quizzes?class="+classID+"&del=fail_nochanges&target="+quizID);
            } else {
                response.sendRedirect(request.getContextPath()+"/teacher/quizzes?class="+classID+"&del=success&target="+quizID);
            }
        }
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
        sendErrorWithMessage(request,response,405,"Method Not Allowed.");
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