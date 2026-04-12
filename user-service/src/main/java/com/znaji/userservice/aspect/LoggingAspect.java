package com.znaji.userservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.znaji.userservice.service.UserService.*(..))")
    public void userServiceMethods() {}

    @Before("userServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Entering method={} args={}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "userServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Exiting method={} result={}",
                joinPoint.getSignature().toShortString(),
                formatResult(result));
    }

    @AfterThrowing(pointcut = "userServiceMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        log.error("Exception in method={} message={}",
                joinPoint.getSignature().toShortString(),
                ex.getMessage(),
                ex);
    }

    private Object formatResult(Object result) {
        if (result == null) {
            return "null";
        }

        if (result instanceof java.util.Collection<?> collection) {
            return "Collection(size=" + collection.size() + ")";
        }

        return result;
    }
}
