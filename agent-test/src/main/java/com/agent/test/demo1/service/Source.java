package com.agent.test.demo1.service;

public class Source {

    public String hello(String name) {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "abc";
    }
}