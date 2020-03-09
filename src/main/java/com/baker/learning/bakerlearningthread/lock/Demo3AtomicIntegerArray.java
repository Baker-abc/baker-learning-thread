package com.baker.learning.bakerlearningthread.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Description Atomic 原子级别操作包
 * @Author
 * @Date 2020/3/9 23:18
 **/
public class Demo3AtomicIntegerArray {

    private static int[] value = new int[]{1, 2, 3, 4, 5};
    private static final AtomicIntegerArray sequenceNumber = new AtomicIntegerArray(value);

    public static void main(String[] args) {

        //以原子更新的方式将数组中索引为 i 的元素与输入值相加
        System.out.println(sequenceNumber.addAndGet(3, 10));
        //以原子更新的方式将数组中索引为 i 的元素自增加 1
        System.out.println(sequenceNumber.incrementAndGet(0));
        System.out.println(sequenceNumber.compareAndSet(4, 5, 10));
        // 将数组中索引为 i 的位置的元素进行更新
        System.out.println(sequenceNumber.get(4));

    }
}
