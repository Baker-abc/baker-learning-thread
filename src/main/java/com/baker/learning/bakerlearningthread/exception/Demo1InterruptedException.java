package com.baker.learning.bakerlearningthread.exception;

import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @description
 * @date 2020/3/5 10:49
 */
@Log4j2
public class Demo1InterruptedException {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        callables.add(new Callable<String>() {

            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(5000);
                    return "success1";
                } catch (InterruptedException e) {
                    log.error("等待异常1：", e);
                }
                return "error1";
            }
        });

        callables.add(new Callable<String>() {

            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(10000);
                    return "success2";
                } catch (InterruptedException e) {
                    log.error("等待异常2：", e);
                    log.info(Thread.currentThread().isInterrupted());
                    log.info(Thread.currentThread().isAlive());
                    Thread.currentThread().interrupt();
                    log.info(Thread.currentThread().isInterrupted());
                    log.info(Thread.currentThread().isAlive());
                }
                return "error2";
            }
        });

        try {
            String result = executorService.invokeAny(callables, 1, TimeUnit.MINUTES);
            System.out.println(result);
        } catch (InterruptedException e) {
            log.error("等待异常3：", e);
        } catch (ExecutionException e) {
            log.error("等待异常4：", e);
        } catch (TimeoutException e) {
            log.error("等待异常5：", e);
        }finally {

            log.info(Thread.currentThread().isInterrupted());
            log.info(Thread.currentThread().isAlive());

            executorService.shutdown();
            log.info(Thread.currentThread().isInterrupted());
            log.info(Thread.currentThread().isAlive());

            try {
                if (!executorService.awaitTermination(3, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                log.error("执行线程等待异常：", e);
            }
            try {
                log.info("【vrs生成pak】任务结束！杀掉UE相关进程！");
            } catch (Exception e) {
                log.info("任务结束！杀掉UE相关进程异常：", e);
            }


            log.info(Thread.currentThread().isInterrupted());
            log.info(Thread.currentThread().isAlive());
        }

    }
}
