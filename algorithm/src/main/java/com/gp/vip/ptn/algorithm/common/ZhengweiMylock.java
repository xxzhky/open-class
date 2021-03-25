package com.gp.vip.ptn.algorithm.common;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author: Fred
 * @date: 2020/8/2 1:16 上午
 * @description: (类的描述)
 */
public class ZhengweiMylock implements Lock {



        private static class Sync extends AbstractQueuedSynchronizer
        {

            @Override
            protected boolean isHeldExclusively()
            {
                return getState() == 1;
            }


            @Override
            protected boolean tryAcquire(int acquires)
            {
                if (compareAndSetState(0, 1))
                {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
                return false;
            }


            @Override
            protected boolean tryRelease(int release)
            {
                if (getState() == 0)
                {
                    throw new IllegalMonitorStateException();
                }
                setExclusiveOwnerThread(null);
                setState(0);
                return true;
            }


            Condition newCondition()
            {
                return new ConditionObject();
            }
        }

        private final Sync sync = new Sync();

        public void lock()
        {
            sync.acquire(1);
        }

        public boolean tryLock()
        {
            return sync.tryAcquire(1);
        }

        public void unlock()
        {
            sync.release(1);
        }

        public Condition newCondition()
        {
            return sync.newCondition();
        }

        public boolean isLock()
        {
            return sync.isHeldExclusively();
        }

        public boolean hasQueuedThread()
        {
            return sync.hasQueuedThreads();
        }

        public void lockInterruptibly() throws InterruptedException
        {
            sync.acquireInterruptibly(1);
        }

        public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException
        {
            return sync.tryAcquireNanos(1, unit.toNanos(timeout));
        }


}
