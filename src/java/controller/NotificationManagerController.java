package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

import dao.ClassDAO;
import dao.NotificationDAO;
import entity.Notification;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utility.WebRedirectionUtil;

@WebServlet(name = "NotificationManagerController", value = {"/teacher/notifications/manage","/admin/notifications/manage"})
public class NotificationManagerController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action.equals("delete")){
            //TODO Checks
            try {
                String notif = request.getParameter("notif");
                NotificationDAO.getInstance().softRemoveNotification(Integer.parseInt(notif));
                String classNoti = request.getParameter("class");
                if(classNoti !=null)
                    response.sendRedirect(request.getContextPath() + "/teacher/notifications?class=" +classNoti);
                else{
                    response.sendRedirect(request.getContextPath() + "/admin/notification");
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
        } else if (action.equals("add")){
            try {
                request.setAttribute("targetClass", ClassDAO.getInstance().getClassFromId(Integer.parseInt(request.getParameter("class"))));
                WebRedirectionUtil.dispatch(request,response,"/view/lecture/create_notification.jsp");
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
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
        User user = (User) request.getSession().getAttribute("user");
        Notification notification = new Notification(
                user.getId(),
                Integer.parseInt(request.getParameter("class")),
                request.getParameter("title"),
                request.getParameter("content"),
                new Timestamp(new Date().getTime()),
                1
        );
        NotificationDAO.getInstance().addNotification(notification);
        response.sendRedirect(request.getContextPath() + "/teacher/notifications?class=" + request.getParameter("class"));
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