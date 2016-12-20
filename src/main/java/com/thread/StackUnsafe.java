package com.thread;

import java.util.*;

/**
 * Created by linzc on 2016/11/22.
 */
public class StackUnsafe {

    private static Stack<Integer> stack = new Stack<Integer>();

    public static void main(String[] args) {

        for (int i = 0; i < 10000; i++) {
            stack.push(i);
        }

        while (true) {
            Thread tPeek = new Thread(new Runnable() {
                public void run() {
                    while (stack.size() > 0) {
                        Integer a = stack.peek();  //取出栈顶元素，不执行删除
                    }
                }
            });

            Thread tPod = new Thread(new Runnable() {
                public void run() {
                    while (stack.size() > 0) {
                        Integer a = stack.pop();  //取出栈顶元素，并将该元素从栈中删除
                    }
                }
            });

            tPeek.start();
            tPod.start();

            while (Thread.activeCount() > 20) ;
        }
    }
}
