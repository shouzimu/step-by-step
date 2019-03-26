package com.dh.bean;

import com.dh.third.zk.ZkSeq;

public class IdUtil {

    private static Snowflake snowflake = new Snowflake(1,1);
    public static long id() {
        return ZkSeq.nextId();
    }


    public static long snowflakeId() {
        return snowflake.nextId();
    }
}
