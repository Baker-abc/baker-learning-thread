package com.baker.learning.bakerlearningthread.lock;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Description Atomic 原子级别操作包 AtomicStampedReference解决ABA问题
 * @Author
 * @Date 2020/3/9 23:18
 **/
public class Demo6AtomicStampedReference {


    public static void main(String[] args) {
        String str1 = "aaa";
        String str2 = "bbb";
        //设置初始版本
        AtomicStampedReference<String> reference = new AtomicStampedReference<>(str1,1);
        //版本加1
        reference.compareAndSet(str1,str2,reference.getStamp(),reference.getStamp()+1);
        System.out.println("reference.getReference() = " + reference.getReference());

        //版本再加1
        boolean b = reference.attemptStamp(str2, reference.getStamp() + 1);
        System.out.println("b: "+b);
        System.out.println("reference.getStamp() = "+reference.getStamp());

        //再和str2比较，传入版本4 版本不一致 set失败
        boolean c = reference.weakCompareAndSet(str2,"ccc",4, reference.getStamp()+1);
        System.out.println("reference.getReference() = "+reference.getReference());
        System.out.println("c = " + c);
    }

}
