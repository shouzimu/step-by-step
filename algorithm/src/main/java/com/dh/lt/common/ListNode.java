package com.dh.lt.common;

import lombok.Data;

@Data
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public static ListNode initNode(int[] values) {
        ListNode head = new ListNode(values[0]);
        ListNode pre = head;
        for (int i = 1; i < values.length; i++) {
            if(null != pre.next){
                pre = pre.next;
            }
            ListNode node = new ListNode(values[i]);
            pre.next = node;
        }

        return head;
    }

    public void print() {
        ListNode node = this;
        while (node != null) {
            System.out.print(node.val + " ");
            node = node.next;
        }
    }
}
