package com.xie.javaagent.demo1.builder;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * javaagent
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class MethodCostEnhance {

    private static MethodCostEnhance methodCostEnhance = new MethodCostEnhance();

    private MethodCostEnhance(){};
    public static MethodCostEnhance getInstance(){
        return methodCostEnhance;
    }


    public  void enhance(Instrumentation inst) {
        System.out.println(" enhance..");
        
        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            return builder
                    .method(ElementMatchers.any()) // 拦截任意方法
                    .intercept(MethodDelegation.to(MethodCostTime.class)); // 委托
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean loaded, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean loaded) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean loaded, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean loaded) {

            }

        };

        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith("com.agent.test.demo1.service")) // 指定需要拦截的类
                .transform(transformer)
                .with(listener)
                .installOn(inst);
    }


}
