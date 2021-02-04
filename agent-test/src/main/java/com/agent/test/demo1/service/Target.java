package com.agent.test.demo1.service;

public class Target {

    /**
     * 为什么要静态方法？
     * @param name
     * @return
     */
    public  String hello(String name) {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello " + name + "!";
        }
    }