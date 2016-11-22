package com.thread;

/**
 * Created by linzc on 2016/11/9.
 */
public class ThreadLocalBase {
    public static void main(String[] args) {
        ThreadLocal<Integer> tl = new ThreadLocal<Integer>();
        tl.set(10);
        System.out.println(tl.get());
    }
}


