package com.dh.lt;

import org.junit.Test;

import java.util.*;

/**
 * https://leetcode.com/problems/3sum/
 * <p>
 * Given array nums = [-1, 0, 1, 2, -1, -4],
 * <p>
 * A solution set is:
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 */
public class _15_3Sum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            List<Integer> ints = map.get(num);
            if (null == ints) {
                ints = new ArrayList<>();
            }
            ints.add(i);
            map.put(num, ints);
        }
        Map<String, Integer> quchong = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 1; j++) {
                int mapValue = 0 - nums[i] - nums[j];
                List<Integer> v = map.get(mapValue);
                if (null != v) {
                    boolean f = true;
                    if (v.contains(i) && v.contains(j) && v.size() <= 2) {
                        f = false;
                    }
                    if ((v.contains(i) || v.contains(j)) && v.size() < 2) {
                        f = false;
                    }

                    if (f) {
                        List<Integer> valus = Arrays.asList(nums[i], nums[j], mapValue);
                        valus.sort(Comparator.comparingInt(r -> r));
                        if (null == quchong.put(valus.get(0) + "_" + valus.get(1), 0)) {
                            res.add(valus);
                        }
                    }
                }

            }
        }
        res.sort(Comparator.comparingInt((List<Integer> r) -> r.get(0)).thenComparing(r -> r.get(1)));
        return res;
    }

    /*

    解题思路

     */

    public List<List<Integer>> threeSumV2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                if (nums[i] + nums[left] + nums[right] > 0) {
                    right = right - 1;
                } else if (nums[i] + nums[left] + nums[right] < 0) {
                    left = left + 1;
                } else {
                    List<Integer> values = Arrays.asList(nums[i], nums[left], nums[right]);
                    res.add(values);
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;

                }
            }
        }
        return res;
    }

    @Test
    public void testThreeSum() {
        int[] nums = {-2, 0, 0, 2, 2};
        List<List<Integer>> res = threeSum(nums);

        for (List<Integer> re : res) {
            re.forEach(r -> System.out.print(r + " "));
            System.out.println();
        }

        List<List<Integer>> res2 = threeSumV2(nums);
        for (List<Integer> re : res2) {
            re.forEach(r -> System.out.print(r + " "));
            System.out.println();
        }
    }
}
