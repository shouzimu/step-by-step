package com.dh;

import org.junit.Test;

import java.util.Random;
import java.util.stream.IntStream;

public class TestSortPoker {
    @Test
    public void sortPoker() {
        int length = 12;
        int poker[] = IntStream.range(0, length).toArray();
        //打乱
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int swap = poker[i];
            int randIndex = random.nextInt(length);
            poker[i] = poker[randIndex];
            poker[randIndex] = swap;
        }
        for (int i : poker) {
            System.out.print(i + ",");
        }

        //还原
        for (int i = 0; i < length; i++) {
            //a[index]!=index  交换两个值
            if (poker[i] != i) {
                int swap = poker[poker[i]];
                poker[i] = i;
                poker[swap] = swap;
            }
        }
        System.out.println("\n");
        for (int i : poker) {
            System.out.print(i + ",");
        }
    }

    @Test
    public void randomIndex() {
        IntStream.range(0, 10).forEach(i -> {
            Random random = new Random();
            int randIndex = random.nextInt(12);
            System.out.println(randIndex);
        });
    }
}
