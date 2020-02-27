package com.baker.learning.bakerlearningthread.methods;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @desc 某个线程到达预设点时就在此等待，等所有的线程都到达时，大家再一起向下个预设点出发。如此循环反复。
 * @date 2020/2/10 0010 - 18:54
 **/
@Slf4j
public class Demo06CyclicBarrier {

    static final int COUNT = 3;

    static CyclicBarrier cb = new CyclicBarrier(COUNT, new Singer());

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < COUNT; i++) {
            new Thread(new Staff(i, cb)).start();
        }
    }

    static class Singer implements Runnable {

        @Override
        public void run() {
            log.info("为大家唱歌。。。");
        }

    }

    static class Staff implements Runnable {

        CyclicBarrier cb;
        int num;

        Staff(int num, CyclicBarrier cb) {
            this.num = num;
            this.cb = cb;
        }

        @Override
        public void run() {
            log.info("员工{}出发。。。", num);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("员工{}到达地点一。。。", num);
            try {
                cb.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("员工{}再出发。。。", num);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("员工{}到达地点二。。。", num);
            try {
                cb.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("员工{}再出发。。。", num);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("员工{}到达地点三。。。", num);
            try {
                cb.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("员工{}结束。。。", num);
        }
    }
}
