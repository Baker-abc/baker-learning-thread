package com.baker.learning.bakerlearningthread.methods;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @desc CountDownLatch全部同时进行------模拟并发
 * @date 2020/2/10 0010 - 17:55
 **/
@Slf4j
public class Demo05CountDownLatch {

    public static void main(String[] args) {

        final CountDownLatch start = new CountDownLatch(3);
        final CountDownLatch end = new CountDownLatch(1);

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                log.info("========= 运动员 " + Thread.currentThread().getId() + "  就位==========");
                start.countDown();
                end.await();
                log.info("========= 运动员 " + Thread.currentThread().getId() + "  开跑==========");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
                log.info("========= 运动员 " + Thread.currentThread().getId() + "  就位==========");
                start.countDown();
                end.await();
                log.info("========= 运动员 " + Thread.currentThread().getId() + "  开跑==========");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                log.info("========= 运动员 " + Thread.currentThread().getId() + "  就位==========");
                start.countDown();
                end.await();
                log.info("========= 运动员 " + Thread.currentThread().getId() + "  开跑==========");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            log.info("============= 运动员准备 ================");
            start.await();
            log.info("============= 发令枪响了 ================");
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
