package controller;

import dao.RecorderDAO;
import java.io.IOException;
import java.io.PrintWriter;

import dao.SubjectDAO;
import entity.Recorder;
import entity.Subject;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import utility.WebRedirectionUtil;
import static utility.WebRedirectionUtil.dispatch;

@WebServlet(name = "ManageSubjectController", value = {"/admin/subjects/add", "/admin/subjects/edit"})
public class ManageSubjectController extends HttpServlet {

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
        String uri = request.getRequestURI();
        char mode = 'E';
        Subject subject = new Subject();
        //Add
        if (uri.endsWith("add")) {
            mode = 'A';
        } else {//Edit
            String id = request.getParameter("id");
            if (id == null) {
                WebRedirectionUtil.send404(request, response);
                return;
            }
            subject = SubjectDAO.getInstance().getSubject(Integer.parseInt(id));
        }
        request.setAttribute("subject", subject);
        request.setAttribute("mode", mode);
        WebRedirectionUtil.dispatch(request, response, "/view/admin/subject/manage_subject.jsp");
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
        String uri = request.getRequestURI();
        //Add
        String name = request.getParameter("name");
        int numofchap = Integer.parseInt(request.getParameter("chapters"));
        Subject sub = new Subject(name, numofchap);
        if (uri.endsWith("add")) {
            if (SubjectDAO.getInstance().getSubject(name.trim()).size() > 0) {
                String nameErr = "Subject already exist!";
                request.setAttribute("nameErr", nameErr);
                Subject subject = new Subject();
                char mode = 'A';
                request.setAttribute("mode", mode);
                request.setAttribute("subject", subject);
                WebRedirectionUtil.dispatch(request, response, "/view/admin/subject/manage_subject.jsp");
            } else {
                SubjectDAO.getInstance().addSubject(sub);
                Recorder record = new Recorder(currentUser.getId(), "created", 1, new Timestamp(System.currentTimeMillis()));
                RecorderDAO.getInstance().newRecord(record);
                response.sendRedirect(request.getContextPath() + "/admin/subjects");
            }
        } else { //Edit
            int id = Integer.parseInt(request.getParameter("id"));
            SubjectDAO.getInstance().updateSubject(sub, id);
            Recorder record = new Recorder(currentUser.getId(), "updated", 1, new Timestamp(System.currentTimeMillis()));
            RecorderDAO.getInstance().newRecord(record);
            response.sendRedirect(request.getContextPath() + "/admin/subjects");
        }
        
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
