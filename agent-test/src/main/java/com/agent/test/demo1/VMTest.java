//package com.agent.test.demo1;
//
//import com.sun.tools.attach.AgentInitializationException;
//import com.sun.tools.attach.AgentLoadException;
//import com.sun.tools.attach.AttachNotSupportedException;
//import com.sun.tools.attach.VirtualMachine;
//
//import java.io.IOException;
//import java.lang.management.ManagementFactory;
//import java.lang.management.RuntimeMXBean;
//
///**
// * 通过agentmain加载java-agent
// */
//public class VMTest {
//    static int i = 0;
//    static int pid = 0;
//    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, IOException, AgentLoadException, AgentInitializationException, AttachNotSupportedException {
//        boolean isattach = false;
//        while(true){
//            Thread.sleep(2000);
//            i++;
//            System.out.println("mainagent test=========pid:"+getProcessID());
//            if(i >2){
//
//                if(!isattach){
//
//                    VirtualMachine vm = VirtualMachine.attach(getProcessID()+"");
//                    //指定agent的路径
//                    vm.loadAgent("/Users/xieyang/work/web_space/java-agent-all/java-agents/agent3/target/agent3.jar");
//                    isattach =true;
//                }
//                Test3.sayHello2("rrrrrrrr");
//                Test3.sayHello();
//                Test3.sayHello3();
//
//
//            }
//
//        }
//    }
//
//    public static final int getProcessID() {
//        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
//        //System.out.println(runtimeMXBean.getName());
//        return Integer.valueOf(runtimeMXBean.getName().split("@")[0])
//                .intValue();
//    }
//
//
//
//}