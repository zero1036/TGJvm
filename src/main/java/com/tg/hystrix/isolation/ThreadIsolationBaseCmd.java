package com.tg.hystrix.isolation;

import com.netflix.hystrix.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by linzc on 2019/3/8.
 */
public class ThreadIsolationBaseCmd extends HystrixCommand<String> {

    public static class ThreadIsolation1Cmd extends ThreadIsolationBaseCmd {
        private static final String commandKey = "cmd1";
        private static final String groupKey = "grp";

        public ThreadIsolation1Cmd(int id) {
            super(id, commandKey, groupKey);
        }
    }

    public static class ThreadIsolation2Cmd extends ThreadIsolationBaseCmd {
        private static final String commandKey = "cmd2";
        private static final String groupKey = "grp";

        public ThreadIsolation2Cmd(int id) {
            super(id, commandKey, groupKey);
        }
    }

    private final int id;
    private final String commandKey;
    private final String groupKey;

    public ThreadIsolationBaseCmd(int id, String cKey, String gKey) {

        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(cKey))
                .andCommandKey(HystrixCommandKey.Factory.asKey(gKey))
//                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(gKey))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        //线程池大小，默认为10，建议值:请求高峰时99.5%的平均响应时间 + 向上预留一些即可
                        .withCoreSize(2)
                        //配置线程值等待队列长度,默认值:-1 建议值:-1表示不等待直接拒绝,测试表明线程池使用直接决绝策略+ 合适大小的非回缩线程池效率最高.所以不建议修改此值。 当使用非回缩线程池时，queueSizeRejectionThreshold,keepAliveTimeMinutes 参数无效
                        .withMaxQueueSize(-1)
                        //设置线程池的最大大小，只有在设置allowMaximumSizeToDivergeFromCoreSize的时候才能生效，默认是10个线程
                        .withMaximumSize(10)
                        //设置保持存活的时间，单位是分钟，默认是1。如果设置allowMaximumSizeToDivergeFromCoreSize为true，那么coreSize就不等于maxSize，此时线程池大小是可以动态调整的，可以获取新的线程，也可以释放一些线程，如果coreSize < maxSize，那么这个参数就设置了一个线程多长时间空闲之后，就会被释放掉
                        .withKeepAliveTimeMinutes(1)
                        //允许线程池大小自动动态调整，设置为true之后，maxSize就生效了，此时如果一开始是coreSize个线程，随着并发量上来，那么就会自动获取新的线程，但是如果线程在keepAliveTimeMinutes内空闲，就会被自动释放掉，默认是false
                        .withAllowMaximumSizeToDivergeFromCoreSize(false)
                )
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withCircuitBreakerEnabled(false)
                        .withCircuitBreakerRequestVolumeThreshold(30)
                        .withCircuitBreakerSleepWindowInMilliseconds(12000)
                        //SEMAPHORE 信号量；THREAD ：线程
                        //设置使用线程池隔离策略
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                        //设置超时时间
                        .withExecutionTimeoutInMilliseconds(300)));
        this.commandKey = cKey;
        this.groupKey = gKey;
        this.id = id;

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
                        new ThreadIsolation1Cmd(id).execute();
                    } else {
                        new ThreadIsolation2Cmd(id).execute();
                    }
                } catch (Exception ex) {
                    System.out.println("HystrixRuntimeException:" + ex.getMessage() + " id=" + id);
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(60);
    }
}
