package com.thread;

/**
 * Created by linzc on 2016/10/19.
 */
public class VolatileTest {

    public static volatile int race = 0;

    public static void increase() {
        race++;
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
//                @Override
                public void run() {
                    for (int i1 = 0; i1 < 10000; i1++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }

        System.out.println(race);
    }


    //正确应用如下
//    volatile boolean shutdownRequested;
//
//    public void shutdown() {
//        shutdownRequested = true;
//    }
//
//    public void doWork() {
//        while (!shutdownRequested) {
//            //do stuff
//        }
//    }
}
