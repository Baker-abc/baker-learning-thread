package com.baker.learning.bakerlearningthread.executor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @Description executorService.invokeAny
 * 线程池中任意一个线程结束就结束等待
 * @Author
 * @Date 2020/2/27 23:13
 **/
@Slf4j
public class Demo01ExecutorServiceInvokeAny {

    public static void main(String[] args) throws IOException {
        test();
    }

    /**
     * 监控一个磁盘目录的变化，在一定时间内，创建文件数量达到要求值，则结束线程等待
     */
    static void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            String listenFolder = "E:/test/";

            Set<Callable<String>> callables = new HashSet<Callable<String>>();
            callables.add(new Callable<String>() {
                public String call() throws Exception {
                    TimeUnit.SECONDS.sleep(5);
                    return "time out";
                }
            });
            callables.add(new Callable<String>() {
                public String call() throws Exception {
                    WatchService ws = null;
                    try {
                        ws = FileSystems.getDefault().newWatchService();
                        log.info("磁盘监听开启中。。。。。");
                        Path p = Paths.get(listenFolder);
                        p.register(ws, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

                        int count = 0;
                        while (count < 2) {
                            WatchKey watchKey = ws.take();
                            List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                            for (WatchEvent<?> event : watchEvents) {
                                count = count + 1;
                                log.info("[" + listenFolder + event.context() + "]文件发生了[" + event.kind() + "]事件" + event.count() + "; count=" + count);
                            }
                            watchKey.reset();
                        }
                    } catch (Exception e) {
                        log.error("磁盘监控异常：", e);
                        return "disk error";
                    } finally {
                        try {
                            ws.close();
                        } catch (IOException e) {
                            throw e;
                        }
                    }
                    log.info("本次磁盘监听结束。。。。。");
                    return "disk success";
                }
            });

            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    FileUtils.write(new File(listenFolder + "test1.txt"), "1111", "utf-8");
                    FileUtils.write(new File(listenFolder + "test2.txt"), "2222", "utf-8");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();


            String result = executorService.invokeAny(callables, 10, TimeUnit.SECONDS);
            log.info("result = {}", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            //平滑的关闭ExecutorService，当此方法被调用时，ExecutorService停止接收新的任务并且等待已经提交的任务（包含提交正在执行和提交未执行）执行完成。
            // 当所有提交任务执行完毕，线程池即被关闭
            executorService.shutdown();
            try {
                // 用于设定超时时间及单位。当等待超过设定时间时，会监测ExecutorService是否已经关闭，若关闭则返回true，否则返回false
                if (!executorService.awaitTermination(3, TimeUnit.SECONDS)) {
                    //试图停止当前正执行的task，并返回尚未执行的task的list
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                log.error("线程等待异常：", e);
            }
        }

    }
}
