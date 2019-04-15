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

    @Test
    public void testCreatePersistentSequential(){
        System.out.println(ZkSeq.createPersistentSequential("/seq/cao"));
    }



    @Test
    public void testCreatePersistent(){
        System.out.println(ZkSeq.createPersistent("/seq"));
    }

    @Test
    public void deletePath(){
        ZkSeq.deletePath("/seq/cao0000000005");
    }


    @Test
    public void getPath(){
        ZkSeq.getPath("/seq");
    }
}

