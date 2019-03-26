package com.dh;


import com.dh.third.zk.ZkSeq;
import org.junit.Test;

public class ZkSeqTest {

    @Test
    public void testZkSeq(){
        System.out.println(ZkSeq.nextId());
        System.out.println(ZkSeq.nextId());
        System.out.println(ZkSeq.nextId());
        System.out.println(ZkSeq.nextId());
    }
}
