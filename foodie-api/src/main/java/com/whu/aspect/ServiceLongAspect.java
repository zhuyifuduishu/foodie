package com.whu.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class ServiceLongAspect {
    public static final Logger log = LoggerFactory.getLogger(ServiceLongAspect.class);

    /*
    * AOP通知：
    * 1.前置通知
    * 2.后置通知
    * 3.环绕通知
    * 4.异常通知
    * 5.最终通知
    * */

    @Around("execution(* com.whu.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("****** 开始执行{}.{}******", joinPoint.getTarget(), joinPoint.getSignature().getName());

        //记录开始时间
        long begin = System.currentTimeMillis();

        //执行目标service
        Object result = joinPoint.proceed();

        //记录结束时间
        long end = System.currentTimeMillis();

        long takeTime = end - begin;

        if (takeTime > 3000) {
            log.error("****** 执行结束，耗时:{}毫秒 ******",takeTime);
        } else if (takeTime>2000) {
            log.warn("****** 执行结束，耗时:{}毫秒 ******",takeTime);
        }else{
            log.info("****** 执行结束，耗时:{}毫秒 ******",takeTime);
        }

        return result;
    }
}
