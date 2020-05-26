package tech.weizhang.bd.learn.core;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.Tuple2;

import tech.weizhang.utils.SparkConfigUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class OperatorSamples {
    private static final Logger logger = LoggerFactory.getLogger(OperatorSamples.class);
    private static JavaSparkContext context = SparkConfigUtil.getSparkContext();

    public static void main(String[] args) {
//        mapOperator();
//        filterOperator();
//        flatMapOperator();
//        groupByKeyOperator();
//        reduceByKeyOperator();
//        sortByKeyOperator();
//        joinAndCogroupOperator();
//        mapPartitionsOperator();
//        glomOperator();
//        unionOperator();
//        cartesianOperator();
//        subtractAndIntersectionOperator();
//        groupByOperator();
        sampleOperator();
    }

    /**
     * map算子操作：对每个元素做操作，然后返回。
     */
    static void mapOperator() {
        logger.info("map 算子操作开始=================================");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        JavaRDD<Integer> numberRdd = context.parallelize(numbers);
        JavaRDD<Integer> doubleNumber = numberRdd.map(number -> number * 2);
        doubleNumber.foreach(number -> {
            System.out.print(number + " ");
        });
        context.close();
        logger.info("map 算子操作结束=================================");

    }

    /**
     * filter算子操作：过滤
     */
    static void filterOperator() {
        logger.info("filter 算子操作开始=================================");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        JavaRDD<Integer> list = context.parallelize(numbers);
        JavaRDD<Integer> evenNumber = list.filter(number -> number % 2 == 0);
        JavaRDD<Integer> oddNumber = list.filter(number -> number % 2 != 0);
        evenNumber.foreach(number -> {
            logger.info(number + "  ");
        });
        oddNumber.foreach(number -> {
            logger.info(number + "  ");
        });
        context.close();
        logger.info("filter 算子操作结束=================================");
    }

    /**
     * flatmap算子操作：对每个元素做操作，返回Iterable类型，相对于map的每个元素之返回一个值，flatmap可能返回多个（封装在Iterable中）
     */
    static void flatMapOperator() {
        logger.info("flatMap 算子操作开始=================================");
        List<String> lines = Arrays.asList("sdf asdf2", "sdf 2dg", "sdf haha");
        JavaRDD<String> slit = context.parallelize(lines);
        JavaRDD<String> numbers = slit.flatMap(number -> Arrays.asList(number.split(" ")).iterator());
        numbers.foreach(num -> {
            logger.info(num);
        });
        context.close();
        logger.info("flatMap 算子操作结束=================================");
    }

    /**
     * groupByKey 算子：按key分组聚合元素，类似关系型数据库的group by操作
     */
    static void groupByKeyOperator() {
        List<Tuple2<String, Integer>> pairs = Arrays.asList(new Tuple2<>("class1", 33), new Tuple2<>("class2", 44), new Tuple2<>("class2", 55), new Tuple2<>("class1", 67));
        JavaPairRDD<String, Integer> rddPairs = context.parallelizePairs(pairs);
        JavaPairRDD<String, Iterable<Integer>> groupPairs = rddPairs.groupByKey();
        groupPairs.foreach(p -> {
            logger.error(p._1);
            Iterator<Integer> it = p._2.iterator();
            while (it.hasNext()) {
                logger.error(it.next().toString());
            }
        });
        context.close();
    }

    /**
     * reduceByKey 算子：按key聚合后，对同一个key的值进行reduce操作。
     */
    static void reduceByKeyOperator() {
        List<Tuple2<String, Integer>> pairs = Arrays.asList(new Tuple2<>("class1", 33), new Tuple2<>("class2", 44), new Tuple2<>("class2", 55), new Tuple2<>("class1", 67));
        JavaPairRDD<String, Integer> javaPairRDD = context.parallelizePairs(pairs);
        JavaPairRDD<String, Integer> reduce = javaPairRDD.reduceByKey((v1, v2) -> v1 + v2);
        reduce.foreach(r -> {
            logger.error(r._1 + ":" + r._2);
        });
        context.close();
    }

    /**
     * sortByKey 算子:按key排序rdd的元素
     */
    static void sortByKeyOperator() {
        List<Tuple2<Integer, String>> list = Arrays.asList(
                new Tuple2<>(85, "jack"),
                new Tuple2<>(60, "tom"),
                new Tuple2<>(53, "marry"),
                new Tuple2<>(93, "katy"));
        JavaPairRDD<Integer, String> javaPairRDD = context.parallelizePairs(list);
        JavaPairRDD<Integer, String> javaPairRDD1 = javaPairRDD.sortByKey(false);
        javaPairRDD1.foreach(r -> {
            logger.error(r._1 + ":" + r._2);
        });
        context.close();
    }

    /**
     * @Desp: join和cogroup 算子
     * @Param: join类似数据库的连接操作，cogroup将key相同的数据做合并，返回的rdd中合并数据都是Iterable类型的。
     * @Author: weizhang
     * @Date: 2020/5/26 10:44
     */
    static void joinAndCogroupOperator() {

        List<Tuple2<Integer, Integer>> scoreList = Arrays.asList(new Tuple2<>(1, 99), new Tuple2<>(2, 45), new Tuple2<>(3, 67), new Tuple2<>(4, 88), new Tuple2<>(2, 89));
        List<Tuple2<Integer, String>> nameList = Arrays.asList(new Tuple2<>(1, "jack"), new Tuple2<>(2, "marry"), new Tuple2<>(3, "katty"), new Tuple2<>(4, "tom"), new Tuple2<>(2, "lucy"));
        JavaPairRDD<Integer, Integer> scoreRdd = context.parallelizePairs(scoreList);
        JavaPairRDD<Integer, String> nameRdd = context.parallelizePairs(nameList);
        JavaPairRDD<Integer, Tuple2<Integer, String>> joinRdd = scoreRdd.join(nameRdd);
        JavaPairRDD<Integer, Tuple2<Iterable<Integer>, Iterable<String>>> cogroupRdd = scoreRdd.cogroup(nameRdd);
        joinRdd.foreach(r -> {
            logger.error(r._1.toString() + ":" + r._2._1 + "  " + r._2._2);
        });
        cogroupRdd.foreach(r -> {
            logger.error("key->" + r._1 + ":");
            Iterator<Integer> itInt = r._2._1.iterator();
            Iterator<String> itStr = r._2._2.iterator();
            while (itInt.hasNext()) {
                logger.error(itInt.next() + "->" + itStr.next());
            }

        });
        context.close();
    }

    /**
     * @Desp: mapPartitions 算子:map是一条一条处理，与map不同的是，mapPartitions会将数据按分区数量分批处理，提高处理效率。
     * @Param: []
     * @Author: weizhang
     * @Date: 2020/5/26 14:03
     */
    static void mapPartitionsOperator() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        JavaRDD<Integer> javaRDD = context.parallelize(list);

        JavaRDD<Integer> resultRdd = javaRDD.mapPartitions(partition -> {
            List<Integer> result = new ArrayList<>();
            logger.error("get a partition >>>");
            while (partition.hasNext()) {
                result.add(partition.next() * 2);
            }
            return result.iterator();
        });
        resultRdd.foreach(r -> logger.error(r.toString()));
        context.close();

    }

    /**
     * @Desp: glom 算子，将rdd里的元素按分区合并成一个数组
     * @Param: []
     * @Author: weizhang
     * @Date: 2020/5/26 15:11
     */
    static void glomOperator() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        JavaRDD<Integer> d = context.parallelize(list);
        JavaRDD<List<Integer>> javaRDD = d.glom();
        javaRDD.foreach(dd ->
        {
            logger.error("get a partition >>>>");
            Iterator<Integer> it = dd.iterator();
            while (it.hasNext()) {
                logger.error(it.next().toString());
            }
        });
        context.close();
    }

    /**
     * @Desp: RDD间的各种集合操作：
     * union：求合集（不去重）
     * cartesian：笛卡尔积
     * subtract：求差集，去掉两个RDD中相同的元素，保留不一样的。
     * intersection:求交集
     */

    /**
     * @Desp: union 算子：将两个类型相同的rdd合并为一个，包括重复数据
     * @Param: []
     * @Author: weizhang
     * @Date: 2020/5/26 15:32
     */
    static void unionOperator() {
        List<Tuple2<Integer, String>> list1 = Arrays.asList(new Tuple2<>(1, "dd"), new Tuple2<>(2, "we"));
        List<Tuple2<Integer, String>> list2 = Arrays.asList(new Tuple2<>(1, "ede"), new Tuple2<>(3, "werf"));
        JavaPairRDD<Integer, String> rdd1 = context.parallelizePairs(list1);
        JavaPairRDD<Integer, String> rdd2 = context.parallelizePairs(list2);
        JavaPairRDD<Integer, String> rdd3 = rdd1.union(rdd2);
        rdd3.foreach(r -> {
            logger.error(r._1 + "->" + r._2);
        });
        context.close();
    }

    /**
     * @Desp: cartesian 算子：笛卡尔积,将两个rdd的元素一一对应
     * @Param: []
     * @Author: weizhang
     * @Date: 2020/5/26 16:04
     */
    static void cartesianOperator() {
        List<Tuple2<Integer, String>> list1 = Arrays.asList(new Tuple2<>(1, "dd"), new Tuple2<>(2, "we"));
        List<Tuple2<Integer, String>> list2 = Arrays.asList(new Tuple2<>(1, "ede"), new Tuple2<>(3, "werf"));
        JavaPairRDD<Integer, String> rdd1 = context.parallelizePairs(list1);
        JavaPairRDD<Integer, String> rdd2 = context.parallelizePairs(list2);
        JavaPairRDD<Tuple2<Integer, String>, Tuple2<Integer, String>> result = rdd1.cartesian(rdd2);
        rdd1.cartesian(rdd2).foreach(d -> {
            logger.error(d._1._1 + "->" + d._1._2 + ":" + d._2._1 + "->" + d._2._2);
        });
        context.close();
    }

    /**
     * @Desp: subtract 算子：差集，返回第一个RDD中与第二个RDD中不一样的元素，intersection 算子：交集，返回两个RDD相同的元素
     * @Param: []
     * @Author: weizhang
     * @Date: 2020/5/26 17:46
     */
    static void subtractAndIntersectionOperator() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(3, 4, 5);

        JavaRDD<Integer> rdd1 = context.parallelize(list1);
        JavaRDD<Integer> rdd2 = context.parallelize(list2);
        JavaRDD<Integer> subtractRdd = rdd1.subtract(rdd2);
        JavaRDD<Integer> intersectionRdd = rdd1.intersection(rdd2);
        logger.error("subtractRdd:");
        subtractRdd.foreach(r -> {
            logger.error(r.toString());
        });
        logger.error("intersection:");
        intersectionRdd.foreach(r -> logger.error(r.toString()));
    }

    /**
     * @Desp: groupBy 算子：比groupByKey直接用key聚合功能强大，可以传入函数，将函数返回值作为分组的key，而value就是Itrable类型的父rdd的元素。
     * @Param: []
     * @Author: weizhang
     * @Date: 2020/5/26 17:30
     */
    static void groupByOperator() {
        List<Tuple2<String, Integer>> pairs = Arrays.asList(new Tuple2<>("class1", 33), new Tuple2<>("class2", 44), new Tuple2<>("class2", 55), new Tuple2<>("class1", 67));
        JavaPairRDD<String, Integer> rddPairs = context.parallelizePairs(pairs);
        JavaPairRDD<String, Iterable<Tuple2<String, Integer>>> result = rddPairs.groupBy(k -> k._1 + "a");
        result.foreach(r -> {
            logger.error(r._1 + ":");
            Iterator<Tuple2<String, Integer>> it = r._2.iterator();
            while (it.hasNext()) {
                Tuple2<String, Integer> t = it.next();
                logger.error(t._1 + "->" + t._2);
            }
        });
        context.close();
    }

    /**
     * @Desp: sample 算子：随机抽样，withReplacement:是否放回式抽样，fraction：抽样比例，seed：抽样算法的初始值种子
     * @Param: []
     * @Author: weizhang
     * @Date: 2020/5/26 17:57
     */
    static void sampleOperator() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        JavaRDD<Integer> javaRDD = context.parallelize(list);
        JavaRDD<Integer> result = javaRDD.sample(false, 0.2, 3);
        result.foreach(r -> logger.error(r.toString()));
        context.close();
    }


}
