package com.tg.thread;

import java.util.LinkedList;

/**
 * Created by linzc on 2016/11/28.
 */
public class LinkedListUnsafe {

    private static LinkedList<Integer> list = new LinkedList<Integer>();

    public static class ListAddObj implements Runnable {

        private int start = 0;
        private int end = 0;
        private Object pLock;

        public ListAddObj(int s, int e, Object lock) {
            this.start = s;
            this.end = e;
            this.pLock = lock;
        }

        public void run() {
            for (int i = start; i <= end; i++) {
                list.addFirst(i);
            }
            synchronized (pLock) {
                pLock.notifyAll();
            }
        }
    }


    /*
    main
     */
    public static void main(String[] args) {
        final Object plock = new Object();

        Thread thread1 = new Thread(new ListAddObj(1, 1000, plock));
        Thread thread2 = new Thread(new ListAddObj(1001, 2000, plock));
        Thread thread3 = new Thread(new ListAddObj(2001, 3000, plock));

        thread1.start();
        thread2.start();
        thread3.start();

        synchronized (plock) {
            try {
                plock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(list.size());
        }
    }

}
