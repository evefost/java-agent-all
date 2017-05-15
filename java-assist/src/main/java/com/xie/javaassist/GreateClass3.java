package com.xie.javaassist;

import javassist.*;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 各种参数处理
 */
public class GreateClass3 {

    private static  String className ="com.javaassist.CreateClass3";
      
    public static void main(String[] args) throws Exception {

        String outputDir = System.getProperty("user.dir")+"/java-assist/class_out";
        deleletFile(outputDir,className);

        // 创建类
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass(className);



        addMethodWithParams(cc,pool);


        //必须调用该方法才能创建该类，否则下面报classNotfound
        Class aClass = cc.toClass();
        Object instance = aClass.newInstance();
//      Object instance = Class.forName("com.javaassist.CreateClass").newInstance();
        System.out.println("被创建的类:"+instance.getClass().getName());

//        Method initMethod =  aClass.getMethod("init",new Class[]{String.class,int.class});
//        Object newClassName = initMethod.invoke(instance, "newClassName", 11);
//        System.out.println("init result :"+newClassName);
//
//        Method setName =  aClass.getMethod("setClassName",new Class[]{String.class});
//        setName.invoke(instance,"这是新方法名");

        //把生成类输出到该目录下
        cc.writeFile(outputDir);
    }

    private static void deleletFile(String outputDir,String className){
        //删除源文件
        String filePath = className.replace(".",File.separator);
        File clsFile = new File(outputDir+File.separator+filePath+".class");
        if(clsFile.exists()){
            clsFile.delete();
        }
    }




    /**
     * 3.在方法内插入source
     * $0, $1, $2, ...
     * $0代码的是this，$1代表方法参数的第一个参数、$2代表方法参数的第二个参数,以此类推，$N代表是方法参数的第N个。：
     */
    public static void addMethodWithParams(CtClass cc,ClassPool pool) throws Exception {
        CtMethod m1 =CtMethod.make("public String method1(String className){return null;}",cc);
        CtMethod m2 =CtMethod.make("public String method2(String className,int id){return null;}",cc);
        CtMethod m3 =CtMethod.make("public String method2(String[] args){return null;}",cc);
        cc.addMethod(m1);
        cc.addMethod(m2);
        cc.addMethod(m3);
        m1.setBody(m2,new ClassMap());

    }
  
}  
