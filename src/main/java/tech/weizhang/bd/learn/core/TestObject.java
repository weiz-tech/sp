package tech.weizhang.bd.learn.core;

public class TestObject {
    private static String name="privatestaticname";
    public String getName(){
        return name;
    }
    public void setName(String name){
        TestObject.name = name;
    }


}
