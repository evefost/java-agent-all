package com.agent.test.demo1;

/**
 * 简单监控方法的执时间
 */
public class Test3 {

    public static void main(String[] args) {
        sayHello();
        sayHello2("hello world222222222");
    }

    public static void sayHello() {
        try {
            Thread.sleep(2000);
            System.out.println("hello world!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sayHello2(String hello) {
        try {
            Thread.sleep(500);
            System.out.println(hello);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}  