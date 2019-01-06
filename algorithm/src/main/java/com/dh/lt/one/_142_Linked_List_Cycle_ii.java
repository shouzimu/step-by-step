package com.dh.lt.one;

import com.dh.lt.common.ListNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.com/problems/linked-list-cycle-ii/
 * <p>
 * 142. Linked List Cycle II
 * 题目的意思是返回链表开始循环的第一个节点
 *
 * <p>
 * Input: head = [3,2,0,-4], pos = 1
 * 由 -4指向1，所以循环开始的第一个节点值为2，index为1
 * <p>
 * Input: head = [1,2], pos = 0
 * 2->1按照顺序1是第一个节点，所以index为0
 */
public class _142_Linked_List_Cycle_ii {

    /**
     * 最简单的方式是使引入一个O(n)的存储空间来判断遍历过的节点
     * 第一次遇到add失败的节点就是循环开始的节点
     * <p>
     * 但是题目说能不能不使用额外空间来解决这个问题
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        Set<ListNode> set = new HashSet<>();
        ListNode onePrev = null;
        while (head != null) {
            boolean add = set.add(head);
            if (!add) {
                onePrev.next = head;
                return head;
            }
            onePrev = head;
            head = head.next;
        }
        return null;
    }


    /**
     * 不使用额外空间来解决这个问题
     *
     * @param head
     * @return
     * @TODO
     */
    public ListNode detectCycleV2(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode oneStep = head;
        ListNode twoStep = oneStep.next;
        while (oneStep.next != null && twoStep.next != null) {
            if (oneStep.equals(twoStep)) {
                //此时
                return oneStep;
            }
            oneStep = oneStep.next;
            twoStep = twoStep.next;
            if (twoStep.next != null) {
                twoStep = twoStep.next;
            }
        }
        return null;
    }


    @Test
    public void testDetectCycle() {
        ListNode node = ListNode.initNode(new int[]{3, 2, 0, -4});
        ListNode head = node;
        ListNode targetNode = null;
        while (head.next != null) {
            if (0 == head.val) {
                targetNode = head;
            }
            head = head.next;
            if (head.next == null) {
                head.next = targetNode;
                break;
            }
        }
        ListNode n = detectCycleV2(node);
        Assert.assertEquals(targetNode, n);
    }
}
