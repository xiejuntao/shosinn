package xjt.sb.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class AopConfig {
/*    @Around("execution(* xjt.sb.service.LifeService.c()) ")
    public void c(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        joinPoint.proceed();
        long end = System.currentTimeMillis();
        System.err.println("c method time cost（ms）: " + (end - start));
    }*/
    @Before("execution(* xjt.sb.service.LifeService.c()) ")
    public void b() throws Throwable {
        System.out.println("===================b");
        Thread.sleep(1000);
    }
    @Before("execution(* xjt.sb.service.LifeService.c()) ")
    public void a() throws Throwable {
        System.out.println("====================bb");
        Thread.sleep(1000);
    }
}
