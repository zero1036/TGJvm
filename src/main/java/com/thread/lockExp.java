package com.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class lockExp {

    public static void createBusyThread() {
        Thread thread = new Thread(new Runnable() {
//            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) ;
            }
        }, "testBusyThread");
        thread.start();
    }

    //线程锁等待演示
    public static void createLockThread(final Object lock) {
        Thread thread = new Thread(new Runnable() {

//            @Override
            public void run() {
                // TODO Auto-generated method stub
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            }
        }, "testLockThread");
        thread.start();
    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
        bReader.readLine();
        createBusyThread();
        bReader.readLine();
        Object object = new Object();
        createLockThread(object);
    }

}
