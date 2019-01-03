package com.dh.lt;

import com.dh.lt.common.ListNode;
import org.junit.Test;

/**
 * 24. Swap Nodes in Pairs
 * https://leetcode.com/problems/swap-nodes-in-pairs/
 * <p>
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 */
public class _24_SwapNodesInPairs {
    public ListNode swapPairs(ListNode head) {
        ListNode first = new ListNode(0);
        first.next = head;
        ListNode pre = first;
        while (pre.next != null && pre.next.next != null) {
            //交换next和next.next
            ListNode curr = pre.next;
            ListNode next = curr.next;
            pre.next = next;
            curr.next = next.next;
            next.next = curr;
            pre = curr;

        }
        return first.next;
    }

    @Test
    public void testSwapPairs() {
        ListNode head = ListNode.initNode(new int[]{1, 2, 3, 4, 5});
        head = swapPairs(head);
        head.print();
    }
}
