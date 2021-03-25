package com.gp.vip.ptn.algorithm.common.tanxiaohuanlock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * create by tanxh on 2020-06-11
 **/

public class BaseMyLock {
    //记录重入次数
    private AtomicInteger reentryCounter = new AtomicInteger(0);
    //记录当前持有锁的对象
    private Object currentLockObj;

    protected boolean lock(Object obj){
        if(reentryCounter.intValue() == 0){
            //当前无锁 cas方式获取锁
            boolean result = reentryCounter.compareAndSet(0, 1);
            if(result){
                currentLockObj = obj;
                System.out.println(Thread.currentThread().getName() + " 抢锁成功");
                return true;
            }else{
                System.out.println(Thread.currentThread().getName() + " 抢锁失败--------------------------");
                return false;
            }
        }

        if(obj != currentLockObj){
            System.out.println(Thread.currentThread().getName() + " 锁对象不一致，获取失败");
            return false;
        }else{
            //重入
            int i = reentryCounter.incrementAndGet();
            System.out.println(Thread.currentThread().getName() + " 获取到重入锁 当前锁深度 i=" + i);
            return true;
        }
    }

    protected boolean unLock(Object obj){
        if(reentryCounter.intValue() == 0){
            System.out.println(Thread.currentThread().getName() + " 当前没有锁，解锁失败");
            return false;
        }

        if(obj != currentLockObj){
            System.out.println(Thread.currentThread().getName() + " 锁对象不一致，解锁失败 !!!!!!!!!!!!!!!!!!!!!!!");
            return false;
        }else{
            int i = reentryCounter.decrementAndGet();
            assert i < 0:"i不能小于0";
            System.out.println(Thread.currentThread().getName() + " 解锁成功，目前锁深度为 i=" + i);
            return true;
        }

    }
}
