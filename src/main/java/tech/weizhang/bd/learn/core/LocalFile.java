package tech.weizhang.bd.learn.core;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;
import sun.rmi.server.InactiveGroupException;
import tech.weizhang.utils.SparkConfigUtil;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LocalFile {
    private static final Logger logger = LoggerFactory.getLogger(LocalFile.class);
    private static JavaSparkContext context = SparkConfigUtil.getSparkContext();

    public static void main(String[] args) {
//        mapOperator();
//        filterOperator();
//        flatMapOperator();
//        groupByKeyOperator();
//        reduceByKeyOperator();
        sortByKeyOperator();
    }

    /**
     * map算子操作
     */
    static void mapOperator(){
        logger.info("map 算子操作开始=================================");
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7);
        JavaRDD<Integer> numberRdd = context.parallelize(numbers);
        JavaRDD<Integer> doubleNumber = numberRdd.map(number->number*2);
        doubleNumber.foreach(number->{
            System.out.print(number+" ");
        });
        context.close();
        logger.info("map 算子操作结束=================================");

    }

    /**
     * filter算子操作
     */
    static void filterOperator(){
        logger.info("filter 算子操作开始=================================");
        List<Integer> numbers  = Arrays.asList(1,2,3,4,5,6,7,8,9);
        JavaRDD<Integer> list = context.parallelize(numbers);
        JavaRDD<Integer> evenNumber = list.filter(number->number %2 ==0);
        JavaRDD<Integer> oddNumber = list.filter(number->number %2 !=0);
        evenNumber.foreach(number -> {
            logger.info(number+"  ");
        });
        oddNumber.foreach(number-> {
            logger.info(number+"  ");
        });
        context.close();
        logger.info("filter 算子操作结束=================================");
    }

    /**
     * flatmap算子操作
     */
    static void flatMapOperator(){
        logger.info("flatMap 算子操作开始=================================");
        List<String> lines = Arrays.asList("sdf asdf2","sdf 2dg","sdf haha");
        JavaRDD<String> slit = context.parallelize(lines);
        JavaRDD<String> numbers = slit.flatMap(number->Arrays.asList(number.split(" ")).iterator());
        numbers.foreach(num->{
            logger.info(num);
        });
        context.close();
        logger.info("flatMap 算子操作结束=================================");
    }

    /**
     * groupByKey 算子
     */
    static void groupByKeyOperator(){
        List<Tuple2<String,Integer>> pairs = Arrays.asList(new Tuple2<>("class1",33),new Tuple2<>("class2",44),new Tuple2<>("class2",55),new Tuple2<>("class1",67));
        JavaPairRDD<String,Integer> rddPairs = context.parallelizePairs(pairs);
        JavaPairRDD<String,Iterable<Integer>> groupPairs = rddPairs.groupByKey();
        groupPairs.foreach(p->{
            logger.error(p._1);
            Iterator<Integer> it = p._2.iterator();
            while (it.hasNext()){
                logger.error(it.next().toString());
            }
        });
        context.close();
    }

    /**
     * reduceByKey 算子
     */
    static void reduceByKeyOperator(){
        List<Tuple2<String,Integer>> pairs = Arrays.asList(new Tuple2<>("class1",33),new Tuple2<>("class2",44),new Tuple2<>("class2",55),new Tuple2<>("class1",67));
        JavaPairRDD<String,Integer> javaPairRDD = context.parallelizePairs(pairs);
        JavaPairRDD<String,Integer> reduce = javaPairRDD.reduceByKey((v1,v2)->v1+v2);
        reduce.foreach(r->{
            logger.error(r._1+":"+r._2);
        });
        context.close();
    }
    /**
     * sortByKey 算子
     */
    static void sortByKeyOperator(){
        List<Tuple2<Integer,String>> list = Arrays.asList(
                new Tuple2<>(85,"jack"),
                new Tuple2<>(60,"tom"),
                new Tuple2<>(53,"marry"),
                new Tuple2<>(93,"katy"));
        JavaPairRDD<Integer,String> javaPairRDD = context.parallelizePairs(list);
        JavaPairRDD<Integer,String> javaPairRDD1 = javaPairRDD.sortByKey(false);
        javaPairRDD1.foreach(r->{
            logger.error(r._1+":"+r._2);
        });
        context.close();
    }
}
