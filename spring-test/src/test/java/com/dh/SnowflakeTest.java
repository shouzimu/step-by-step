package com.dh;

import com.dh.bean.IdUtil;
import java.util.stream.IntStream;
import org.junit.Test;

public class SnowflakeTest {

    @Test
    public void testId() {
        IntStream.rangeClosed(0, 10).forEach(i -> {
            System.out.println(IdUtil.id());
            System.out.println("---00--0--");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }
}
