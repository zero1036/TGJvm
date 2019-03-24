package com.tg.thread.threadPool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * Created by linzc on 2018/11/28.
 */
public class CompletableFutureTest {

    /**
     * 证明，当ForkJoinPool执行的ForkJoinTask没有通过fork拆分任务时，没有execute或submit下都会创建新的线程
     *
     * @param args
     * @throws Exception
     */
    public static void main4(String[] args) throws Exception {

        final int factor = 3;

        /**
         * ForkJoinPool构造器需要指定并行度parallelism：默认取32767与Runtime.getRuntime().availableProcessors()的较小值
         * public ForkJoinPool() {
         *     this(Math.min(MAX_CAP, Runtime.getRuntime().availableProcessors()),
         *     defaultForkJoinWorkerThreadFactory, null, false);
         * }
         *
         * Runtime.getRuntime().availableProcessors()为CPU核心数
         *
         * 当并行度parallelism取1时，打印结果：future2会等待future1执行完成，并沿用worker1
         * Thread=ForkJoinPool-1-worker-1 target1=6
         * Thread=ForkJoinPool-1-worker-1 target2=0
         *
         * 当并行度parallelism取2时，打印结果：future2无需等待future1执行完成
         * Thread=ForkJoinPool-1-worker-1 target1=6
         * Thread=ForkJoinPool-1-worker-0 target2=0
         */
        Executor executor = new ForkJoinPool(1);

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {

            int target = factor + 3;
            System.out.println("Thread=" + Thread.currentThread().getName() + " target1=" + target);

            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException ex) {

            }
            return target;
        }, executor);

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int target = factor - 3;
            System.out.println("Thread=" + Thread.currentThread().getName() + " target2=" + target);
            return target;
        }, executor);

        System.out.println("start");

        Integer var1 = future1.join();
        Integer var2 = future2.join();

        System.out.println("result=" + (var1 + var2));

        TimeUnit.SECONDS.sleep(50);
    }

    public static void main3(String[] args) throws Exception {

        final int factor = 3;

        /**
         * 1、延续以上测试，当parallelism取2，当3个future并行，第1个长时间挂起时，第2与第3个Future会按先后顺序使用对应worker
         * 打印结果：
         * Thread=ForkJoinPool-1-worker-1 target1=6
         * Thread=ForkJoinPool-1-worker-0 target2=0
         * Thread=ForkJoinPool-1-worker-0 target3=0
         *
         * 2、调用关系
         * ForkJoinPool通过createWorker()创建ForkJoinWorkerThread
         * 线程ForkJoinWorkerThread.pool = ForkJoinPool
         * 线程ForkJoinWorkerThread.workQueue = ForkJoinPool.workQueue[].workQueue
         * ForkJoinWorkerThread通过重写Thread.run()调用
         *
         * 3、
         * ForkJoinPool持有WorkQueue[]
         */
        Executor executor = new ForkJoinPool(2);

        ThreadLocal<Integer> local = new ThreadLocal<>();
        local.set(-34);

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {

            int target = factor + 3;
            System.out.println("Thread=" + Thread.currentThread().getName() + " target1=" + target);

            local.set(99);

            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException ex) {

            }
            return target;
        }, executor);

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int target = factor - 3;
            System.out.println("Thread=" + Thread.currentThread().getName() + " target2=" + target);
            return target;
        }, executor);

        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
            int target = factor - 3;
            System.out.println("Thread=" + Thread.currentThread().getName() + " target3=" + target);

            System.out.println("ThreadLocal=" + local.get());
            return target;
        }, executor);

        System.out.println("start");

        Integer var1 = future1.join();
        Integer var2 = future2.join();
        Integer var3 = future3.join();

        System.out.println("result=" + (var1 + var2 + var3));

        TimeUnit.SECONDS.sleep(50);
    }

    static ThreadLocal<Integer> localInt = new ThreadLocal<>();

    public static void main(String[] args) throws Exception {
        /**
         * 打印结果：证明两个task共用同一worker同一线程
         * Thread=ForkJoinPool-1-worker-0 value1=null
         * Thread=ForkJoinPool-1-worker-1 value2=null
         * Thread=ForkJoinPool-1-worker-1 value3=2
         */
        Executor executor = new ForkJoinPool(2);
        localInt.set(99);

        CompletableFuture.runAsync(() -> {
            System.out.println("Thread=" + Thread.currentThread().getName() + " value1=" + localInt.get());
            localInt.set(1);
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException ex) {

            }
        }, executor);

        CompletableFuture.runAsync(() -> {
            System.out.println("Thread=" + Thread.currentThread().getName() + " value2=" + localInt.get());
            localInt.set(2);
        }, executor);

        CompletableFuture.runAsync(() -> {
            System.out.println("Thread=" + Thread.currentThread().getName() + " value3=" + localInt.get());
            localInt.set(3);
        }, executor);

        TimeUnit.SECONDS.sleep(50);
    }


}
