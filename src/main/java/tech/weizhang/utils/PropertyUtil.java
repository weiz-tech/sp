package tech.weizhang.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    private static Properties p =null;
    static {
        InputStream in = PropertyUtil.class.getClassLoader().getResourceAsStream("conf.properties");
        p = new Properties();
        try{
            p.load(in);
        }catch (IOException e){
            throw new RuntimeException("从conf.properties读取配置文件失败！");
        }

        //init logger
//        LogBackConfigurator

    }

    public static String getProperty(String key){
        return p.getProperty(key);
    }


}
