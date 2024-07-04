package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import dao.ClassDAO;
import dao.LectureDAO;
import dao.SubjectDAO;
import entity.Class;
import entity.Lecture;
import entity.Subject;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static utility.WebRedirectionUtil.dispatch;
import static utility.WebRedirectionUtil.send404;

@WebServlet(name = "SubjectListController", value = {"/student/subjects","/teacher/subjects"})
public class SubjectListController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        User user = (User) request.getSession().getAttribute("user");
        if (user.getRole() == User.TEACHER){
            processLecture(request, response);
        } else if (user.getRole() == User.STUDENT){
            processStudent(request, response);
        } else {
            send404(request, response);
        }

    }

    private void processStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String query = request.getParameter("query");
        if (query == null){
            query = "";
        }
        Vector<Subject> validSubjects = new Vector<>();
        Vector<Subject> allSubject = SubjectDAO.getInstance().getAllSubject();
        for (Subject subject : allSubject){
            if (!subject.getName().contains(query)) continue;
            Vector<Class> classOfSubject = ClassDAO.getInstance().getClassOfSubject(subject.getId());
            for (Class c : classOfSubject){
                if (ClassDAO.getInstance().isStudentUserInClass(user.getId(),c.getId())){
                    validSubjects.add(subject);
                    break;
                }
            }
        }
        request.setAttribute("allSubject",validSubjects);
        dispatch(request,response,"/view/common/subject/SubjectList.jsp");
    }

    private void processLecture(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Lecture lecture = LectureDAO.getInstance().getLecture(user.getId());
        Vector<Subject> validSubjects = new Vector<>();
        Vector<Subject> allSubject = SubjectDAO.getInstance().getAllSubject();
        String query = request.getParameter("query");
        if (query == null){
            query = "";
        }
        for (Subject subject : allSubject){
            if (!subject.getName().contains(query)) continue;
            Vector<Class> classOfSubject = ClassDAO.getInstance().getClassOfSubject(subject.getId());
            for (Class c : classOfSubject){
                if (c.getLectureId() == lecture.getId()){
                    validSubjects.add(subject);
                    break;
                }
            }
        }
        request.setAttribute("allSubject",validSubjects);
        dispatch(request,response,"/view/common/subject/SubjectList.jsp");
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