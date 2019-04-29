package com.dt.open.delayed.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
/**
 * rabbit 死信消息载体
 * 发送延迟队列时，先把消息存在此对象中，在加上目的地队列名称，然后再发到死信队列中，
 * 当消息超时时，转发到转发队列，添加对转发队列的监听，消费转发队列，获取需要延迟发送的信息，该信息就是DLXMessage对象，
 * 这样就拿到了目的地队列名称，然后再发送一次消息，就完成了延迟队列的发送
 */
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class DLXMessage implements Serializable {

    private static final long serialVersionUID = 9956432152000L;
    private String exchange;
    private String queueName;
    private String content;
    private long times;

    public DLXMessage(String queueName, String content, long times) {
        super();
        this.queueName = queueName;
        this.content = content;
        this.times = times;
    }
}