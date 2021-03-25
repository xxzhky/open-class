package com.gp.vip.ptn.algorithm.test;

/**
 * @author: Fred
 * @date: 2020/8/23 10:06 上午
 * @description: (类的描述)
 */
public class Test03 {

    public String name;

    public Test03(String name) {
        this.name = name;
    }

    static void transform(StringBuffer s, StringBuffer s2, int a, Integer b, Test03 c, String d) {
        s2 = s;
        s = new StringBuffer("new");
        s.append("hah");
        s2.append("hah");
        a--;
        b++;
        c.name += "hah";
        d += "hah";
    }

    public static void main(String[] args) {
        StringBuffer s = new StringBuffer("good");
        StringBuffer s2 = new StringBuffer("bad");
        int a = 3;
        Integer b = 4;
        Test03 c = new Test03("bob");
        String d = c.name;
        transform(s, s2, a, b, c, d);
        System.out.println(s);
        System.out.println(s2);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c.name);
        System.out.println(d);
    }


}
