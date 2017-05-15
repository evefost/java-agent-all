package com.agent.test.demo1;

/**
 * 简单监控方法的执时间
 */
public class Test3 {




    public static void sayHello2(String hello) {
        try {
            Thread.sleep(500);
            System.out.println("方法2"+hello);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void sayHello() {
        try {
            Thread.sleep(2000);
            System.out.println("方法1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sayHello3() {
        try {
            Thread.sleep(500);
            System.out.println("方法3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}  