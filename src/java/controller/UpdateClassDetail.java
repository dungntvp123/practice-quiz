package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import dao.ClassDAO;
import entity.Class;
import entity.StudentClassDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateClassDetail", value = "/admin/UpdateClassDetail")
public class UpdateClassDetail extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {


            String ClassName = request.getParameter("ClassName");

            String SubjectName = request.getParameter("SubjectName");
            int subjectID = Integer.parseInt(request.getParameter("SubjectName"));

            int IdofLecture = Integer.parseInt(request.getParameter("LectureID"));

            Date startTime = Date.valueOf(request.getParameter("StartDateSubject"));
            Date finishedTime = Date.valueOf(request.getParameter("EndDateSubject"));

            int ClassID = Integer.parseInt(request.getParameter("ClassID"));

            int status = Integer.parseInt(request.getParameter("Status"));

            int n = ClassDAO.getInstance().updateClassByClassID(ClassID, ClassName, subjectID, IdofLecture, startTime, finishedTime, status);
            String[] studentStatusStrings = request.getParameterValues("statusOfStudentInClass");
            int[] studentStatusS = new int[studentStatusStrings.length];
            String[] studentIDStrings = request.getParameterValues("IdOfStudentToChange");
            int[] studentIDs = new int[studentIDStrings.length];
            for (int i = 0; i < studentStatusStrings.length; i++) {
                studentStatusS[i] = Integer.parseInt(studentStatusStrings[i]);
                // System.out.println(studentStatusS[i]);

                studentIDs[i] = Integer.parseInt(studentIDStrings[i]);
                //System.out.println("day la std "+studentIDs[i]);
                int m = ClassDAO.getInstance().updateStudentStatus(ClassID, studentIDs[i], studentStatusS[i]);
                System.out.println(m);
            }

            response.sendRedirect(request.getContextPath()+"/admin/classes");


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