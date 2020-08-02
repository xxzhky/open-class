package com.gp.vip.ptn.eventfred.corefred;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventListener {
    protected Map<String, Event> events = new HashMap<>();

    public void addListener(String eventType, Object target){
        try {
            this.addListener(eventType,
                    target,
                    target.getClass().getMethod("on"+ toUpperFirstCase(eventType), Event.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private String toUpperFirstCase(String eventType) {
        char[] cs= eventType.toCharArray();
        cs[0] -= 32;
        System.out.println(String.valueOf(cs));
        return String.valueOf(cs);
    }

    protected void addListener(String eventType, Object target, Method callback){
        events.put(eventType, new Event(target, callback));

    }

    //只要有动作，就触发
    private void trigger(Event event){
        event.setSource(this);
        event.setTime(System.currentTimeMillis());
        if (event.getCallback()!=null){
            try {
                event.getCallback().invoke(event.getTarget(), event);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    //事件名称触发
    protected void trigger(String trigger){
        if (!this.events.containsKey(trigger)){
            return;
        }
        trigger(this.events.get(trigger).setTrigger(trigger));
    }


}
