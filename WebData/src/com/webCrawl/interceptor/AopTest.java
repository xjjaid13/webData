package com.webCrawl.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopTest {

	@After(value="execution(* com.*.*(..))")
    public void beforeSayHello(JoinPoint joinPoint){
        System.out.println("Before :"+joinPoint.getArgs()[0]);
    }
	
}
