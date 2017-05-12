package com.agent.test.demo1;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

public class VirtualMachineTest {
   
    public static void main(String[] args) throws AttachNotSupportedException, IOException,
            AgentLoadException,AgentInitializationException, InterruptedException {
        // attach to target VM   
        VirtualMachine vm = VirtualMachine.attach("2063");
        vm.loadAgent("/Users/xieyang/work/web_space/java-agent-all/java-agents/agent3/target/agent3.jar");
        Thread.sleep(30000);
        vm.detach();
        System.out.println("已detach agent");
        Thread.sleep(30000);

    }  
}  