package tech.weizhang.learn.spark;

import tech.weizhang.learn.repo.TestObject;

public class TestMain {


    public static void main(String[] args) {
        TestObject to = new TestObject();
        TestObject to1 = new TestObject();
        System.out.println(to.getName());
        to1.setName("foobar");
        System.out.println(to.getName());
    }
}