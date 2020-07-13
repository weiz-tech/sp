package tech.weizhang.utils;

public class KafkaUtil {
    private static final String HOST = "kafka.host";
    private static final String TOPIC = "yield";


    public static String[] getConf(){
        return PropertyUtil.getProperty(HOST).split(";");
    }

    public static String getTopic(){
        return TOPIC;
    }


}
