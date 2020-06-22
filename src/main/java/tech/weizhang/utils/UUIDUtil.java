package tech.weizhang.utils;

import cn.hutool.Hutool;

public class UUIDUtil {
    public static String get(){
       String uuid = java.util.UUID.randomUUID().toString();
        return uuid.replaceAll("-","");

    }
}
