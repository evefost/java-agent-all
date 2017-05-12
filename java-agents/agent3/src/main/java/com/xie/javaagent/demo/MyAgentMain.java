package com.xie.javaagent.demo;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class MyAgentMain {

    public static void agentmain(String args, Instrumentation inst) throws UnmodifiableClassException {
        System.out.println("MyAgentMain agentmain attach...");
        inst.addTransformer(new MonitorTransformer());
//        System.getProperties().setProperty("monitor.conf", args);
//
//        for (Class clazz :inst.getAllLoadedClasses()){
//           System.out.println(clazz.getName());
//        }
//        inst.addTransformer(newMonitorAgentTransformer(),true);  
//       inst.retransformClasses(WaitTest.class);  

        System.out.println("MyAgentMain agentmain end...");
    }
} 