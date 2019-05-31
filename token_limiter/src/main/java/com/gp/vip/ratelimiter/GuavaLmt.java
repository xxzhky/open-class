package com.gp.vip.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class GuavaLmt {

    private  RateLimiter rateLimiter= RateLimiter.create(5);//qps=10


    public void doReq(String name){
        if (rateLimiter.tryAcquire()){
            //获得一个令牌
            System.out.println("name:"+name+"成功获得令牌。");
        }else {
            System.out.println("拒绝服务");
        }

    }
    public static void main(String[] args) {
        //一秒内并发访问20
        final CountDownLatch latch= new CountDownLatch(20);
        GuavaLmt lmt= new GuavaLmt();
        final Random random= new Random(10);

        for (int i=0;i<20;i++){
            final int finalI = i;
            new Thread(()->{
                try {
                    latch.await();
                    int sleepTime= random.nextInt(1000);
                    System.out.println("Tread ->"+Thread.currentThread().getName()+" sleep "+sleepTime);
                    Thread.sleep(sleepTime);
                    lmt.doReq("t-"+ finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();

            latch.countDown();
        }

    }
}
