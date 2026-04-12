package com.znaji.userservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class ExecutionTimeAspect {

    @Pointcut("execution(* com.znaji.userservice.controller.*.*(..))")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logExecutionTime(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long end = System.currentTimeMillis();
            log.info("Execution time of {}: {} ms", joinPoint.getSignature().toShortString(), (end - start));
        }
    }
}