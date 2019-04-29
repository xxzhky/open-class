package com.dt.open.delayed.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SysInitLoad implements ApplicationRunner {

    @Autowired
    private IMessageService messageService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("发送时间:"+ System.currentTimeMillis());
        String message = "测试延迟消息";
        messageService.send(MQConstant.HELLO_QUEUE_NAME,message,6000);

        message = "测试普通消息";
        messageService.send(MQConstant.HELLO_QUEUE_NAME,message);
    }
}