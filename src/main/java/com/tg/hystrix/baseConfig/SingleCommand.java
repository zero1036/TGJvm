package com.tg.hystrix.baseConfig;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * Created by linzc on 2019/3/5.
 */
public class SingleCommand extends HystrixCommand<String> {

    public SingleCommand() {
        //groupKey
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                //CommandKey
                .andCommandKey(HystrixCommandKey.Factory.asKey("testCommand"))
                // since we're doing an in-memory cache lookup we choose SEMAPHORE isolation
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        /**
                         * 熔断器相关
                         */
                        //开启熔断器
                        .withCircuitBreakerEnabled(true)
                        //熔断器强制打开
                        .withCircuitBreakerForceOpen(true)
                        //熔断器强制关闭
                        .withCircuitBreakerForceClosed(false)
                        //默认值20.意思是至少有20个请求才进行errorThresholdPercentage错误百分比计算。
                        // 比如一段时间（10s）内有19个请求全部失败了。错误百分比是100%，但熔断器不会打开，因为requestVolumeThreshold的值是20.
                        // 这个参数非常重要，熔断器是否打开首先要满足这个条件
                        .withCircuitBreakerRequestVolumeThreshold(20)
                        //设定错误百分比，默认值50%，例如一段时间（10s）内有100个请求，其中有55个超时或者异常返回了，
                        // 那么这段时间内的错误百分比是55%，大于了默认值50%，这种情况下触发熔断器-打开。
                        .withCircuitBreakerErrorThresholdPercentage(50)
                        //半开试探休眠时间，默认值5000ms。当熔断器开启一段时间之后比如5000ms，会尝试放过去一部分流量进行试探，确定依赖服务是否恢复。
                        //如果试探请求成功，熔断器关闭；如果失败，熔断器再次打开，继续等待下一个休眠窗口过去后再重试
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)


                        /**
                         * 隔离策略相关
                         */
                        //设置使用信号量隔离策略: SEMAPHORE 信号量；THREAD ：线程
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        //设置信号量隔离时的最大并发请求数
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(3)
                        //设置fallback的最大并发数
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(5)
                        //设置超时时间
                        .withExecutionTimeoutInMilliseconds(300)));
    }

    @Override
    protected String run() throws Exception {
        return null;
    }

    @Override
    protected String getFallback() {
        return super.getFallback();
    }
}
