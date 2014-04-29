package com.webCrawl.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.webCrawl.entity.CrawlBug;

@Aspect
@Component
public class AopTest {

	@After(value="execution(* com.*.*(..))")
    public void beforeSayHello(JoinPoint joinPoint){
        System.out.println("Before :"+joinPoint.getArgs()[0]);
    }
	
}
