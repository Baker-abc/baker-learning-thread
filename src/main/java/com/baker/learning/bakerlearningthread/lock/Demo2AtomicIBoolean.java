package com.baker.learning.bakerlearningthread.lock;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description Atomic 原子级别操作包
 * @Author
 * @Date 2020/3/9 23:18
 **/
public class Demo2AtomicIBoolean {

    // 默认为 false
    private static final AtomicBoolean sequenceNumber = new AtomicBoolean(true);

    public static void main(String[] args) {
        //获取当前值
        System.out.println(sequenceNumber.get());
        //实际值和预期值一样才更新。  返回true 则说明实际值和预期值一样，返回false 实际值和预期值不一样。
        System.out.println(sequenceNumber.compareAndSet(true, false));
        System.out.println(sequenceNumber.get());

    }
}
