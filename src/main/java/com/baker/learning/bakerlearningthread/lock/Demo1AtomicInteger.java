package com.baker.learning.bakerlearningthread.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description  Atomic 原子级别操作包
 * @Author
 * @Date 2020/3/9 23:18
 **/
public class Demo1AtomicInteger {

    private static final AtomicInteger sequenceNumber = new AtomicInteger(0);

    public static void main(String[] args) {
        //以原子方式将输入的数值与实例中原本的值相加，并返回最后的结果
        System.out.println(sequenceNumber.addAndGet(2));
        //将实例中的值更新为新值，并返回旧值
        System.out.println(sequenceNumber.getAndSet(5));
        //以原子的方式将实例中的原值进行加 1 操作，并返回最终相加后的结果
        System.out.println(sequenceNumber.incrementAndGet());
        //以原子的方式将实例中的原值加 1，返回的是自增前的旧值
        System.out.println(sequenceNumber.getAndIncrement());
        //获取当前值
        System.out.println(sequenceNumber.get());
    }
}
