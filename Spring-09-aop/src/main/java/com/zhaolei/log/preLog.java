package com.zhaolei.log;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class preLog implements MethodBeforeAdvice {
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println(target.getClass().getName()+"的"+method.getName()+"被执行了");
    }
}
