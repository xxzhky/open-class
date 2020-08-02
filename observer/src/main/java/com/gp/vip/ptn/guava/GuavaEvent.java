package com.gp.vip.ptn.guava;

import com.google.common.eventbus.Subscribe;

/**
 * @author: Fred
 * @date: 2020/8/2 1:12 下午
 * @description: (类的描述)
 */
public class GuavaEvent {

    @Subscribe
    public void subscribe(String str){
        System.out.println("执行方法，入参是 "+ str);
    }
}
