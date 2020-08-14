package com.gp.vip.ptn.algorithm.limiter;

import redis.clients.jedis.Jedis;

/**
 * @author: Fred
 * @date: 2020/8/7 10:23 下午
 * @description:
 *
 * 借助Redis的有序集合ZSet来实现时间窗口算法限流，实现的过程是：
 *
 * 第一步：先使用ZSet的key存储限流的ID，score用来存储请求的时间。
 *
 * 第二步：每次有请求访问来了之后，先清空之前时间窗口的访问量，统计目前时间窗口的个数与最大允许访问量对比。
 *
 * 第三步：如果大于等于最大访问量则返回 false 执行限流操策略，负责允许执行业务逻辑，并且在 ZSet 中添加一条有效的访问记录。
 */
public class RedisTimeWindow {

    /**
     * 6种限流方法之服务端时间窗口算法
     * * @author www.jiagou1216.com
     */
    public static class RedisLimit {
        /**
         * Redis 操作客户端
         */
        static Jedis jedis = new Jedis("60.205.187.105", 6379);
        static {
            jedis.auth("ygv7650");
        }

        public static void main(String[] args) throws InterruptedException {
            for (int i = 0; i < 100; i++) {
                boolean res = isPeriodLimiting("jiagou1216.com", 3, 10);
                if (res) {
                    System.out.println("正常访问【https://www.jiagou1216.com】:" + i);
                } else {
                    System.out.println("限流访问【https://www.jiagou1216.com】:" + i);
                }
            }        // 休眠 4s
            Thread.sleep(4000);
            // 超过最大执行时间之后，再从发起请求
            boolean res = isPeriodLimiting("jiagou1216.com", 3, 10);
            if (res) {
                System.out.println("休眠后，正常执行请求");
            } else {
                System.out.println("休眠后，被限流");
            }
        }

        /**
         * 限流方法（滑动时间算法/时间窗口算法）
         * * @param key      限流标识
         * * @param period   限流时间范围（单位：秒）
         * * @param maxCount 最大运行访问次数
         * * @return 是否限流
         */
        private static boolean isPeriodLimiting(String key, int period, int maxCount) {
            // 当前时间戳
            long nowTs = System.currentTimeMillis();
            // 删除非时间段内的请求数据（清除老访问数据，比如 period=60 时，标识清除 60s 以前的请求记录）
            jedis.zremrangeByScore(key, 0, nowTs - period * 1000);
            //System.out.println(jedis.get("1234"));
            // 当前请求次数
            long currCount = jedis.zcard(key);
            if (currCount >= maxCount) {
                // 超过最大请求次数，执行限流
                return false;
            }
            // 未达到最大请求数，正常执行业务,请求记录 +1
            jedis.zadd(key, nowTs, "" + nowTs);
            return true;

        }

    }
}
