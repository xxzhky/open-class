package com.gp.vip.ptn.algorithm.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author: Fred
 * @date: 2022/6/15 14:01
 * @description: (类的描述)
 */
public class FibonacciTest {

    static int computeCount = 0;

    static class Fibonacci extends RecursiveTask<Integer> {
        int n;

        Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            computeCount++;
            System.out.printf("Current thread is " + Thread.currentThread()
                    + "\n n = " + n + "\n");

            if (n <= 2)
                return 1;
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Fibonacci f2 = new Fibonacci(n - 2);
            f2.fork();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("wati temp answer is :" + n + "\n");
            int answer = f1.join() + f2.join();
            System.out.printf("temp answer is :" + answer + ",  n is :" + n + "\n");
            return answer;
        }
    }


    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(2);
        Fibonacci task = new Fibonacci(5);
        int answer = 0;
        answer = pool.invoke(task);
        System.out.printf("Hello answer is :" + answer + " , compute count is :" + computeCount);
    }
}

