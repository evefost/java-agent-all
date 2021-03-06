package com.xie.javaagent.demo;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过javaasssist 修改字节码
 * 简单监控方法的执时间
 */
public class MonitorTransformer implements ClassFileTransformer {

    final static String prefix = "\nlong startTime = System.currentTimeMillis();\n";
    final static String postfix = "\nlong endTime = System.currentTimeMillis();\n";
    final static List<String> methodList = new ArrayList<String>();

    static {
        methodList.add("com.agent.test.demo1.Test3.sayHello");
        methodList.add("com.agent.test.demo1.Test3.sayHello2");
    }

    /* (non-Javadoc)
     * @see java.lang.instrument.ClassFileTransformer#transform(java.lang.ClassLoader, java.lang.String, java.lang.Class, java.security.ProtectionDomain, byte[])
     */
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {

        //先判断下现在加载的class的包路径是不是需要监控的类，通过instrumentation进来的class路径用‘/’分割
        if (className.startsWith("com/agent/test/demo1/Test3")) {
            //将‘/’替换为‘.’m比如monitor/agent/Mytest替换为monitor.agent.Mytest
            className = className.replace("/", ".");
            CtClass ctclass = null;
            try {
                //用于取得字节码类，必须在当前的classpath中，使用全称 ,这部分是关于javassist的知识
                ctclass = ClassPool.getDefault().get(className);
                //循环一下，看看哪些方法需要加时间监测
                for (String method : methodList) {

                    if (method.startsWith(className)) {

                        String methodName = method.substring(method.lastIndexOf('.') + 1, method.length());
                        String outputStr = "\nSystem.out.println(\"this method " + methodName + " cost:\" +(endTime - startTime) +\"ms.\");";
                        CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);
                        String newMethodName = methodName + "$impl";
                        ctmethod.setName(newMethodName);

                        CtMethod newMethod = CtNewMethod.copy(ctmethod, methodName, ctclass, null);
                        StringBuilder bodyStr = new StringBuilder();
                        bodyStr.append("{");
                        bodyStr.append(prefix);
                        bodyStr.append(newMethodName + "($$);\n");

                        bodyStr.append(postfix);
                        bodyStr.append(outputStr);

                        bodyStr.append("}");
                        newMethod.setBody(bodyStr.toString());
                        ctclass.addMethod(newMethod);
                    }
                }
                //输出修改后的类
                String outputDir = System.getProperty("user.dir") + "/java-assist/class_out";
                ctclass.writeFile(outputDir);
            } catch (IOException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            } catch (CannotCompileException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NotFoundException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

}