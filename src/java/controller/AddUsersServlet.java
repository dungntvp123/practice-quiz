package controller;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import dao.UserDAO;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.apache.commons.lang.StringEscapeUtils;
import utility.Utility;



import static utility.WebRedirectionUtil.dispatch;

@WebServlet(name = "AddUsersServlet", value = "/admin/users/add")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5
)
public class AddUsersServlet extends HttpServlet {

    private final String[] HEADERS = "Username,First Name,Last Name,Email,Role".split(",");

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
        //GET
        dispatch(request,response,"/view/admin/user/add_user.jsp");
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
        //POST
//        Enumeration<String> parameterNames = request.getParameterNames();
//        while (parameterNames.hasMoreElements()){
//            System.out.println(parameterNames.nextElement());
//        }
        //Reader
        Exception exception = null;
        Part part = request.getPart("file");
        String pwd = Utility.encode(request.getParameter("password"));
        if (!request.getParameter("password").matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$")){
            exception = new IllegalArgumentException("Invalid password. It should contain a special character like \"@ _ & %\", at least one number, and at least one uppercase character.");
            response.sendRedirect(request.getContextPath() + "/admin/users?error=" + URLEncoder.encode(exception.getMessage(), "UTF-8"));
            return;
        }
        InputStream stream = part.getInputStream();
        UserDAO userDAO = UserDAO.getInstance();
        userDAO.startConnection();
        try (Reader reader = new InputStreamReader(stream)) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .setHeader()
                    .setAllowMissingColumnNames(false)
                    .build();
            CSVParser records = csvFormat.parse(reader);
            List<User> users = new ArrayList<>();
            for (CSVRecord record : records){
                String username = record.get("Username");
                String firstName = record.get("First Name");
                String lastName = record.get("Last Name");
                String email = record.get("Email");
                String role = record.get("Role");
                int r;
                switch (role){
                    case "Lecture":
                        r = 1;
                        break;
                    case "Student":
                        r = 2;
                        break;
                    case "Admin":
                        r = 0;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid Role at row " + record.getRecordNumber());
                }
                if (username.isEmpty()){
                    throw new IllegalArgumentException("Invalid Username at row " + + record.getRecordNumber());
                }
                if (firstName.isEmpty()){
                    throw new IllegalArgumentException("Invalid firstName at row " + record.getRecordNumber());
                }
                if (lastName.isEmpty()){
                    throw new IllegalArgumentException("Invalid lastName at row " + record.getRecordNumber());
                }
                if (!email.matches("^[\\w-\\.]+@((fpt.edu.vn)|(fu.edu.vn))$")){
                    throw new IllegalArgumentException("Invalid lastName at row " + record.getRecordNumber() + ". It should be an FPT email.");
                }
                User user = new User(username,Utility.encode(pwd),firstName,lastName,
                        email,new Timestamp(new Date().getTime()),r,"KEY");
                users.add(user);
            }
            for (User user : users){
                userDAO.addUserThrow(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }
        if (exception == null){
            response.sendRedirect(request.getContextPath() + "/admin/users");
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/users?error=" + URLEncoder.encode(exception.getMessage(), "UTF-8"));
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