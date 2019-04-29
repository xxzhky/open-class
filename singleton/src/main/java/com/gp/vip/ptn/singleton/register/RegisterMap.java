package com.gp.vip.ptn.singleton.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegisterMap {

    private static Map<String, Object> register= new ConcurrentHashMap<>();

    public static RegisterMap getInstance(String name){
        if (name==null){
            name= RegisterMap.class.getName();
        }

        if (register.get(name)==null){
            register.put(name, new RegisterMap());
        }

        return (RegisterMap) register.get(name);
    }

}
