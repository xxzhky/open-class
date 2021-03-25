package com.gp.vip.ptn.algorithm.common.tanxiaohuanlock;

/**
 * create by tanxh on 2020-06-10
 **/

public class CASLockDemo implements Runnable{
    private MyRetryLock myLock;

    public CASLockDemo(MyRetryLock myLock) {
        this.myLock = myLock;
    }

    @Override
    public void run() {
        try{
            //尝试获取锁
            myLock.lock(this);
            //获取到锁执行操作
            doSomething();
        }finally {
            myLock.unLock(this);
        }
    }

    /**
     * 模拟处理业务 睡5秒
     */
    private void doSomething(){
        System.out.println(Thread.currentThread().getName() + " 获取到锁开始处理");
        sleep(5000);
        System.out.println(Thread.currentThread().getName() + " 业务处理完成");
    }


    private static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        MyRetryLock lock = new MyRetryLock();

        CASLockDemo lockDemo1 = new CASLockDemo(lock);
        CASLockDemo lockDemo2 = new CASLockDemo(lock);
        CASLockDemo lockDemo3 = new CASLockDemo(lock);
        new Thread(lockDemo1, "lockDemo-1").start();
        new Thread(lockDemo2, "lockDemo-2").start();
        new Thread(lockDemo2, "lockDemo-2-2").start();

        new Thread(lockDemo3, "lockDemo-3").start();
        new Thread(lockDemo3, "lockDemo-3-2").start();

        new Thread(lockDemo1, "lockDemo-1-2").start();
        new Thread(lockDemo1, "lockDemo-1-3").start();
        sleep(1000);
        new Thread(lockDemo1, "lockDemo-1-4").start();
        sleep(2000);
        new Thread(lockDemo1, "lockDemo-1-5").start();
        sleep(3000);

    }
}
