package com.dt.open.hotloaded.plugin;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ServerLogPlugin implements MethodBeforeAdvice {

    private List logs= new ArrayList(4096);
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        String time= SimpleDateFormat.getDateTimeInstance().format(new Date());
        String log= String.format("%s %s.%s() 参数：%s", time, method.getDeclaringClass().getName(),
                Arrays.toString(args));
        logs.add(log);
        System.out.println(log);
    }
}
