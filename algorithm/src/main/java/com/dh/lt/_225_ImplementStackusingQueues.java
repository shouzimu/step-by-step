package com.dh.lt;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/implement-stack-using-queues/
 * <p>
 * Implement the following operations of a stack using queues.
 * <p>
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * empty() -- Return whether the stack is empty.
 *
 *
 * 问题，第一次使用了LinkedBlockingQueue作为队列的实现，最后run code的时候提示了
 * error: cannot find symbol: class LinkedBlockingQueue
 * 应该是leetcode不支持使用juc下的代码，查看Queue发现有一个LinkedList的实现，所以改成LinkedList
 *
 *
 * 后面查看Discuss发现大家都是用一个队列来实现，先插入，然后再反转，将最后的加入的元素置于队头
 * 不得不感叹自己的想法太过于狭窄。
 */
public class _225_ImplementStackusingQueues {
    class MyStack {

        Queue<Integer> pushQueue;
        Queue<Integer> popQueue;

        /**
         * Initialize your data structure here.
         */
        public MyStack() {
            pushQueue = new LinkedList<>();
            popQueue = new LinkedList<>();
        }

        /**
         * Push element x onto stack.
         */
        public void push(int x) {
            pushQueue.add(x);
        }

        /**
         * Removes the element on top of the stack and returns that element.
         * 返回最后一个入队的数，即队尾
         */
        public int pop() {
            Integer last = null;
            if (!empty()) {
                if (!pushQueue.isEmpty()) {
                    while (!pushQueue.isEmpty()) {
                        last = pushQueue.poll();
                        if (!pushQueue.isEmpty()) {
                            popQueue.add(last);
                        }
                    }
                } else {
                    while (!popQueue.isEmpty()) {
                        last = popQueue.poll();
                        if (!popQueue.isEmpty()) {
                            pushQueue.add(last);
                        }
                    }
                }

            }
            return last;
        }

        /**
         * Get the top element.
         */
        public int top() {
            Integer last = null;
            if (!empty()) {
                if (!pushQueue.isEmpty()) {
                    while (!pushQueue.isEmpty()) {
                        last = pushQueue.poll();
                        popQueue.add(last);
                    }
                } else {
                    while (!popQueue.isEmpty()) {
                        last = popQueue.poll();
                        pushQueue.add(last);
                    }
                }

            }
            return last;
        }

        /**
         * Returns whether the stack is empty.
         */
        public boolean empty() {
            return popQueue.isEmpty() && pushQueue.isEmpty();
        }
    }

    @Test
    public void testStackQueue() {
        MyStack stack = new MyStack();

        stack.push(1);
        stack.push(2);
        System.out.println(stack.top());;   // returns 2
        System.out.println(stack.pop());;   // returns 2
        System.out.println(stack.empty());; // returns false


        System.out.println("succes");
    }

}
