package com.xie.javaagent.demo1;

public class Target {

    /**
     * 为什么要静态方法？
     * @param name
     * @return
     */
    public  String hello(String name) {
            return "Hello " + name + "!";
        }
    }