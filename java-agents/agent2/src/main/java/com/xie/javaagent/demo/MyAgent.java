package com.xie.javaagent.demo;

import java.lang.instrument.Instrumentation;

/**
 * Created by xieyang on 17/5/12.
 */
public class MyAgent {

    public static void premain(String agentArgs, Instrumentation inst){

        System.out.println("javaagent premain 启动 "+agentArgs);
        inst.addTransformer(new MonitorTransformer());
    }
}
