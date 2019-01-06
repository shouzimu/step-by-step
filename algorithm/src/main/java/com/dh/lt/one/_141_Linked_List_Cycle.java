package com.dh.lt.one;

import com.dh.lt.common.ListNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/linked-list-cycle/
 * <p>
 * 141. Linked List Cycle
 * 判断链表是否存在环
 * <p>
 * Input: head = [3,2,0,-4], pos = 1
 * Output: true
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 */
public class _141_Linked_List_Cycle {

    /**
     * 极客时间上覃超老师那学到的解法，很机智，就是使用两个步长不一致的指针，分别往后走，只要存在环，那么这两个指针就会指到
     * 同一个节点上
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode oneStep = head;
        ListNode twoStep = oneStep.next;
        while (oneStep.next != null && twoStep.next != null) {
            if (oneStep.equals(twoStep)) {
                return true;
            }
            oneStep = oneStep.next;
            twoStep = twoStep.next;
            if (twoStep.next != null) {
                twoStep = twoStep.next;
            }
        }
        return false;
    }


    /**
     * 常规解法，将遍历过的节点存在Set中，遍历时进行进行判重，如果存在则break，说明存在环
     *
     * @param head
     * @return
     */
    public boolean hasCycleV2(ListNode head) {
        if (head == null) {
            return false;
        }
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            boolean notExist = set.add(head);
            if (!notExist) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    @Test
    public void testHashCycle() {
        ListNode node = ListNode.initNode(new int[]{1, 2, 3, 4, 5});
        ListNode head = node;
        while (head.next != null) {
            head = head.next;
            if (head.next == null) {
                head.next = node;
                break;
            }
        }
        Assert.assertEquals(true, hasCycleV2(node));
    }
}
