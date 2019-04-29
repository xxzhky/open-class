package com.gp.vip.ptn.singleton.lazy;

public class LazyTwo {

    private LazyTwo(){}

    public static LazyTwo lazy= null;

    public synchronized static LazyTwo getInstance(){
        if (lazy==null){
            lazy= new LazyTwo();
        }
        return lazy;
    }
}
