package com.gp.vip.ptn.algorithm.common;

import java.util.concurrent.Semaphore;

/**
 * @author: Fred
 * @date: 2020/6/17 6:20 下午
 * @description: (类的描述)
 */
public class LiuningThreadPrint {
    static final Semaphore semaphore = new Semaphore(1);

    static int count=1;

    static class ThreadA implements Runnable {

        @Override
        public void run() {
            while (count<100) {
                while (count % 3 != 0) {
                    semaphore.release();
                }
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadA " + count);
                count++;
                semaphore.release();
            }
        }
    }

    static class ThreadB implements Runnable {

        @Override
        public void run() {
            while (count < 100) {
                while (count % 3 != 1) {
                    semaphore.release();
                }
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadB " + count);
                count++;
                semaphore.release();
            }
        }
    }

    static class ThreadC implements Runnable {

        @Override
        public void run() {
            while (count < 100) {
                while (count % 3 != 2) {
                    semaphore.release();
                }
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadC " + count);
                count++;
                semaphore.release();
            }
        }
    }


    public static void main(String[] args) {
        new Thread(new ThreadA()).start();
        new Thread(new ThreadB()).start();
        new Thread(new ThreadC()).start();
    }
}
