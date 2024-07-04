package utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;

public class APIUtil {

    public static JsonElement getElement(String s){
        return new JsonPrimitive(s);
    }

    public static JsonElement getElement(java.lang.Number x){
        return new JsonPrimitive(x);
    }

    public static JsonNull getNull(){
        return JsonNull.INSTANCE;
    }

    public static JsonElement getElement(boolean b){
        return new JsonPrimitive(b);
    }
}
