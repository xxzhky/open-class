package com.gp.vip.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class GuavaLmt {

    private  RateLimiter rateLimiter= RateLimiter.create(5);//qps=10


    public void doReq(String name){
        if (rateLimiter.tryAcquire()){
            //获得一个令牌
            System.out.println("name:"+name+"成功获得令牌。");
        }else {
            System.out.println("拒绝服务");
        }

    }
    public static void main(String[] args) {
        //一秒内并发访问20
        final CountDownLatch latch= new CountDownLatch(20);
        GuavaLmt lmt= new GuavaLmt();
        final Random random= new Random(10);

        for (int i=0;i<20;i++){
            final int finalI = i;
            new Thread(()->{
                try {
                    latch.await();
                    int sleepTime= random.nextInt(1000);
                    System.out.println("Tread ->"+Thread.currentThread().getName()+" sleep "+sleepTime);
                    Thread.sleep(sleepTime);
                    lmt.doReq("t-"+ finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();

            latch.countDown();
        }

    }

    @Test
    public void testWeekOfYear(){
        long startTime1 = 1530613938532l;
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置星期一为一周开始的第一天
        calendar.setMinimalDaysInFirstWeek(4);//可以不用设置
        calendar.setTimeInMillis(System.currentTimeMillis());//获得当前的时间戳
        int weekYear = calendar.get(Calendar.YEAR);//获得当前的年
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);//获得当前日期属于今年的第几周


        calendar.setWeekDate(weekYear, weekOfYear, 2);//获得指定年的第几周的开始日期
        long starttime = calendar.getTime().getTime();//创建日期的时间该周的第一天，
        calendar.setWeekDate(weekYear, weekOfYear, 1);//获得指定年的第几周的结束日期
        long endtime = calendar.getTime().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
        String dateStart = simpleDateFormat.format(starttime);//将时间戳格式化为指定格式
        String dateEnd = simpleDateFormat.format(endtime);
        System.out.println("第几周："+weekOfYear);
        System.out.println(dateStart);
        System.out.println(dateEnd);
        System.out.println(String.format("第"+"%s"+"周 "+"["+"%s"+"-"+"%s"+"]"+"%n",weekOfYear,dateStart,dateEnd));
    }
}
