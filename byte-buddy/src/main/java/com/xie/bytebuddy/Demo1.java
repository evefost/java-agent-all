package com.xie.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * Created by xieyang on 17/5/15.
 */
public class Demo1 {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(Demo1.class.getClassLoader())
                .getLoaded();

        System.out.println(dynamicType.newInstance().toString());

        //createSimpleClass();
        // createClass2();

    }



    private static void createSimpleClass(){
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("example.Type")
                .make();
    }


    private  void createClass2(){
//        Foo dynamicFoo = new ByteBuddy()
//                .subclass(Foo.class)
//                .method(isDeclaredBy(Foo.class)).intercept(FixedValue.value("One!"))
//                .method(named("foo")).intercept(FixedValue.value("Two!"))
//                .method(named("foo").and(takesArguments(1))).intercept(FixedValue.value("Three!"))
//                .make()
//                .load(getClass().getClassLoader())
//                .getLoaded()
//                .newInstance();
    }
}
