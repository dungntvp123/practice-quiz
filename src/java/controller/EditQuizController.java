package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Vector;

import dao.ClassDAO;
import dao.InformalDAO;
import dao.LectureDAO;
import dao.QuestionDAO;
import dao.RecorderDAO;
import dao.TestDAO;
import entity.*;
import entity.Test;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utility.group.Pair;

import static utility.WebRedirectionUtil.*;

@WebServlet(name = "EditQuizController", value = "/teacher/edit_quiz")
public class EditQuizController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            Map<String, String[]> map = request.getParameterMap();
//            for (String k : map.keySet()) {
//                String[] v = map.get(k);
//                out.printf("<p> %s: %s<p>", k, Arrays.toString(v));
//            }
//        }
        //Handler
        User user = (User) request.getSession().getAttribute("user");
        Timestamp current = new Timestamp(System.currentTimeMillis());
        String name = request.getParameter("name");
        long duration = Long.parseLong(request.getParameter("duration"));
        //Datetime
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Timestamp startTime = null, finishedTime = null;
        try {
            startTime = new Timestamp(sdf.parse(request.getParameter("StartDate")).getTime());
            finishedTime = new Timestamp(sdf.parse(request.getParameter("EndDate")).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String[] questions = request.getParameterValues("questions");
        String[] coefficients = request.getParameterValues("coefficient");
        String allow = request.getParameter("allow-review");
        int numOfQues = questions.length;
        double coefficientQ = 0; //TODO Something
        int classId = Integer.parseInt(request.getParameter("classID"));
        int status = 1;
        Test test = new Test(name, duration, startTime, finishedTime, numOfQues, coefficientQ, classId, status, allow != null);
        Test testWithID = TestDAO.getInstance().addTestReturnID(test);
        RecorderDAO.getInstance().newRecord(new Recorder(user.getId(), "created", 4, current));
        int quizID = testWithID.getId();
        System.out.println("quizID =" + quizID);
        //Add Test Detail
        if (questions.length != coefficients.length) {
            //utility.Check
            throw new RuntimeException("questions size does not match coefficients size. (" +
                    questions.length + " vs. " + coefficients.length + ").");
        }
        for (int i = 0; i < questions.length; i++){
            int questionID = Integer.parseInt(questions[i]);
            double coefficient = Double.parseDouble(coefficients[i]);     
            TestDetail td = new TestDetail(quizID,questionID,coefficient);
            TestDAO.getInstance().addTestDetail(td);
        }
        response.sendRedirect(request.getContextPath()+"/teacher/quizzes?class="+classId);
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

        User user = (User) request.getSession().getAttribute("user");
        String classIDString = request.getParameter("classID");
        String quizIDString = request.getParameter("quizID");
        entity.Class currentClass = null;
        Lecture lecture = LectureDAO.getInstance().getLecture(user.getId());
        try {
            int classID = Integer.parseInt(classIDString);
            currentClass = ClassDAO.getInstance().getClassFromId(classID);
            if (currentClass == null) {
                send404(request, response);
                return;
            }
            if (currentClass.getLectureId() != lecture.getId()) {
                send403(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            send404(request, response);
            return;
        }
        Test test = null;
        Vector<Map<String, Object>> bank = QuestionDAO.getInstance().getQuestionBankOfSubject(currentClass.getSubjectId());
        Vector<Pair<Integer, Double>> inQuiz = new Vector<>();

        //utility.Check if there is quiz
        int quizID = 0;
        try {
            quizID = Integer.parseInt(quizIDString);
            test = TestDAO.getInstance().getTest(quizID);
            if (test != null) {
                inQuiz = QuestionDAO.getInstance().getQuestionsOfQuiz(test.getId());
            }
        } catch (NumberFormatException ex) {
        }
        //Sol
        for (Pair<Integer, Double> p : inQuiz) {
            System.out.println(p.getFirst() + " - " + p.getSecond());
        }
        //Set Attribute
        request.setAttribute("class", currentClass);
        request.setAttribute("quiz", test);
        request.setAttribute("bank", bank);
        request.setAttribute("inQuiz", inQuiz);
        //Debug Print

        //Dispatch
        dispatch(request, response, "/view/lecture/quiz/CreateQuizForm.jsp");
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