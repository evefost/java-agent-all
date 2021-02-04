package com.agent.test.demo1;

import com.agent.test.demo1.service.Source;
import com.agent.test.demo1.service.Target;

/**
 * Created by qinmp on 2017/5/12.
 */
public class Test1 {

    public static void main(String[] args){
        System.out.println("AgentDemo1 main 执行 ");
        Source source = new Source();
        Target target = new Target();
        source.hello("abc");
        target.hello("efg");
    }
}
