package com.tg.thread;

/**
 * 多线程，交替输出1,2,1,2,1,2......
 * Created by linzc on 2016/11/28.
 */
public class OutputThread implements Runnable {

    private int num;
    private Object lock;

    public OutputThread(int num, Object lock) {
        super();
        this.num = num;
        this.lock = lock;
    }

    public void run() {
        try {
            while (true) {
                synchronized (lock) {
                    lock.notifyAll(); //Wakes up all threads that are waiting on this object's monitor，
                    lock.wait();      //Causes the current thread to wait until another thread invokes the notify() method or the notifyAll() method for this object.
                    System.out.println(num);
                }
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        final Object lock = new Object();

        Thread thread1 = new Thread(new OutputThread(1, lock));
        Thread thread2 = new Thread(new OutputThread(2, lock));

        thread1.start();
        thread2.start();
    }

}
