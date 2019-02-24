package com.dh.al.dp;

import org.junit.Test;

/**
 * ClassName: PackProblem
 *
 * @author lihong <intelcorecpu@gmail.com>
 * @Description: 对于一组不同重量、不可分割的物品,我们需要选择一些装入背包
 * @date 2019/2/8 23:02
 */
public class PackProblem {

    public final int[] items = new int[]{2, 2, 4, 6, 3};

    public final int maxWeight = 9;

    public final int maxAmount = 5;

    public static int resMax = Integer.MIN_VALUE;

    /**
     * 回溯法
     * @param i
     * @param cw
     */
    public void solutionBackTrack(int i, int cw) {
        if (cw == maxWeight || i == maxAmount) {
            if (cw > resMax)
                resMax = cw;
            return;
        }
        solutionBackTrack(i + 1, cw);
        if (cw + items[i] <= maxWeight) {
            solutionBackTrack(i + 1, cw + items[i]);
        }

    }

    @Test
    public void testSolutionV1() {
        solutionBackTrack(0, 0);
        System.out.println(resMax);
    }


}
