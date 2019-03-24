package com.tg.hystrix.isolation;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.TimeUnit;

/**
 * Created by linzc on 2019/3/8.
 */
public class SemaphoreIsolation2Command extends HystrixCommand<String> {

    private final int id;
    private long start, end;
    private static final String commandKey = "cmd2";

    public SemaphoreIsolation2Command(int id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                // since we're doing an in-memory cache lookup we choose SEMAPHORE isolation
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        //SEMAPHORE 信号量；THREAD ：线程
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE) //设置使用信号量隔离策略
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(3)  //设置信号量隔离时的最大并发请求数
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(5)     //设置fallback的最大并发数
                        .withExecutionTimeoutInMilliseconds(300)));   //设置超时时间

        this.id = id;
        this.start = System.currentTimeMillis();
    }

    @Override
    protected String run() throws Exception {
        TimeUnit.MILLISECONDS.sleep(id * 30);
        System.out.println(commandKey + " running normal, id=" + id);
        return "ValueFromHashMap_" + id;
    }

    @Override
    protected String getFallback() {
        System.out.println(commandKey + " fallback, id=" + id);
        return "fallback:" + id;
    }

    public static void main(String[] args) throws InterruptedException {
        int count = 20;
        while (count > 0) {
            int id = count--;
            new Thread(() -> {
                try {
                    if (id % 2 == 0) {
                        new SemaphoreIsolation1Command(id).execute();
                    } else {
                        new SemaphoreIsolation2Command(id).execute();
                    }
                } catch (Exception ex) {
                    System.out.println("HystrixRuntimeException:" + ex.getMessage() + " id=" + id);
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(60);
    }
}
