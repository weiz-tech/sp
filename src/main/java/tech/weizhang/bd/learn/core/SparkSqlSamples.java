package tech.weizhang.bd.learn.core;

import cn.hutool.json.JSONObject;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.StructField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.weizhang.draft.TestDto;
import tech.weizhang.utils.SparkConfigUtil;

import java.util.ArrayList;
import java.util.List;

public class SparkSqlSamples {
    private static final Logger logger = LoggerFactory.getLogger(SparkSqlSamples.class);
    private static JavaSparkContext context = SparkConfigUtil.getSparkContext();

    public static void main(String[] args) {
        TestDto td = new TestDto("lisi",1L);
        JSONObject jsonObject = new JSONObject(td);
        String tdJ = jsonObject.toString();
        logger.error(tdJ);
        json2SQL();




    }

    static void json2SQL(){
        SQLContext sqlContext = new SQLContext(context);
        Dataset df = sqlContext.read().json("C:\\Users\\wz\\Desktop\\td.json");
        df.show();
        df.select("name").show();
        df.printSchema();
        df.filter(df.col("id").gt(2)).show();
        context.close();
    }

    static void programmatically2Dataset(){
        List<StructField> structFields = new ArrayList<>();

    }
}
