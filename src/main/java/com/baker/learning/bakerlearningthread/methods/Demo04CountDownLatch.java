package com.baker.learning.bakerlearningthread.methods;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @desc 一个或者多个线程，等待其他多个线程完成某件事情之后才能执行
 * @date 2020/2/10 0010 - 17:55
 **/
@Slf4j
public class Demo04CountDownLatch {

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                log.info("========= " + Thread.currentThread().getId() + " ==========");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
                log.info("========= " + Thread.currentThread().getId() + " ==========");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                log.info("========= " + Thread.currentThread().getId() + " ==========");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            countDownLatch.await(10, TimeUnit.SECONDS);
            log.info("============= 所有线程全部进行完毕 ================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
