package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import dao.ClassDAO;
import dao.LectureDAO;
import dao.NotificationDAO;
import entity.Class;
import entity.Lecture;
import entity.Notification;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utility.WebRedirectionUtil;

@WebServlet(name = "NotificationListController", value = {"/student/notifications","/teacher/notifications"})
public class NotificationListController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String class_id = request.getParameter("class");
        if (class_id == null){
            WebRedirectionUtil.send404(request, response);
            return;
        }
        Class targetClass = ClassDAO.getInstance().getClassFromId(Integer.parseInt(class_id));
        if (targetClass == null){
            WebRedirectionUtil.send404(request, response);
        }
        String target;
        if (user.getRole() == User.TEACHER){
            Lecture lecture = LectureDAO.getInstance().getLecture(user.getId());
            if (lecture.getId() != targetClass.getLectureId()){
                WebRedirectionUtil.send403(request, response);
                return;
            }
            target = "/view/lecture/notification_list.jsp";
        } else if (user.getRole() == User.STUDENT) {
            if (!ClassDAO.getInstance().isStudentUserInClass(user.getId(),targetClass.getId())){
                WebRedirectionUtil.send403(request, response);
                return;
            }
            target = "/view/student/notification_list.jsp";
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/notifications");
            return;
        }
        Vector<Notification> notifications = NotificationDAO.getInstance().getNotifications(targetClass.getId());
        request.setAttribute("notifications",notifications);
        request.setAttribute("targetClass",targetClass);
        WebRedirectionUtil.dispatch(request,response,target);
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