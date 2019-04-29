package com.gp.vip.ptn.singleton.lazy;

import com.gp.vip.ptn.singleton.register.RegisterMap;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ThreadSafeTest {

    @Test
    public void timeConsuming(){
        long start =System.currentTimeMillis();
        for (int i=0; i<2000000; i++) {

            Object obj= RegisterMap.getInstance(null);

        }
        System.out.println("总耗时："+ (System.currentTimeMillis()-start));
    }

    @Test
    public void getInstance() {

        int count=200;

        CountDownLatch countDownLatch= new CountDownLatch(count);

        long start =System.currentTimeMillis();
        for (int i=0; i<count; i++) {

            new Thread() {
                public void run() {

                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Object obj= LazyOne.getInstance();
                    System.out.println(System.currentTimeMillis()+":"+obj);
                }
            }.start();

            countDownLatch.countDown();
        }

        try {
            Thread.sleep(1000);
            System.out.println("the latest instance : "+LazyOne.getInstance());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //System.out.println("总耗时："+ (System.currentTimeMillis()-start));
    }
}