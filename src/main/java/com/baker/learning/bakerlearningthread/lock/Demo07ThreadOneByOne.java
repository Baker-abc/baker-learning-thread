package com.baker.learning.bakerlearningthread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description
 * @date 2020/4/26 20:27
 */
public class Demo07ThreadOneByOne {


    public static int one = 1;
    public static int two = 1;

    public static final int loop_count = 100;

    static volatile boolean flag = true;


    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Model model = new Model(1);
        new Thread(new JiNum(model)).start();
        new Thread(new OuNum(model)).start();
    }

    static class JiNum implements Runnable {

        private Model model;

        public JiNum(Model model) {
            this.model = model;
        }

        @Override
        public void run() {
            while (one < loop_count) {
                if (!flag) {
                    try {
                        lock.lock();

                        one++;
                        System.out.println(Thread.currentThread().getName() + " " + model.getNember());
                        model.setNember(model.getNember() + 1);

                        flag = true;
                    } finally {
                        lock.unlock();
                    }
                }

            }
        }
    }

    static class OuNum implements Runnable {

        private Model model;

        public OuNum(Model model) {
            this.model = model;
        }

        @Override
        public void run() {
            while (two < loop_count) {
                if (flag) {
                    try {
                        lock.lock();

                        two++;
                        System.out.println(Thread.currentThread().getName() + " " + model.getNember());
                        model.setNember(model.getNember() + 1);

                        flag = false;

                    } finally {
                        lock.unlock();
                    }
                }

            }
        }
    }

    static class Model {
        private Integer nember;

        public Model(Integer nember) {
            this.nember = nember;
        }

        public Integer getNember() {
            return nember;
        }

        public void setNember(Integer nember) {
            this.nember = nember;
        }
    }
}
