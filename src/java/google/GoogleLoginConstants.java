/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package google;

/**
 *
 * @author ADMIN
 */
public class GoogleLoginConstants {

    public static String GOOGLE_CLIENT_ID = "200072144555-l8h8o6uk851np2ekc53evm88funv2vak.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "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";
    /* Change the redirect URI if needed. */
    public static String GOOGLE_REDIRECT_URI = "http://localhost:8086/SWP/callback";
    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";
}
