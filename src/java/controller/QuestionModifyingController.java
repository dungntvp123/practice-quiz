package controller;

import dao.LectureDAO;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

import dao.QuestionDAO;
import entity.ExtendedQuestion;
import entity.Lecture;
import entity.Selection;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static utility.WebRedirectionUtil.dispatch;
import static utility.WebRedirectionUtil.sendErrorWithMessage;

@WebServlet(name = "QuestionModifyingController", value = "/teacher/question_old")
public class QuestionModifyingController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Parameter Printout
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String key : parameterMap.keySet()){
            String[] strings = parameterMap.get(key);
            System.out.print("Key = " + key + ", Values = ");
            for (String s : strings){
                System.out.print(s + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------------");
        // Parameter Printout
        String questionID = request.getParameter("questionID");
        ExtendedQuestion question;
        try {
            int qid = Integer.parseInt(questionID);
            question = QuestionDAO.getInstance().getQuestionFromID(qid);
        } catch (NumberFormatException ex){
            response.sendError(404);
            return;
        }
        if (question == null){
            response.sendError(404);
            return;
        }
        //utility.Check if the lecture is the user
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Lecture lecture = LectureDAO.getInstance().getLecture(user.getId());
        if (lecture.getId() != question.getTeacherID()){
            sendErrorWithMessage(request,response,403,"You cannot access this page because this resource is not available to you.");
            return;
        }
        //Okay
        Vector<Selection> selectionsOfQuestion = QuestionDAO.getInstance().getSelectionsOfQuestion(question.getId());
        request.setAttribute("question",question);
        request.setAttribute("selections",selectionsOfQuestion);
        int correct = 0;
        for (Selection sel : selectionsOfQuestion){
            if (sel.getCoefficent() != 0){
                correct++;
            }
        }
        request.setAttribute("correctAnswers",correct);
        dispatch(request,response,"/view/Edit_Question.jsp");
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