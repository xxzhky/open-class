package com.dt.open.delayed.mq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = MQConstant.HELLO_QUEUE_NAME)
public class ReceiverMessage {

    @RabbitHandler
    public void process(String content) {
        System.out.println("接受时间:"+ System.currentTimeMillis());
        System.out.println("接受消息:" + content);
    }
}
