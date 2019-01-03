package com.dh.lt;

import com.dh.lt.common.ListNode;
import org.junit.Test;

import java.util.Stack;

/**
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 * <p>
 * Given linked list: 1->2->3->4->5, and n = 2.
 * <p>
 * After removing the second node from the end, the linked list becomes 1->2->3->5.
 * <p>
 * <p>
 * 1、第一个思路是从末尾往前删，所以就利用了栈这个结构来存储实现倒置的操作
 * 找到待删节点的前一个节点并将它的next指向next.next即完成了删除操作
 * <p>
 * 2、看了下Solution，第一次遍历list得出总长度len，第二次遍历到(len-n)位置执行删除操作
 */
public class _19_RemoveNthNodeFromEndOfList {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = head;
        if (n < 1) {
            return first;
        }
        Stack<ListNode> stack = new Stack<>();
        while (head != null) {
            stack.push(head);
            head = head.next;
        }

        int stackSize = stack.size();
        //删除的是第一个节点
        if (n >= stackSize) {
            first = first.next;
        } else {
            //要删除节点的前一个节点
            ListNode prevNode = null;
            for (int i = 0; i <= n; i++) {
                prevNode = stack.pop();
            }
            prevNode.next = prevNode.next.next;

        }
        return first;
    }


    public ListNode removeNthFromEndV2(ListNode head, int n) {
        ListNode first = head;
        if (n < 1) {
            return first;
        }
        ListNode second = head;

        int len = 0;
        while (head != null) {
            head = head.next;
            len++;
        }
        int index = len - n - 1;
        while (index > 0) {
            second = second.next;
            index--;
        }
        if (index < 0) {
            first = first.next;
        } else {
            second.next = second.next.next;
        }
        return first;
    }

    @Test
    public void testRemoveNthFromEnd() {
        ListNode head = ListNode.initNode(new int[]{1, 2, 3, 4, 5});
        ListNode node = removeNthFromEndV2(head, 3);
        node.print();
    }
}
