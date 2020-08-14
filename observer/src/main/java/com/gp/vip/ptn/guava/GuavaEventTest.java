package com.gp.vip.ptn.guava;

import com.google.common.eventbus.EventBus;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @author: Fred
 * @date: 2020/8/2 1:17 下午
 * @description: (类的描述)
 */
public class GuavaEventTest {

    public static void main(String[] args) {
        EventBus bus= new EventBus();

        GuavaEvent event= new GuavaEvent();

        bus.register(event);
        bus.post("tom");


        //Spring   使用观察者模式
        ContextLoaderListener  listener= new ContextLoaderListener();
    }
}
