package com.gp.vip.ptn.algorithm.common;

/**
 * @author: Fred
 * @date: 2020/8/25 3:26 下午
 * @description: (类的描述)
 */
public class CrossPoint {


    public boolean crossDis (Node a, Node b){

        while(null!=a&&null!=b){
            if (a.pop()==b.pop()){
                return true;
            }
        }
        return false ;
    }



}
class Node  {
     Node node;
     Node next;

    public Node pop(){
        Node t=null;
        while (node!=null){
            t  = node.next;
        }
        return  t;
    }
}


