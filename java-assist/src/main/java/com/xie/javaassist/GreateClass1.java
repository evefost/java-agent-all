package com.xie.javaassist;

import javassist.*;

import java.io.File;

public class GreateClass1 {

    private static  String className ="com.javaassist.CreateClass1";
      
    public static void main(String[] args) throws Exception {

        String outputDir = System.getProperty("user.dir")+"/java-assist/class_out";
        deleletFile(outputDir,className);

        // 创建类
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass(className);

        addFields(pool,cc);

        createConstuctors(pool,cc);

        addMethods(cc);


        //必须调用该方法才能创建该类，否则下面报classNotfound
        Class aClass = cc.toClass();
        Object instance = aClass.newInstance();
//      Object instance = Class.forName("com.javaassist.CreateClass").newInstance();
        System.out.println("被创建的类:"+instance.getClass().getName());

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
     * 1.添加私有成员className及其getter、setter方法
     */
    public static void addFields(ClassPool pool,CtClass cc) throws Exception {

        //(1)创建属性
        CtField field01 = CtField.make("private int id = 1;",cc);
        CtField field02 = CtField.make("private String address;", cc);
        cc.addField(field01);
        cc.addField(field02);
        cc.addMethod(CtNewMethod.setter("setId", field01));
        cc.addMethod(CtNewMethod.getter("getId", field01));


        //(2)创建属性
        CtField field3 = new CtField(pool.get("java.lang.String"), "className", cc);
        field3.setModifiers(Modifier.PRIVATE);
        cc.addMethod(CtNewMethod.setter("setClassName", field3));
        cc.addMethod(CtNewMethod.getter("getClassName", field3));
        cc.addField(field3, CtField.Initializer.constant(""));

        CtField field4 = new CtField(pool.get("java.lang.Integer"), "score", cc);
        field4.setModifiers(Modifier.PRIVATE);
        cc.addMethod(CtNewMethod.setter("setScore", field4));
        cc.addMethod(CtNewMethod.getter("getScore", field4));
        cc.addField(field4);

    }

    /**
     * $0
     * 2.添加各类型构造器
     * $0, $1, $2, ...
     * $0代码的是this，$1代表方法参数的第一个参数、$2代表方法参数的第二个参数,以此类推，$N代表是方法参数的第N个。：
     */
    public static void createConstuctors(ClassPool pool,CtClass cc ) throws Exception {

        //(1)添加无参的构造体
        CtConstructor cons = new CtConstructor(new CtClass[] {}, cc);
        cons.setBody("{int i =8 ; className=\"" +cc.getSimpleName()+"\";}");
        cc.addConstructor(cons);

        //(2)添加有参构造器
        CtConstructor constructor = new CtConstructor(new CtClass[]{CtClass.intType,pool.get("java.lang.String")},cc);
        constructor.setBody("{this.id=$1;this.className=$2;}");
        cc.addConstructor(constructor);
    }


    /**
     * 3.添加方法
     * $0, $1, $2, ...
     * $0代码的是this，$1代表方法参数的第一个参数、$2代表方法参数的第二个参数,以此类推，$N代表是方法参数的第N个。：
     */
    public static void addMethods(CtClass cc) throws Exception {

        //(1)创建方法
        CtMethod method01 = CtMethod.make("public String getFirstName(){return className;}", cc);
        CtMethod method02 = CtMethod.make("public void setIdAndName(int id,String className){this.id =id;this.className = className;}", cc);
        CtMethod method03 = CtMethod.make("public void setMutilParams(int id,String className,String address)" +
                "{this.id =$1;this.className = $2;this.address = $3;}", cc);

        cc.addMethod(method01);
        cc.addMethod(method02);
        cc.addMethod(method03);

    }
  
}  
