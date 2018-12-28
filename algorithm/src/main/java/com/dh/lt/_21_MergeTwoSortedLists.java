package com.dh.lt;

import com.dh.lt.common.ListNode;
import org.junit.Test;

/**
 * 合并两个有序链表
 * https://leetcode.com/problems/merge-two-sorted-lists/
 * <p>
 * Input: 1->2->4, 1->3->4
 * Output: 1->1->2->3->4->4
 * <p>
 * 这道题比较菜了，竟然花了1个小时，难度级别是easy
 * 踩到的坑，对于插入位置没有做合理的判断，仅跟当前节点比较是不对的
 * 后面改成 list2->list1时同时判断list1的当前值和下一个节点的值来判断插入位置，就OK了
 */
public class _21_MergeTwoSortedLists {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l2 == null && l1 == null) {
            return l1;
        }
        if (l1 == null && l2 != null) {
            return l2;
        }
        if (l2 == null && l1 != null) {
            return l1;
        }

        //判断第一个元素小的那一个作为结果链表
        if (l1.val <= l2.val) {
            return merge(l1, l2);
        } else {
            return merge(l2, l1);
        }

    }

    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode head = l1;
        while (l2 != null && l1.next != null) {
            ListNode l1next = l1.next;
            // 1、如果l2的值>=l1当前节点且<=下一个节点，则插入两个节点之间
            if (l2.val >= l1.val && null != l1next && l1next.val >= l2.val) {
                ListNode l2next = l2.next;
                l1.next = l2;
                l2.next = l1next;
                l1 = l1.next;
                l2 = l2next;
            } else {
                //2、否则list1下移一个节点
                l1 = l1.next;
            }
        }
        //3，如果测试l1已经走到末尾节点，则直接将节点2拼到它后面
        if (l1.next == null) {
            l1.next = l2;
        }
        return head;
    }

    @Test
    public void testmergeTwoLists() {
        ListNode l1 = ListNode.initNode(new int[]{0, 1});
        ListNode l2 = ListNode.initNode(new int[]{1, 2});
        ListNode l3 = mergeTwoLists(l1, l2);
        l3.print();
    }
}
