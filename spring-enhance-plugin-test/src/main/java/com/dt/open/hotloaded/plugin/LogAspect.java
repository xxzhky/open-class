package com.dt.open.hotloaded.plugin;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Aspect
@Component//切面类加入到IOC容器中
public class LogAspect {

    @Pointcut("execution(public * com.dt.open.hotloaded..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void logStart(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        //log.info(joinPoint.getSignature().getName()+""+ Arrays.asList(joinPoint.getArgs()));
    }

    @After("webLog()")
    public void logEnd(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature().getName() + "除非结束");
    }

    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void logReturn(Object result) {
        log.info("返回结果： {}", result);
    }

    @AfterThrowing(pointcut = "webLog()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.info("除法运算异常... {}", ex);
    }

    /**
     * 环绕通知：
     *   环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     *   环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     */
    @Around(value = "webLog()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        log.info("环绕通知的目标方法名："+proceedingJoinPoint.getSignature().getName());
        try {
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

}
