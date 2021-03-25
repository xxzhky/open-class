package com.gp.vip.ptn.algorithm.common;

/**
 * @author: Fred
 * @date: 2020/6/17 6:15 下午
 * @description: (类的描述)
 */
public class ZhangjunqingMultiThreadPrint {


        public static void main(String[] args) {
            PrintNum p=new PrintNum();
            Thread t1=new Thread(p);
            Thread t2=new Thread(p);
            t1.setName("奇数");
            t2.setName("偶数");
            t1.start();
            t2.start();
        }
    }
    class PrintNum implements Runnable {
        int num = 1;

        @Override
        public void run() {
            synchronized (this) {
                while (true) {
                    notify();
                    if (num <= 9) {
                        System.out.println(Thread.currentThread().getName() + ":" + num);
                        num++;
                    } else {
                        break;
                    }
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

