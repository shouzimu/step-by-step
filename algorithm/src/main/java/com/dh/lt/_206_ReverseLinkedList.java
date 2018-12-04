package com.dh.lt;

import org.junit.Test;

/**
 * https://leetcode.com/problems/reverse-linked-list/
 * 反转链表
 */
public class _206_ReverseLinkedList {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    '}';
        }
    }


    public ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        ListNode temp;
        while (null != head) {
            temp = newHead;
            newHead = head;
            head = head.next;
            newHead.next = temp;
        }
        return newHead;
    }

    @Test
    public void testReverseList() {
        ListNode l2 = new ListNode(1);
        ListNode l22 = new ListNode(2);
        ListNode l23 = new ListNode(3);
        ListNode l24 = new ListNode(4);
        l23.setNext(l24);
        l22.setNext(l23);
        l2.setNext(l22);

        ListNode res = reverseList(l2);
        print(res);
    }

    public void print(ListNode res) {
        System.out.print(res.val + " ");
        while (res.next != null) {
            System.out.print(res.next.val + " ");
            res = res.next;
        }
    }

}
