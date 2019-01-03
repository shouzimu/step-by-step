package com.dh.lt.two;

import org.junit.Test;

import java.util.Stack;

/**
 * mplement the following operations of a queue using stacks.
 * <p>
 * push(x) -- Push element x to the back of queue.
 * pop() -- Removes the element from in front of queue.
 * peek() -- Get the front element.
 * empty() -- Return whether the queue is empty.
 */

/**
 * 处理方法，使用两个栈，一个输入栈、一个输出栈，队列里添加内容时都添加到输入栈中，
 * 遇到pop peek等输出操作时,如果输出栈为空，将输入栈的内容全部pop 然后push到输出栈
 */
public class _232_ImplementQueueusingStacks {
    class MyQueue {

        Stack<Integer> inputStack;
        Stack<Integer> outoutStack;

        /**
         * Initialize your data structure here.
         */
        public MyQueue() {
            inputStack = new Stack<>();
            outoutStack = new Stack<>();
        }

        /**
         * Push element x to the back of queue.
         */
        public void push(int x) {
            inputStack.push(x);
        }

        /**
         * Removes the element from in front of queue and returns that element.
         */
        public int pop() {
            if (outoutStack.isEmpty()) {
                while (!inputStack.isEmpty()) {
                    outoutStack.push(inputStack.pop());
                }
            }
            if (!outoutStack.isEmpty()) {
                return outoutStack.pop();
            } else {
                return 0;
            }
        }

        /**
         * Get the front element.
         */
        public int peek() {
            if (outoutStack.isEmpty()) {
                while (!inputStack.isEmpty()) {
                    outoutStack.push(inputStack.pop());
                }
            }
            if (!outoutStack.isEmpty()) {
                return outoutStack.peek();
            } else {
                return 0;
            }
        }

        /**
         * Returns whether the queue is empty.
         */
        public boolean empty() {
            return inputStack.isEmpty() && outoutStack.isEmpty();
        }
    }

    @Test
    public void testStackQueue() {
        MyQueue queue = new MyQueue();

        queue.push(1);
        queue.push(2);
        queue.push(3);
        System.out.println( queue.pop());   // returns 1
        System.out.println( queue.pop());   // returns 1

        queue.push(4);
        queue.push(5);
        System.out.println( queue.pop());   // returns 1
        System.out.println( queue.pop());   // returns 1
        System.out.println( queue.pop());   // returns 1


        System.out.println("succes");
    }
}
