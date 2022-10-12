package com.gp.vip.ptn.algorithm.threadpool;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: Fred
 * @date: 2022/10/12 23:30
 * @description: (类的描述)
 */
public class ThreadpoolTest {

    public void startThreads(ThreadPoolTaskExecutor taskExecutor, CountDownLatch countDownLatch,
                             int numThreads) {
        for (int i = 0; i < numThreads; i++) {
            taskExecutor.execute(() -> {
                try {
                    Thread.sleep(100L * ThreadLocalRandom.current().nextLong(1, 10));
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

//    @Test
//    public void whenUsingDefaults_thenSingleThread() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.afterPropertiesSet();
//
//        CountDownLatch countDownLatch = new CountDownLatch(10);
//        this.startThreads(taskExecutor, countDownLatch, 10);
//
//        while (countDownLatch.getCount() > 0) {
//            Assert.assertEquals(1, taskExecutor.getPoolSize());
//        }
//    }
//
//    @Test
//    public void whenCorePoolSizeFive_thenFiveThreads() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(5);
//        taskExecutor.afterPropertiesSet();
//
//        CountDownLatch countDownLatch = new CountDownLatch(10);
//        this.startThreads(taskExecutor, countDownLatch, 10);
//
//        while (countDownLatch.getCount() > 0) {
//            Assert.assertEquals(5, taskExecutor.getPoolSize());
//        }
//    }
//
//    @Test
//    public void whenCorePoolSizeFiveAndMaxPoolSizeTen_thenFiveThreads() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(5);
//        taskExecutor.setMaxPoolSize(10);
//        taskExecutor.afterPropertiesSet();
//
//        CountDownLatch countDownLatch = new CountDownLatch(10);
//        this.startThreads(taskExecutor, countDownLatch, 10);
//
//        while (countDownLatch.getCount() > 0) {
//            Assert.assertEquals(5, taskExecutor.getPoolSize());
//        }
//    }
//
//    @Test
//    public void whenCorePoolSizeFiveAndMaxPoolSizeTenAndQueueCapacityTen_thenTenThreads() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(5);
//        taskExecutor.setMaxPoolSize(10);
//        taskExecutor.setQueueCapacity(10);
//        taskExecutor.afterPropertiesSet();
//
//        CountDownLatch countDownLatch = new CountDownLatch(20);
//        this.startThreads(taskExecutor, countDownLatch, 20);
//
//        while (countDownLatch.getCount() > 0) {
//            Assert.assertEquals(10, taskExecutor.getPoolSize());
//        }
//    }

}
