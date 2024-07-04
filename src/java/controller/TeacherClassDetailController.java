package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import dao.ClassDAO;
import dao.LectureDAO;
import dao.NotificationDAO;
import dao.TestDAO;
import entity.Class;
import entity.Lecture;
import entity.Test;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static utility.WebRedirectionUtil.dispatch;
import static utility.WebRedirectionUtil.sendErrorWithMessage;

@WebServlet(name = "TeacherClassDetailController", value = "/teacher/quizzes")
public class TeacherClassDetailController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        User user = (User) request.getSession().getAttribute("user");
        int classId = Integer.parseInt(request.getParameter("class"));

        entity.Class targetClass = ClassDAO.getInstance().getClassFromId(classId);
        Lecture lecture = LectureDAO.getInstance().getLecture(user.getId());
        if (lecture.getId() != targetClass.getLectureId()){
            sendErrorWithMessage(request,response,403,"You cannot access this page because this resource is not available to you.");
            return;
        }
        request.setAttribute("class",targetClass);

        Vector<Test> tests = TestDAO.getInstance().getTestOfClass(targetClass.getId());
        request.setAttribute("tests",tests);
        request.setAttribute("notifications", NotificationDAO.getInstance().getNotifications(targetClass.getId(),5));
        dispatch(request,response,"/view/lecture/class/ClassPageLecture.jsp");
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