package com.agent.test.demo1;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class VMTest {
    static int i = 0;
    static int pid = 0;
    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, IOException, AgentLoadException, AgentInitializationException, AttachNotSupportedException {
        boolean isattach = false;
        while(true){
            Thread.sleep(2000);
            i++;
            System.out.println("mainagent test=========pid:"+getProcessID());
            if(i >5){
                //Class<?> aClass = Class.forName("com.agent.test.demo1.Test3.class");
                //Test.invokerTest3();
                if(!isattach){

                    VirtualMachine vm = VirtualMachine.attach(getProcessID()+"");
                    vm.loadAgent("/Users/xieyang/work/web_space/java-agent-all/java-agents/agent3/target/agent3.jar");
                    isattach =true;
                }

                Test3.sayHello();

            }

        }
    }

    public static final int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println(runtimeMXBean.getName());
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0])
                .intValue();
    }



}  