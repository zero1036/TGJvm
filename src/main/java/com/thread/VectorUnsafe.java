package com.thread;

import java.util.*;

/**
 * Created by linzc on 2016/11/22.
 */
public class VectorUnsafe {

    private static Vector<Integer> vector = new Vector<Integer>();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            Thread removeThread = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                    }
                }
            });

            Thread getThread = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
//                        尝试加入首先判断i是否在vector size范围内，结果同样报错，
//                        if (i < vector.size()) {
//                            continue;
//                        }
                        vector.get(i);
                    }
                }
            });

            removeThread.start();
            getThread.start();

            //不要同时产生过多的线程，否则会导致操作系统假死
            while (Thread.activeCount() > 20) ;
        }
    }

}
