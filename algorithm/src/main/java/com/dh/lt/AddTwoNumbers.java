package com.dh.lt;

import org.junit.Test;

/**
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 *
 * 这个题是末位补零，前面对齐，很坑
 */
public class AddTwoNumbers {

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }
    }

    //前面补0的算法
    public ListNode addTwoNumbersV2(ListNode nl1, ListNode nl2) {
        int sum = (nl1.val + nl2.val)%10;
        int carry =  (nl1.val + nl2.val)/10;
        ListNode res = new ListNode(sum);
        ListNode curr = res;
        //从前往后相加
        while (nl1.next != null && nl2.next != null){
            int tempSum = carry + (nl1.next.val + nl2.next.val);
            sum = tempSum%10;
            ListNode n = new ListNode(sum);
            curr.next = n;
            curr = curr.next;
            carry = tempSum/10;
            nl1 = nl1.next;
            nl2 = nl2.next;
        }

        while (nl1.next != null){
            int tempSum = carry + nl1.next.val;
            sum = tempSum%10;
            ListNode n = new ListNode(sum);
            curr.next = n;
            curr = curr.next;
            carry = tempSum/10;
            nl1 = nl1.next;
        }

        while (nl2.next != null){
            int tempSum = carry + nl2.next.val;
            sum = tempSum%10;
            ListNode n = new ListNode(sum);
            curr.next = n;
            curr = curr.next;
            carry = tempSum/10;
            nl2 = nl2.next;
        }

        //如果最后还有补位，则进一
        if(carry > 0){
            ListNode n = new ListNode(carry);
            curr.next = n;
        }
        return res;
    }


    //前面补0的算法
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //从末尾开始加,倒置
        ListNode nl1 = new ListNode(l1.val);
        ListNode nl2 = new ListNode(l2.val);

        //反转两个ListNode
        while (l1.next != null) {
            ListNode n = nl1;
            nl1 =  new ListNode(l1.next.val);
            nl1.next = n;
            l1 = l1.next;
        }
        while (l2.next != null) {
            ListNode n = nl2;
            nl2 =  new ListNode(l2.next.val);
            nl2.next = n;
            l2 = l2.next;
        }

        int sum = (nl1.val + nl2.val)%10;
        int carry =  (nl1.val + nl2.val)/10;
        ListNode res = new ListNode(sum);
        ListNode curr = res;
        //从前往后相加
        while (nl1.next != null && nl2.next != null){
            int tempSum = carry + (nl1.next.val + nl2.next.val);
            sum = tempSum%10;
            ListNode n = new ListNode(sum);
            curr.next = n;
            curr = curr.next;
            carry = tempSum/10;
            nl1 = nl1.next;
            nl2 = nl2.next;
        }

        while (nl1.next != null){
            int tempSum = carry + nl1.next.val;
            sum = tempSum%10;
            ListNode n = new ListNode(sum);
            curr.next = n;
            curr = curr.next;
            carry = tempSum/10;
            nl1 = nl1.next;
        }

        while (nl2.next != null){
            int tempSum = carry + nl2.next.val;
            sum = tempSum%10;
            ListNode n = new ListNode(sum);
            curr.next = n;
            curr = curr.next;
            carry = tempSum/10;
            nl2 = nl2.next;
        }
        return res;
    }


    @Test
    public void testAddTwoNumbers() {
        ListNode l1 = new ListNode(2);
        ListNode l12 = new ListNode(4);
        ListNode l13 = new ListNode(3);
        l12.setNext(l13);
        l1.setNext(l12);


        ListNode l2 = new ListNode(5);
        ListNode l22 = new ListNode(6);
        ListNode l23 = new ListNode(4);
        ListNode l24 = new ListNode(0);
        l23.setNext(l24);
        l22.setNext(l23);
        l2.setNext(l22);

        ListNode res1 = addTwoNumbers(l1,l2);
        ListNode res2 = addTwoNumbersV2(l1,l2);
        System.out.print("res1 : ");
        print(res1);
        System.out.print("\nres2 : ");
        print(res2);
    }

    public void print(ListNode res){
        System.out.print(res.val+ " ");
        while (res.next != null){
            System.out.print(res.next.val+ " ");
            res = res.next;
        }
    }

}

