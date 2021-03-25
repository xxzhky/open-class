package com.gp.vip.ptn.algorithm.common;

public class LinkedReverse {
    //头结点插入
    public static ListNode reverseByInsert(ListNode listNode,ListNode head){
        ListNode node = listNode;
        while(node!= null){
            ListNode tempList = node.next;
            node.next = head.next;
            head.next = node;
            node = tempList;
        }
        return head.next;
    }

    //就地反转
    public static ListNode reverseByLocal(ListNode listNode,ListNode head){
        head.next= listNode;
        ListNode node = listNode;
        ListNode pNext = node.next;
        while (pNext!=null){
            node.next = pNext.next;
            pNext.next = head.next;
            head.next = pNext;
            pNext=node.next;
        }
        return head.next;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
