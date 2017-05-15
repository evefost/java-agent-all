package com.xie.javaassist;

import javassist.*;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 在方法体内插入source
 */
public class GreateClass2 {

    private static String className = "com.javaassist.CreateClass2";

    public static void main(String[] args) throws Exception {

        String outputDir = System.getProperty("user.dir") + "/java-assist/class_out";
        deleletFile(outputDir, className);

        // 创建类
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass(className);

        addFields(pool, cc);

        insertSourceMethods(cc, pool);
        addCatch(cc);


        //必须调用该方法才能创建该类，否则下面报classNotfound
        Class aClass = cc.toClass();
        Object instance = aClass.newInstance();
//      Object instance = Class.forName("com.javaassist.CreateClass").newInstance();
        System.out.println("被创建的类:" + instance.getClass().getName());

        Method initMethod = aClass.getMethod("init", new Class[]{String.class, int.class});
        Object newClassName = initMethod.invoke(instance, "newClassName", 11);
        System.out.println("init result :" + newClassName);

        Method setName = aClass.getMethod("setClassName", new Class[]{String.class});
        setName.invoke(instance, "这是新方法名");

        //把生成类输出到该目录下
        cc.writeFile(outputDir);
    }

    private static void deleletFile(String outputDir, String className) {
        //删除源文件
        String filePath = className.replace(".", File.separator);
        File clsFile = new File(outputDir + File.separator + filePath + ".class");
        if (clsFile.exists()) {
            clsFile.delete();
        }
    }

    /**
     * 1.添加私有成员className及其getter、setter方法
     */
    public static void addFields(ClassPool pool, CtClass cc) throws Exception {

        CtField field0 = CtField.make("private int id;", cc);
        cc.addField(field0);

        CtField field3 = new CtField(pool.get("java.lang.String"), "className", cc);
        field3.setModifiers(Modifier.PRIVATE);
        cc.addMethod(CtNewMethod.setter("setClassName", field3));
        cc.addMethod(CtNewMethod.getter("getClassName", field3));
        cc.addField(field3, CtField.Initializer.constant(""));


    }


    /**
     * 3.在方法内插入source
     * $0, $1, $2, ...
     * $0代码的是this，$1代表方法参数的第一个参数、$2代表方法参数的第二个参数,以此类推，$N代表是方法参数的第N个。：
     */
    public static void insertSourceMethods(CtClass cc, ClassPool pool) throws Exception {

        //获取某个方法
        //CtMethod setClassName = cc.getDeclaredMethod("setClassName");
        CtMethod setClassName = cc.getDeclaredMethod("setClassName", new CtClass[]{pool.get("java.lang.String")});

        setClassName.insertBefore("System.out.println(\"insertbefore setClassName方法[\"+className);");
        setClassName.insertAfter("System.out.println(\"insertafter setClassname方法[\"+className);");

        //添加一个就去方法
        CtMethod newM = CtMethod.make("public String init(String className,int id){return null;} throws Exception", cc);

        //设置方法体
        newM.setBody("{" + "String temp = this.id+this.className;"
                +"Thread.sleep(5000l);"
                +"return temp;}");
        cc.addMethod(newM);

        //插入计时
        timingCode(newM,cc);


    }

    //插入计时
    private static  void timingCode(CtMethod srcM ,CtClass cc) throws CannotCompileException, NotFoundException {
        //添加的代码不能引用在方法中其他地方定义的局部变量。这种限制使我不能在 Javassist
        // 中使用在源代码中使用的同样方法实现计时代码，
        // 在这种情况下，我在开始时添加的代码中定义了一个新的局部变量，并在结束处添加的代码中引用这个变量。
//        srcM.insertBefore("long startTime = System.currentTimeMillis();");
//        srcM.insertAfter("long time  = System.currentTimeMillis() - startTime;");
//        srcM.insertAfter("\"方法耗时:\"+time+\"ms\";");

        CtMethod newMethod = CtNewMethod.copy(srcM, srcM.getName(), cc, null);
        srcM.setName(srcM.getName()+"$impl");
        cc.addMethod(newMethod);

        StringBuffer sb = new StringBuffer() ;
        sb.append("{")
                .append("long startTime = System.currentTimeMillis();")
                .append(srcM.getName()+"($$);")
                .append("long time  = System.currentTimeMillis() - startTime;")
            .append("System.out.println(\"方法耗时:\"+time+\"ms\");")
            .append("return \"执行完\";}");

        //(2)添加try catch
//        CtMethod newMethod = ctClass.getDeclaredMethod("init");
        newMethod.setBody(sb.toString());
        CtClass etype = ClassPool.getDefault().get("java.io.IOException");
        newMethod.addCatch("{ System.out.println($e); throw $e; }",etype );




    }


    private static  void addCatch(CtClass ctClass) throws CannotCompileException, NotFoundException {

        //(1)添加try catch
        CtMethod ctMethod = CtMethod.make("public void testMethod(){} throws Exception",ctClass);
        StringBuffer sb = new StringBuffer() ;
        sb.append("{")
                .append("System.out.println(\"catch 外\");")
                .append("try{")
                .append("System.out.println(\"catch 内\");")
                .append("}catch (java.io.IOException e) {")
                .append("System.out.println(\"异常\");")
                .append("}}");

        ctMethod.setBody(sb.toString());
        ctClass.addMethod(ctMethod);


    }

}  
