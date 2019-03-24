package com.tg.thread.threadPool;

import java.util.concurrent.RecursiveAction;

/**
 * Created by linzc on 2018/11/28.
 */
public class PrintTask extends RecursiveAction {
    private static final int THRESHOLD = 50; //最多只能打印50个数
    private int start;
    private int end;


    public PrintTask(int start, int end) {
        super();
        this.start = start;
        this.end = end;
    }


    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() {

        if (end - start < THRESHOLD) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + "的i值：" + i);
            }
        } else {
            int middle = (start + end) / 2;
            PrintTask left = new PrintTask(start, middle);
            PrintTask right = new PrintTask(middle, end);
            //并行执行两个“小任务”
            left.fork();
            right.fork();
        }

    }
}
