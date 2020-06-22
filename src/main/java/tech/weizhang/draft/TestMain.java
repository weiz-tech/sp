package tech.weizhang.draft;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestMain {
public static void main(String[]args) throws Exception{
        TestDto td1=new TestDto("aa",1L);
        TestDto td2=new TestDto("bb",1L);
        TestDto td3=new TestDto("cc",2L);
        TestDto td4=new TestDto("dd",2L);
        List<TestDto> testList=new ArrayList<>();
        testList.add(td1);
        testList.add(td2);
        testList.add(td3);
        testList.add(td4);
        Class<TestDto> c=TestDto.class;
    Method method=c.getMethod("getId");

            Function<TestDto, Long> f=s->{
                try{
                    return (Long)method.invoke(s);
                }catch (Exception e){
                    throw new RuntimeException();
                }
            };
        Map<Long,List<TestDto>> result = testList.stream().collect(Collectors.groupingBy(f,Collectors.toList()));
        for(Map.Entry<Long,List<TestDto>> entry:result.entrySet()){
            System.out.println(entry.getKey()+"::"+entry.getValue().toString());
        }
        }

        }
