package com.dt.open.delayed.jdk;

import java.util.concurrent.DelayQueue;

public class Consumer extends Thread {
    // 延时队列 ,消费者从其中获取消息进行消费
    private DelayQueue<Message> queue;

    private volatile boolean running=true;

    public Consumer(DelayQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Message take = queue.take();
                if (queue.isEmpty())
                    running=false;
                System.out.println("消费消息id：" + take.getId() + " 消息体：" + take.getBody());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
