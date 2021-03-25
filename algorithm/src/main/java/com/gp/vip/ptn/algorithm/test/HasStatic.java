package com.gp.vip.ptn.algorithm.test;

/**
 * @author: Fred
 * @date: 2020/8/23 10:03 上午
 * @description: (类的描述)
 */
public class HasStatic {

    private static int x = 100;

    public static void main(String args[]) {
        HasStatic hs1 = new HasStatic();
        hs1.x++;
        HasStatic hs2 = new HasStatic();
        hs2.x++;
        hs1 = new HasStatic();
        hs1.x++;
        HasStatic.x--;
        System.out.println("x =" + x);


    }
}
