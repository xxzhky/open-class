package com.gp.vip.ptn.algorithm.common;

import java.util.ArrayList;

/**
 * @author: Fred
 * @date: 2020/8/29 1:02 上午
 * @description: (类的描述)
 */
public class Test {

    public static void main(String[] args) {
        ArrayList<String> l1 = new ArrayList<String>();
        ArrayList<Integer> l2 = new ArrayList<Integer>();
        l1.add("1");
        l2.add(1);
        System.out.println(l1.get(0).getClass());
        System.out.println(l2.get(0).getClass());
        System.out.println(l1.getClass() == l2.getClass());


        System.out.println(test("99999"));

    }

    private static final ArrayList<String> list = new ArrayList<>();
    public static String test(String j){
        int i = 1, s = 1, f = 1, a = 1, b = 1,c = 1,d = 1,e = 1;
        list.add(new String("11111111111111111111111111111"));
        return test(s+i+f+a+b+c+d+e+"");


    }


}
class Test2{
    static {
        System.out.print("OK");

    }

    public static final String a=new String("JDK");

}
abstract class U{

}