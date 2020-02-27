package com.baker.learning.bakerlearningthread.methods;

import java.util.concurrent.TimeUnit;

/**
 * @desc wait/notify配合使用 休眠/唤醒线程
 * @date 2020/2/10 0010 - 15:46
 **/
public class Demo02WaitAndNotify {

    public static void main(String[] args) throws InterruptedException {
        Handler handler = new Handler();
        new Thread(handler).start();
        new Thread(handler).start();

        TimeUnit.SECONDS.sleep(5);
        System.out.println("=========== 线程唤醒 " + Thread.currentThread().getId() + "===========");
        handler.notifyThis();

        //唤醒handler实例的全部线程
//        synchronized (handler) {
//            handler.notifyAll();
//        }
    }

    static class Handler implements Runnable {

        void waitThis() {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        void notifyThis() {
            synchronized (this) {
                this.notify();
            }
        }

        @Override
        public void run() {
            System.out.println("============= 1 " + Thread.currentThread().getId() + "===========");
            System.out.println("=========== 线程休眠 " + Thread.currentThread().getId() + "===========");

            waitThis();

            System.out.println("============= 2 " + Thread.currentThread().getId() + "===========");
        }
    }
}
