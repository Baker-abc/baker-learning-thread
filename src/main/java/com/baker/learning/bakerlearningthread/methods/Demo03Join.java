package com.baker.learning.bakerlearningthread.methods;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @desc join插队
 * @date 2020/2/10 0010 - 16:45
 **/
@Slf4j
public class Demo03Join {

    public static void main(String[] args) {
        Thread thread = new Thread(new Handler());
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(2);
            //插主函数的队
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("============ 3 ============");


    }

    static class Handler implements Runnable {

        @Override
        public void run() {
            log.info("=========== 1 ===========");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("=========== 2 ===========");
        }
    }
}
