package com.tg.future;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiFunction;

/**
 * Created by linzc on 2017/7/25.
 */
public class CompletableFutureTest {
    private int x = 0;
    private int y = 0;

    public static void main(String[] args) throws Exception {
        CompletableFutureTest test = new CompletableFutureTest();
        test.test4();
    }

    public void test() throws Exception {
        String str = "tg";
        ExecutorService exs = Executors.newFixedThreadPool(10);
        while (x < 10) {
            CompletableFuture futureCount = CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(1000);

                    x += 1;
                    String name = Thread.currentThread().getName();
                    System.out.println("th " + name + " target y:" + y + " target x" + x);
                } catch (InterruptedException e) {
                }
            }, exs);
        }
        System.out.println("end");
        Thread.sleep(7000);
        System.out.println("result" + x);
        /**
         * 最后打印结果，x大于20，runnable会修改外部变量
         * 问题是：while(x < 10)仍然不会停止，
         */
    }

    public void test2() throws Exception {
        String str = "tg";
        ExecutorService exs = Executors.newFixedThreadPool(3);
        while (y < 10) {
            CompletableFuture futureCount = CompletableFuture.runAsync(() -> {
                try {
                    // Simulate long running task  模拟长时间运行任务
                    if (y == 2 || y == 8) {
                        Thread.sleep(1000);
                    }

                    x += 1;
                    String name = Thread.currentThread().getName();
                    System.out.println("th " + name + " target y:" + y + " target x:" + x);
                } catch (InterruptedException e) {
                }
            }, exs);
            y += 1;
        }
        System.out.println("result");
        Thread.sleep(7000);
        System.out.println(y);
    }

    /**
     * 等待两个future同时完成
     *
     * @throws Exception
     */
    public void test3() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return "zero";
        }, executor);
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return "hello";
        }, executor);

        CompletableFuture<String> reslutFuture =
                f1.thenCombine(f2, new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String t, String u) {
                        return t.concat(u);
                    }
                });

        System.out.println(reslutFuture.get());//zerohello
    }

    /**
     * 可以对CompletableFuture指定处理完成的时间，如果按时完成则通知，否则抛出超时异常并处理。
     *
     * @throws Exception
     */
    public void test4() throws Exception {
        CompletableFuture<String> responseFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("over");
            return "hello world!";
        });

        CompletableFuture<Void> result = failAfter(Duration.ofSeconds(2)).acceptEither(responseFuture, (x) -> {
            System.out.println("responseFuture is over successed! the response is " + x);
        }).exceptionally(throwable -> { //exceptionally必须在最后
            System.out.println("responseFuture is not over on time!");
            return null;
        });

        System.out.println(result.get());
    }

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static <T> CompletableFuture<T> failAfter(Duration duration) {
        final CompletableFuture<T> promise = new CompletableFuture<>();
        scheduler.schedule(() -> {
            final TimeoutException ex = new TimeoutException("Timeout after " + duration);
            return promise.completeExceptionally(ex);
        }, duration.toMillis(), TimeUnit.MILLISECONDS);
        return promise;
    }


    public void test5() throws Exception {
        CompletableFuture<String> responseFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("over");
            return "hello world!";
        });

        List<CompletableFuture<String>> list = new ArrayList<CompletableFuture<String>>();
        CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]));
    }
}
