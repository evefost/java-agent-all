package com.xie.javaassist.demo1;

import javassist.ClassPool;
import javassist.CtClass;

/**
 * 1、读取 、修改 、输出字节码
 *
 */
public class TestReadWriteClass {

    public static void main(String[] args) throws Exception {


        ClassPool pool = ClassPool.getDefault();
        //会从classpath中查询该类
        CtClass cc = pool.get("com.xie.javaassist.demo1.Rectangle");
        //设置.Rectangle的父类
        cc.setSuperclass(pool.get("com.xie.javaassist.demo1.Point"));
        //输出.Rectangle.class文件到该目录中
        String outputDir = System.getProperty("user.dir")+"/java-assist/class_out";
        cc.writeFile(outputDir);

        //输出成二进制格式
        byte[] b=cc.toBytecode();
        //输出并加载class 类，默认加载到当前线程的ClassLoader中，也可以选择输出的ClassLoader。
        Class clazz=cc.toClass();
        Object o = clazz.newInstance();

        System.out.println(o.toString());

    }
}
