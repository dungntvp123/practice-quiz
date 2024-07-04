package controller;

import java.io.IOException;
import java.io.PrintWriter;

import dao.ClassDAO;
import dao.NotificationDAO;
import dao.UserDAO;
import entity.Notification;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utility.WebRedirectionUtil;

@WebServlet(name = "NotificationDetailController", value = "/user/notification")
public class NotificationDetailController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        User user = (User) request.getSession().getAttribute("user");
        NotificationDAO dao = NotificationDAO.getInstance();
        String id = request.getParameter("id");
        if (id == null){
            WebRedirectionUtil.send404(request,response);
            return;
        }
        Notification notification = dao.getNotification(Integer.parseInt(id));
        if (notification == null){
            WebRedirectionUtil.send404(request,response);
            return;
        }
        entity.Class currentClass = ClassDAO.getInstance().getClassFromId(notification.getClassID());
        User publisher = UserDAO.getInstance().getUser(notification.getPublisher());
        request.setAttribute("notification",notification);
        request.setAttribute("currentClass",currentClass);
        request.setAttribute("publisher",publisher);
        WebRedirectionUtil.dispatch(request,response,"/view/common/notification.jsp");
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