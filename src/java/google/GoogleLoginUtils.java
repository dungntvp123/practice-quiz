/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package google;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author ADMIN
 */
public class GoogleLoginUtils {
    public static String getToken(final String code) throws ClientProtocolException, IOException {
    String response = Request.Post(GoogleLoginConstants.GOOGLE_LINK_GET_TOKEN)
        .bodyForm(Form.form().add("client_id", GoogleLoginConstants.GOOGLE_CLIENT_ID)
            .add("client_secret", GoogleLoginConstants.GOOGLE_CLIENT_SECRET)
            .add("redirect_uri",GoogleLoginConstants.GOOGLE_REDIRECT_URI).add("code", code)
            .add("grant_type", GoogleLoginConstants.GOOGLE_GRANT_TYPE).build())
        .execute().returnContent().asString();
      JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
      String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
      return accessToken;
  }

    static GoogleUser getUserInfo(String accessToken) throws IOException {
            String link = GoogleLoginConstants.GOOGLE_LINK_GET_USER_INFO + accessToken;
            String response = Request.Get(link).execute().returnContent().asString();
            GoogleUser user = new Gson().fromJson(response, GoogleUser.class);
            System.out.println(user);
            return user;
    }
}
