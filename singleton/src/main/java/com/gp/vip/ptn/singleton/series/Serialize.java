package com.gp.vip.ptn.singleton.series;

import java.io.Serializable;

public class Serialize implements Serializable {
    private final static Serialize INSTANCE= new Serialize();

    private  Serialize(){}

    public static Serialize getInstance(){
        return INSTANCE;
    }
    private Object readResolve(){
        return INSTANCE;
    }
}
