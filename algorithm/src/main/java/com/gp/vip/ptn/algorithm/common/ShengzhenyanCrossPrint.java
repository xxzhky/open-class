package com.gp.vip.ptn.algorithm.common;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Fred
 * @date: 2020/7/9 5:50 下午
 * @description: (类的描述)
 */
public class ShengzhenyanCrossPrint {

        private static int i = 0;

        public static void main(String[] args) {
            Lock lock = new ReentrantLock();
            Condition c1 = lock.newCondition();
            Condition c2 = lock.newCondition();
            Condition c3 = lock.newCondition();

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        while (true) {
                            Thread.sleep(1000);
                            c1.await();
                            System.out.println("t1:" + i++);
                            c2.signal();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        while (true) {
                            Thread.sleep(1000);
                            c2.await();
                            System.out.println("t2:" + i++);
                            c3.signal();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            });

            Thread t3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        while (true) {
                            Thread.sleep(1000);
                            c3.await();
                            System.out.println("t3:" + i++);
                            c1.signal();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            });
            t1.start();
            t2.start();
            t3.start();
            try {
                Thread.sleep(1000);
                lock.lock();
                c1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


}
