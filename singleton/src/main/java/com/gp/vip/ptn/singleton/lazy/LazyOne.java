package com.gp.vip.ptn.singleton.lazy;

public class LazyOne {

    private LazyOne(){}

    public static LazyOne lazy= null;

    public static LazyOne getInstance(){
        if (lazy==null){
            lazy= new LazyOne();
        }
        return lazy;
    }
}
