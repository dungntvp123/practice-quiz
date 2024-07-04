package controller;

import java.io.IOException;
import java.io.PrintWriter;

import dao.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ChangePassForLoggedUser", value = {"/ChangePassForLoggedUser", "/admin/ChangePassForLoggedUser"})
public class ChangePassForLoggedUser extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String CurrentPassword = request.getParameter("CurentPassword");
            String lasspass = request.getParameter("lasspass");
            String IdOfUserToChange = request.getParameter("IdOfUserToChange");
            if (CurrentPassword.equals(lasspass)) {
                String newPassWord = request.getParameter("newpassword");
                String UsernameOfLoggedUser = request.getParameter("UsernameOfLoggedUser");
                System.out.println(newPassWord);
                System.out.println(UsernameOfLoggedUser);
                int n = UserDAO.getInstance().changePasswordForLoggedUser(UsernameOfLoggedUser, newPassWord);
                System.out.println(n);
                dispatch(request, response, "view/ChangePassSuccess.jsp");
            }else {
                String ErrorWhenChangePass = "Error";
                request.getSession().setAttribute("ErrorWhenChangePass",ErrorWhenChangePass);
                response.sendRedirect(request.getContextPath()+"/ShowChangePassForLoggedUser?IdOfUser="+IdOfUserToChange);
            }
        }
    }
    void dispatch(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
        RequestDispatcher disp = request.getRequestDispatcher(url);
        disp.forward(request, response);
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