package controller;

import dao.ClassDAO;
import dao.NotificationDAO;
import entity.Class;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utility.group.Pair;

import java.io.IOException;
import java.util.Vector;

import static utility.WebRedirectionUtil.dispatch;

@WebServlet(name = "TeacherClassPageController", value = "/teacher/classes")
public class TeacherClassPageController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String subject = request.getParameter("subject");
        Vector<Class> classOfLecture;
        if (subject == null){
            classOfLecture = ClassDAO.getInstance().getClassOfLecture(user);
        } else {
            classOfLecture = ClassDAO.getInstance().getClassOfLecture(user,Integer.parseInt(subject));
        }
        //Filter
        String query = request.getParameter("query");
        if (query != null){
            for (int i = 0; i < classOfLecture.size(); i++){
                Class c = classOfLecture.get(i);
                if (c.getName().contains(query)){
                    i++;
                } else {
                    classOfLecture.remove(i);
                }
            }
        }
        request.setAttribute("classOfLecture",classOfLecture);
        dispatch(request,response,"/view/lecture/class/ClassListLecture.jsp");
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