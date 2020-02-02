package com.company.portal.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

@Aspect
@Component
public class LoggingAspect {

    private Logger log = Logger.getLogger(this.getClass().getName());

    @Pointcut("within(com.company.portal.controller..*)" +
            " || within(com.company.portal.exception..*)" +
            " || within(com.company.portal.service..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Before("applicationPackagePointcut()")
    public void before(JoinPoint joinPoint) {
        log.info("Executing method: " + joinPoint.getSignature().getName());
    }
}