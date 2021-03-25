package com.gp.vip.ptn.algorithm.common;

/**
 * @author: Fred
 * @date: 2020/8/25 3:45 下午
 * @description: (类的描述)
 */
public class CrossPrint {

    public static void main(String[] args) {

        new Task1().start();
        new Task2().start();

    }

    private static volatile int  num=0;

    static class Task1 extends Thread{

        public void run (){

            try {
                this.wait();
                num++;
            } catch (InterruptedException e) {

            }finally {
                this.notifyAll();
            }

        }

    }
    static class Task2 extends Thread{

        public void run (){

            try {
                this.wait();
                num++;
            } catch (InterruptedException e) {

            }finally {
                this.notifyAll();
            }

        }

    }

}
