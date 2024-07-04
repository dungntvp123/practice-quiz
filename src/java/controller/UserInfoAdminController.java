package controller;

import java.io.IOException;
import java.io.PrintWriter;

import dao.AdminDAO;
import dao.LectureDAO;
import dao.StudentDAO;
import dao.UserDAO;
import entity.User;
import entity.interfaces.UserExtension;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utility.WebRedirectionUtil;

@WebServlet(name = "UserInfoAdminController", value = "/admin/users/info")
public class UserInfoAdminController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        User user = null;
        UserExtension extension = null;
        String userId = request.getParameter("user_id");
        String studentId = request.getParameter("student_id");
        String lectureId = request.getParameter("lecture_id");
        String adminId = request.getParameter("admin_id");
        if (userId != null){
            user = UserDAO.getInstance().getUser(userId);
            switch (user.getRole()){
                case User.ADMINISTRATOR:
                    extension = AdminDAO.getInstance().getAdmin(user.getId());
                    break;
                case User.TEACHER:
                    extension = LectureDAO.getInstance().getLecture(user.getId());
                    break;
                case User.STUDENT:
                    extension = StudentDAO.getInstance().getStudent(user.getId());
                    break;
                default:
                    WebRedirectionUtil.sendErrorWithMessage(request,response,400,"Bad Request");
                    return;
            }
        }
//        if (studentId != null){
//            if (user != null){
//                WebRedirectionUtil.sendErrorWithMessage(request,response,400,"Bad Request");
//                return;
//            }
//
//        }
        request.setAttribute("user",user);
        request.setAttribute("extension",extension);
        WebRedirectionUtil.dispatch(request,response,"/view/admin/user/user_info.jsp");
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