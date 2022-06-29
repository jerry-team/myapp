package com.jerry.myapp.util;
import android.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;
public class ParseTokenUtils {
    public static String parseToken(String token,String key){
        //线将token转换为String类型然和将String转换成json然和去解析就好了
        String strtoken=new String(Base64.decode(token.split("\\.")[1],0));
        try {
            JSONObject object = new JSONObject(strtoken);
            return object.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
