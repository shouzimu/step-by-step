package com.dh.lt;

import org.junit.Test;

import java.util.*;

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


    @Test
    public void testThreeSum() {
        int[] nums = {-2,0,0,2,2};
        List<List<Integer>> res = threeSum(nums);
        for (List<Integer> re : res) {
            re.forEach(r -> System.out.print(r + " "));
            System.out.println();
        }
    }
}
