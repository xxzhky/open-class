package com.dt.open.delayed.jdk.didi;

public class DelayOrderWorker implements Runnable {

    @Override
    public void run() {
        //相关业务逻辑处理
        System.out.println(Thread.currentThread().getName()+" do something ……");
    }
}

