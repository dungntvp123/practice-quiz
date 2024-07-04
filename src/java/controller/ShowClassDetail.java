package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import dao.ClassDAO;
import dao.LectureDAO;
import dao.StudentDAO;
import dao.SubjectDAO;
import entity.*;
import entity.Class;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static utility.WebRedirectionUtil.dispatch;

@WebServlet(name = "ShowClassDetail", value = "/admin/ShowClassDetail")
public class ShowClassDetail extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String IdOfClassToView = request.getParameter("IdOfClassToView");
            Vector<StudentClassDetail> studentInClass = ClassDAO.getInstance().getAllStudentByClassID(IdOfClassToView);
            Class classDetail = ClassDAO.getInstance().getClassDetailByID(IdOfClassToView);
            request.setAttribute("studentInClass", studentInClass);
            request.setAttribute("classDetail", classDetail);
            Vector<Subject> listAllSubject = SubjectDAO.getInstance().getAllSubject();
            Vector<Lecture> listAllLecture = LectureDAO.getInstance().getAllLectures();
            Vector<Student> listAllStudent = StudentDAO.getInstance().getAllStudents();
            request.setAttribute("listAllSubject", listAllSubject);
            request.setAttribute("listAllLecture", listAllLecture);
            request.setAttribute("listAllStudent", listAllStudent);
            dispatch(request,response,"/view/admin/class/class_detail.jsp");
        }
    }

    void dispath(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
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