package com.baker.learning.bakerlearningthread.methods;

import java.util.concurrent.TimeUnit;

/**
 * @desc 线程在预设的地点检测flag，来决定是否停止
 * @date 2020/2/10 0010 - 14:42
 **/
public class Demo01Stop {

    public static void main(String[] args) {
        Handler handler = new Handler();
        new Thread(handler).start();
        handler.isStop = true;
    }

    static class Handler implements Runnable {

        volatile boolean isStop = false;

        @Override
        public void run() {
            try {
                System.out.println("================ 1 ==============");
                TimeUnit.SECONDS.sleep(3);

                if (isStop) {
                    System.out.println("============ stop ================");
                    return;
                }
                System.out.println("================ 2 ==============");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
