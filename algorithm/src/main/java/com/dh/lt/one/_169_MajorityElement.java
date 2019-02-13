package com.dh.lt.one;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 169. Majority Element
 * <p>
 * https://leetcode.com/problems/majority-element/
 * <p>
 * 求众数
 */
public class _169_MajorityElement {

    public int majorityElement(int[] nums) {
        if (null == nums || nums.length == 0) {
            return 0;
        }
        float half = (float) nums.length / 2;
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            Integer c = countMap.get(num);
            if (c == null) {
                c = 0;
            }
            c++;
            countMap.put(num, c);
            if (c * 1.0f > half) {
                return num;
            }
        }
        return nums[0];
    }

    @Test
    public void testMajorityElement() {
        Assert.assertEquals(3, majorityElement(new int[]{3, 2, 3}));
        Assert.assertEquals(2, majorityElement(new int[]{2,2,1,1,1,2,2}));
        Assert.assertEquals(5, majorityElement(new int[]{6, 5, 5}));
    }
}
