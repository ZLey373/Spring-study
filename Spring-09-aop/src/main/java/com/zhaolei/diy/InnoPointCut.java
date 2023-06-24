package com.zhaolei.diy;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect     // 注册切面
public class InnoPointCut {
    @Before("execution(* com.zhaolei.service.UserServiceImpl.*(..))")  // 绑定切点
    public void before(){
        System.out.println("---------执行前---------");
    }
    @After("execution(* com.zhaolei.service.UserServiceImpl.*(..))")
    public void after(){
        System.out.println("---------执行后---------");
    }

    @Around("execution(* com.zhaolei.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("-----环绕前----");
        Object proceed = jp.proceed();
        System.out.println("-----环绕后----");
    }
}
