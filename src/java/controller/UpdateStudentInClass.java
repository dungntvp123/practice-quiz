package controller;

import java.io.IOException;
import java.io.PrintWriter;

import dao.ClassDAO;
import entity.StudentClassDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateStudentInClass", value = "/admin/UpdateStudentInClass")
public class UpdateStudentInClass extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int id = Integer.parseInt(request.getParameter("ClassWithIdToChange"));

            String[] studentIDStrings = request.getParameterValues("StudentIDToGet");
            int[] studentIDs = new int[studentIDStrings.length];
            System.out.println(studentIDStrings.length);
            if (studentIDStrings.length >= 15 && studentIDStrings.length <= 30) {
                int m = ClassDAO.getInstance().RemoveAllStudentInClassByClassID(id);
                for (int i = 0; i < studentIDStrings.length; i++) {
                    studentIDs[i] = Integer.parseInt(studentIDStrings[i]);
                    System.out.println(studentIDs[i]);
                    StudentClassDetail scl = new StudentClassDetail(studentIDs[i], id, 1);
                    int n = ClassDAO.getInstance().setStudentClassDetail(scl);
                }
                request.getSession().removeAttribute("ErrorUpdateNumberOfstudent");
                response.sendRedirect(request.getContextPath() + "/admin/ShowUpdateNumberStudent?ClassWithIdToChange=" + id);
            }else {
                String error= "Erorr";
                request.getSession().setAttribute("ErrorUpdateNumberOfstudent",error);
                response.sendRedirect(request.getContextPath() + "/admin/ShowUpdateNumberStudent?ClassWithIdToChange=" + id);
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