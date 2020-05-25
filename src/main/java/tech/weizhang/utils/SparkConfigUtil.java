package tech.weizhang.utils;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkConfigUtil {
    public static SparkConf conf = null;
    static {
         conf = new SparkConf().setAppName("learnApp").setMaster("local");
    }

    public static JavaSparkContext getSparkContext(){
        return new JavaSparkContext(conf);
    };
}
