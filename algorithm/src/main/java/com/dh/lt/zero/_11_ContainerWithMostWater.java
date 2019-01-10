package com.dh.lt.zero;


import org.junit.Assert;
import org.junit.Test;

/**
 * 11. Container With Most Water
 * <p>
 * https://leetcode.com/problems/container-with-most-water/
 */
public class _11_ContainerWithMostWater {

    public int maxArea(int[] height) {
        if (null == height || height.length < 2) {
            return 0;
        }
        int max = 0;
        for (int i = 0; i < height.length - 1; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int v = Math.min(height[i], height[j]);
                int area = v * (j - i);
                if (area > max) {
                    max = area;
                }
            }
        }

        return max;
    }

    public int maxAreav2(int[] height) {
        if (null == height || height.length < 2) {
            return 0;
        }
        int max = 0;
        int l = 0, r = height.length - 1;
        while (l < r) {
            int v = Math.min(height[l], height[r]);
            int area = v * (r - l);
            if (area > max) {
                max = area;
            }
            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }
        }

        return max;
    }

    @Test
    public void testMaxArea() {
        int[] input = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7,6};
        int res = maxArea(input);
        Assert.assertEquals(49, res);
        int res2 = maxAreav2(input);
        Assert.assertEquals(49, res2);
    }
}
