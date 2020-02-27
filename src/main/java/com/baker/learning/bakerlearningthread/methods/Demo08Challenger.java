package com.baker.learning.bakerlearningthread.methods;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import static jdk.nashorn.internal.objects.NativeMath.random;

/**
 * @desc
 * @date 2020/2/10 0010 - 21:59
 **/
@Slf4j
public class Demo08Challenger {

    static final int COUNT = 6;

    public static void main(String[] args) throws Exception {
        new Thread(new Challenger("张三")).start();
        new Thread(new Challenger("李四")).start();
        new Thread(new Challenger("王五")).start();
        new Thread(new Challenger("赵六")).start();
        new Thread(new Challenger("大胖")).start();
        new Thread(new Challenger("小白")).start();

    }

    static Phaser ph = new Phaser() {

        protected boolean onAdvance(int phase, int registeredParties) {
            log.info("第({})局，剩余[{}]人", phase, registeredParties);
            return registeredParties == 0 ||
                    (phase != 0 && registeredParties == COUNT);
        }

        ;
    };

    static class Challenger implements Runnable {

        String name;
        int state;

        Challenger(String name) {
            this.name = name;
            this.state = 0;
        }

        @Override
        public void run() {
            log.info("[{}]开始挑战。。。", name);
            ph.register();
            int phase = 0;
            int h;
            while (!ph.isTerminated() && phase < 100) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (state == 0) {
                    if (Decide.goon()) {
                        h = ph.arriveAndAwaitAdvance();
                        if (h < 0)
                            log.info("No{}.[{}]继续，但已胜利。。。", phase, name);
                        else
                            log.info("No{}.[{}]继续at({})。。。", phase, name, h);
                    } else {
                        state = -1;
                        h = ph.arriveAndDeregister();
                        log.info("No{}.[{}]退出at({})。。。", phase, name, h);
                    }
                } else {
                    if (Decide.revive()) {
                        state = 0;
                        h = ph.register();
                        if (h < 0)
                            log.info("No{}.[{}]复活，但已失败。。。", phase, name);
                        else
                            log.info("No{}.[{}]复活at({})。。。", phase, name, h);
                    } else {
                        log.info("No{}.[{}]没有复活。。。", phase, name);
                    }
                }
                phase++;
            }
            if (state == 0) {
                ph.arriveAndDeregister();
            }
            log.info("[{}]结束。。。", name);
        }

    }

    static class Decide {

        static boolean goon() {
            return random(9) > 4;
        }

        static boolean revive() {
            return random(9) < 5;
        }
    }
}
