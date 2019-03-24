package com.tg.hystrix.isolation;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.TimeUnit;

/**
 * 信号量隔离例子
 */
public class IsolationCommand extends HystrixCommand<String> {

    private final int id;
    private long start, end;

    public IsolationCommand(int id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("testCommand"))
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
        System.out.println("running normal, id=" + id);
        return "ValueFromHashMap_" + id;
    }

    @Override
    protected String getFallback() {
        System.out.println(" fallback, id=" + id);
        return "fallback:" + id;
    }

    public static void main(String[] args) throws InterruptedException {
        int count = 10;
        while (count > 0) {
            int id = count--;
            new Thread(() -> {
                try {
                    new IsolationCommand(id).execute();
                } catch (Exception ex) {
                    System.out.println("HystrixRuntimeException:" + ex.getMessage() + " id=" + id);
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(60);
    }
}
