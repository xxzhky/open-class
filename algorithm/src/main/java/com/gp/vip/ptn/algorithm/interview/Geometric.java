package com.gp.vip.ptn.algorithm.interview;

/**
 * @author: Fred
 * @date: 2020/9/1 1:24 上午
 * @description: (类的描述)
 */
public class Geometric {

    public int sum(int a, double factor, int totalItems){
        int i, j,sum;
        j=1;
        sum=0;
        for (i=1; i<=totalItems; i++) {
            j=j*2;
            sum=sum+j;
            i++;
        }
        return sum;
    }
}
