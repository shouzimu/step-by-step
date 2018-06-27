package com.dh.concurrent.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * ForkJoin
 */
public class FJ {


    public static class FJTask extends RecursiveTask<Long> {
        private long[] numbers;
        private int from;
        private int to;

        public FJTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            if (to - from < 10000) {
                return sum(numbers, from, to);
            } else {
                int middle = (to + from) >>> 1;
                FJTask left = new FJTask(numbers, from, middle);
                FJTask right = new FJTask(numbers, middle + 1, to);
                left.fork();
                right.fork();
                return left.join() + right.join();
            }
        }

        public long sum(long[] arrar, int from, int to) {
            long sum = 0;
            for (int i = from; i < to; i++) {
                sum += arrar[i];
            }
            return sum;
        }
    }


    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long[] arrs = LongStream.range(0, 100000).toArray();
        long res = forkJoinPool.invoke(new FJTask(arrs, 0, 100000 - 1));
        System.out.println(res);
    }
}
