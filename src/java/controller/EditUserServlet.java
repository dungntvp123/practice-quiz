package controller;

import dao.LectureDAO;
import dao.RecorderDAO;
import dao.StudentDAO;
import java.io.IOException;
import java.io.PrintWriter;

import dao.UserDAO;
import entity.Recorder;
import entity.User;
import utility.Utility;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

import static utility.WebRedirectionUtil.dispatch;
import static utility.WebRedirectionUtil.send403;
import static utility.WebRedirectionUtil.send404;

@WebServlet(name = "EditUserServlet", value = "/admin/users/edit")
public class EditUserServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        User currentUser = (User) request.getSession(false).getAttribute("user");
        String id = request.getParameter("id");
        if (id == null) {
            send404(request, response);
            return;
        }
        if (currentUser.getId() == Integer.parseInt(id)) {
            send403(request, response);
            return;
        }
        User user = UserDAO.getInstance().getUser(Integer.parseInt(id));
        int status = Utility.getUserStatus(user.getId(), user.getRole());
        request.setAttribute("user", user);
        request.setAttribute("status", status);
        dispatch(request, response, "/view/admin/user/edit_user.jsp");
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
        User currentUser = (User) request.getSession(false).getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String passsword = request.getParameter("password");
        String firstname = request.getParameter("first-name");
        String lastname = request.getParameter("last-name");
        String email = request.getParameter("email");
        int role = Integer.parseInt(request.getParameter("role"));
        int status = Integer.parseInt(request.getParameter("status"));
        User user = new User(username, passsword, firstname, lastname, email, role);
        UserDAO.getInstance().updateUser(user, id);
        boolean isExistInLecture = LectureDAO.getInstance().getLecture(id).getId() != 0;
        boolean isExistInStudent = StudentDAO.getInstance().getStudent(id).getId() != 0;
        if (user.getRole() == 1) {
            if (isExistInLecture) {
                LectureDAO.getInstance().updateLecture(id, status);
            } else {
                LectureDAO.getInstance().setLecture(id, status);
            }
            Utility.setUserPrevRole(id, 2);
        }
        if (user.getRole() == 2) {
            if (isExistInStudent) {
                StudentDAO.getInstance().updateStudent(id, status);
            } else {
                StudentDAO.getInstance().setStudent(id, status);
            }
            Utility.setUserPrevRole(id, 1);
        }
        Recorder record = new Recorder(currentUser.getId(), "updated", 6, new Timestamp(System.currentTimeMillis()));
        RecorderDAO.getInstance().newRecord(record);
        response.sendRedirect(request.getContextPath() + "/admin/users");

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
