package com.dh.lt.two;

import com.dh.lt.common.ListNode;
import org.junit.Test;

/**
 * https://leetcode.com/problems/reverse-linked-list/
 * 反转链表
 */
public class _206_ReverseLinkedList {

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
        l23.next = l24;
        l22.next = l23;
        l2.next = l22;

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
