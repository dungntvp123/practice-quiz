/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import google.GoogleLoginConstants;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author ADMIN
 */


public class WebRedirectionUtil {
    
    
    
    public static void dispatch(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
        RequestDispatcher dispatch
                = request.getRequestDispatcher(url);
        dispatch.include(request, response);
    }

    public static void sendErrorWithMessage(HttpServletRequest request, HttpServletResponse response, int error, String message) throws ServletException, IOException {
        response.setStatus(error);
        request.setAttribute("message", message);
        dispatch(request,response,"/view/common/error/error.jsp");
    }

    public static void send404(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sendErrorWithMessage(request,response,404,"Page not found");
    }

    public static void send403(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sendErrorWithMessage(request,response,403,"You cannot access this page because this resource is not available to you.");
    }
}
