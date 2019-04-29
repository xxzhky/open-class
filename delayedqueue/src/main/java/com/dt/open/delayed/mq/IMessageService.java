package com.dt.open.delayed.mq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface IMessageService {
    /**
     * 发送消息到队列
     * @param queueName 队列名称
     * @param message 消息内容
     */
    public void send(String queueName,String message);


    /**
     * 延迟发送消息到队列
     * @param queueName 队列名称
     * @param message 消息内容
     * @param times 延迟时间 单位毫秒
     */
    public void send(String queueName,String message,long times);
}

