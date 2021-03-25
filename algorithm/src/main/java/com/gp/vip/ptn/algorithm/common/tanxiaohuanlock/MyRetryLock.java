package com.gp.vip.ptn.algorithm.common.tanxiaohuanlock;

/**
 * create by tanxh on 2020-06-11
 *
 * 基于BaseMyLock扩展的无限重试的锁
 *
 **/

public class MyRetryLock extends BaseMyLock{

    protected boolean lock(Object obj){
        while (!super.lock(obj)){
            //一直重试获取锁 try块代码只用来测试 删除可以提高并发性
            try {
                System.out.println(Thread.currentThread().getName() + " 没获取到锁 重试ing");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

}
