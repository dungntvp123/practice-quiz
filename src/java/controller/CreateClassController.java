package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

import dao.ClassDAO;
import entity.Class;
import entity.StudentClassDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "controller.CreateClassController", value = "/admin/classes/add")
public class CreateClassController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            int TeacheID = Integer.parseInt(request.getParameter("LectureID"));
            String ClassName = request.getParameter("ClassName");
            int SubjectName = Integer.parseInt(request.getParameter("SubjectName"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date startTime = Date.valueOf(request.getParameter("StartDateSubject"));
            Date finishedTime = Date.valueOf(request.getParameter("EndDateSubject"));
            String[] studentIDStrings = request.getParameterValues("StudentIDToGet");
            System.out.println(studentIDStrings.length);
            if (studentIDStrings.length >= 15 && studentIDStrings.length <= 30) {
                Class className = new Class(ClassName, SubjectName, TeacheID, startTime, finishedTime, 1);
                Class newClass = ClassDAO.getInstance().addClassReturnID(className);
                int ClassID = newClass.getId();
                System.out.println(ClassID);

                int[] studentIDs = new int[studentIDStrings.length];


                for (int i = 0; i < studentIDStrings.length; i++) {
                    studentIDs[i] = Integer.parseInt(studentIDStrings[i]);
                    System.out.println(studentIDs[i]);
                    StudentClassDetail scl = new StudentClassDetail(studentIDs[i], ClassID, 1);
                    int n = ClassDAO.getInstance().setStudentClassDetail(scl);
                }
                request.getSession().removeAttribute("errorWhenCreateClass");
                response.sendRedirect(request.getContextPath() + "/admin/classes");
            } else {
                String errorWhenCreateClass = "The max is 30 and min is 15";
                request.getSession().setAttribute("errorWhenCreateClass", errorWhenCreateClass);
                response.sendRedirect(request.getContextPath() + "/admin/ShowCreateClassController");
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