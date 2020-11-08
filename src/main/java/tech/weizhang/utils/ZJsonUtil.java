package tech.weizhang.utils;

import com.google.gson.Gson;

public class ZJsonUtil {

    public static <T> T fromJson(String jsonStr,Class<T> clazz){
        Gson gson = new Gson();
         return gson.fromJson(jsonStr,clazz);
    }
}
