package com.dh.lt.zero;

import org.junit.Assert;
import org.junit.Test;

/**
 * 26. Remove Duplicates from Sorted Array
 * <p>
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 */
public class _26_RemoveDuplicatesFromSortedArray {

    public int removeDuplicates(int[] nums) {
        return 0;
    }


    @Test
    public void testRemoveDuplicates() {
        int[] arry = new int[]{-1, 0, 0, 0};
        int res = removeDuplicates(arry);
        Assert.assertEquals(2, res);
    }
}
