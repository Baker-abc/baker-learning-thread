package com.baker.learning.bakerlearningthread.methods;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @desc 两个线程在预设点交换变量，先到达的等待对方。
 * @date 2020/2/10 0010 - 19:18
 **/
@Slf4j
public class Demo07Exchanger {

    static Exchanger<Tool> ex = new Exchanger<>();

    public static void main(String[] args) throws Exception {
        new Thread(new Staff("大胖", new Tool("笤帚", "扫地"), ex)).start();
        new Thread(new Staff("小白", new Tool("抹布", "擦桌"), ex)).start();

    }

    static class Staff implements Runnable {

        String name;
        Tool tool;
        Exchanger<Tool> ex;

        Staff(String name, Tool tool, Exchanger<Tool> ex) {
            this.name = name;
            this.tool = tool;
            this.ex = ex;
        }

        @Override
        public void run() {
            log.info("{}拿的工具是[{}]，他开始[{}]。。。", name, tool.name, tool.work);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("{}开始交换工具。。。", name);
            try {
                tool = ex.exchange(tool);
            } catch (Exception e) {
                e.printStackTrace();
            }

            log.info("{}的工具变为[{}]，他开始[{}]。。。", name, tool.name, tool.work);
        }

    }

    static class Tool {

        String name;
        String work;

        Tool(String name, String work) {
            this.name = name;
            this.work = work;
        }

    }
}
