package tech.weizhang.learn.javacore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.weizhang.learn.repo.TestDto;
import tech.weizhang.utils.LogBackConfigurator;
import tech.weizhang.utils.PropertyUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LamdaLearn {


    private static final Logger log = LoggerFactory.getLogger(LamdaLearn.class);

    public static void main(String[] args){
        //获取配置文件参数
        String host = PropertyUtil.getProperty("spark.host");
        log.debug("测试地址："+host);

    }

    //测试数据
    public static List<TestDto> getTestDtos(int size) {
        List<TestDto> dtos = new ArrayList<>(size);
        TestDto td1 = new TestDto("aa", 1L);
        TestDto td2 = new TestDto("bb", 1L);
        TestDto td3 = new TestDto("cc", 2L);
        TestDto td4 = new TestDto("dd", 2L);
        dtos.add(td1);
        dtos.add(td2);
        dtos.add(td3);
        dtos.add(td4);
        return dtos;
    }

    /**
     * 将自定义方法作为入参，动态传入lamda表达式中
     * @throws Exception
     */
    public static void lamdaGroupingBy()  throws Exception {
        Class<TestDto> c = TestDto.class;
        Method method = c.getMethod("getId");
        Function<TestDto, Long> f = s -> {
            try {
                return (Long) method.invoke(s);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        };
        //利用反射，获取getter方法后，将方法传入lamda表达式中
        Map<Long, List<TestDto>> result = LamdaLearn.getTestDtos(4).stream().collect(Collectors.groupingBy(f, Collectors.toList()));
        for (Map.Entry<Long, List<TestDto>> entry : result.entrySet()) {
            log.error(entry.getKey() + "::" + entry.getValue().toString());
        }
    }

}
