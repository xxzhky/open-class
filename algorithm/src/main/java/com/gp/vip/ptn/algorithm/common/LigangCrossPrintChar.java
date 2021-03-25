package com.gp.vip.ptn.algorithm.common;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Fred
 * @date: 2020/6/10 1:42 下午
 * @description: CPC (类的描述)
 */
public class LigangCrossPrintChar {
    public static final char[] chars = {'A', 'B', 'C'};
    public static int status = 0;

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        LigangCrossPrintChar printABC = new LigangCrossPrintChar();

        T tA = printABC.new T('A');
        T tB = printABC.new T('B');
        T tC = printABC.new T('C');
        tA.start();
        tB.start();
        tC.start();
    }

    class T extends Thread{
        public char c;
        public T(char c) {
            this.c = c;
        }

        @Override
        public void run() {
            System.out.println(LigangCrossPrintChar.this);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this);
            synchronized (LigangCrossPrintChar.this) {
                for (int i = 0; i < 6; i ++) {
                    while (chars[atomicInteger.get() % 3] != c) {
                        try {
                            LigangCrossPrintChar.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(this.c);
                    atomicInteger.getAndIncrement();
                    LigangCrossPrintChar.this.notifyAll();
                }
            }
        }
    }
}
