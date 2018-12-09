package com.dh.lt;

import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/sliding-window-maximum/
 * <p>
 * Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
 * Output: [3,3,5,5,6,7]
 * Explanation:
 * <p>
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 * 1 [3  -1  -3] 5  3  6  7       3
 * 1  3 [-1  -3  5] 3  6  7       5
 * 1  3  -1 [-3  5  3] 6  7       5
 * 1  3  -1  -3 [5  3  6] 7       6
 * 1  3  -1  -3  5 [3  6  7]      7
 * <p>
 * <p>
 * 解决方法，使用双端队列，dequeue
 */
public class _239_SlidingWindowMaximum {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (k == 0 || null == nums) {
            return new int[0];
        }
        int length = nums.length;
        if (length == 0) {
            return new int[0];
        }
        int[] res = new int[length - k + 1];
        int j = 0;
        //存储当前窗口的下标
        Deque<Integer> deque = new LinkedList();
        for (int i = 0; i < length; i++) {
            //新元素进来后，判断队列的最大值（即队头）的下标和当前数组下标的差值是否大于k,大于则移除
            if (i >= k && deque.peek() <= i - k) {
                deque.pop();
            }
            //判断新加入的元素和队列里的元素比
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.add(i);
            if (i >= k - 1) {
                res[j++] = nums[deque.peek()];
            }

        }
        return res;
    }

    @Test
    public void testMaxSlidingWindow() {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int[] res = maxSlidingWindow(nums, 3);
        for (int r : res) {
            System.out.print(r + " ");
        }

    }
}
