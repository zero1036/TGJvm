package com.tg.guava.loadingCache;


import com.google.common.base.Preconditions;
import com.google.common.cache.*;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.util.concurrent.*;

public class LoadingCacheTest {
    private volatile static int value = 1;

    /**
     * 测试recordStats
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testRecordStats() throws InterruptedException, ExecutionException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .refreshAfterWrite(200, TimeUnit.MILLISECONDS)
                .expireAfterAccess(1000, TimeUnit.MILLISECONDS)
                .recordStats()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return "get from CacheLoader";
                    }
                });

        String value = cache.get("key");
        System.out.println(value);
        System.out.println("stats:" + cache.stats());

        value = cache.get("key");
        System.out.println(value);
        System.out.println("stats:" + cache.stats());
    }


    /**
     * 测试getIfPresent方法
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testGetIfPresent() throws InterruptedException, ExecutionException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .refreshAfterWrite(200, TimeUnit.MILLISECONDS)
                .expireAfterAccess(1000, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return "get from CacheLoader";
                    }
                });

        Thread.sleep(3000);

        //输出：null，getIfPresent
        String value = cache.getIfPresent("key");
        System.out.println(value);

        //输出：get from CacheLoader
        value = cache.get("key");
        System.out.println(value);
    }

    /**
     * 测试cacheLoader的优先级
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testCacheLoader() throws InterruptedException, ExecutionException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .refreshAfterWrite(200, TimeUnit.MILLISECONDS)
                .expireAfterAccess(1000, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("refreshing CacheLoader");
                        return "get from CacheLoader";
                    }
                });

        //输出：get from CacheLoader，首次没有命中缓存，从cacheLoader中获取
        String value = cache.get("key");
        System.out.println(value);
        Thread.sleep(100);

        //输出：get from CacheLoader，第二次命中缓存，没达到时间触发刷新，从cacheLoader中获取
        value = cache.get("key");
        System.out.println(value);
        Thread.sleep(200);

        //输出：get from getFunction1，第三次命中缓存，达到时间触发刷新，从function1中获取
        value = cache.get("key", () -> {
            System.out.println("refreshing getFunction1");
            return "get from getFunction1";
        });
        System.out.println(value);

        Thread.sleep(3000);

        //输出：get from getFunction2，第四次没有命中缓存，从function2中获取
        value = cache.get("key", () -> {
            System.out.println("refreshing getFunction2");
            return "get from getFunction2";
        });
        System.out.println(value);

        //结论：如果get方法带Callable，Callable的cacheLoader优先级高于loadingCache初始化创建中的cacheLoader
//        注意loadingCache的get方法，会创建新的cacheLoader
//        public V get(K key, final Callable<? extends V> valueLoader) throws ExecutionException {
//            Preconditions.checkNotNull(valueLoader);
//            return this.localCache.get(key, new CacheLoader() {
//                public V load(Object key) throws Exception {
//                    return valueLoader.call();
//                }
//            });
//        }
    }

    /**
     * 测试CacheLoaderReturnNull
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testCacheLoaderReturnNull() throws InterruptedException, ExecutionException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .refreshAfterWrite(1000, TimeUnit.MILLISECONDS)
                .expireAfterAccess(1000, TimeUnit.MILLISECONDS)

                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("refreshing CacheLoader");
                        return null;
                    }
                });

        try {
            String value = cache.get("key");
            System.out.println(value);
        } catch (CacheLoader.InvalidCacheLoadException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testLoadingCacheExpireAfterAccess() throws InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                //缓存项在创建后，在给定时间内没有被读/写访问，则清除。
                .expireAfterAccess(100, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("loading from CacheLoader datasource ");
                        return "target value";
                    }
                });

        getAndReload(cache);
        getAndReload(cache);
        Thread.sleep(200);
        getAndReload(cache);
    }

    /**
     * 缓存项在创建后，在给定时间内没有被读/写访问，则清除，理论上，只要给定时间内一直有访问，就不会过期
     *
     * @throws InterruptedException
     */
    @Test
    public void testLoadingCacheExpireAfterAccess2() throws InterruptedException, ExecutionException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("thread-%d")
                .build();
        ExecutorService executorService = Executors.newCachedThreadPool(threadFactory);

        int expireAfterAccess = 300;
        int refreshAfterWrite = 200;
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                //缓存项在创建后，在给定时间内没有被读/写访问，则清除。
                .expireAfterAccess(expireAfterAccess, TimeUnit.MILLISECONDS)
                .refreshAfterWrite(refreshAfterWrite, TimeUnit.MILLISECONDS)
                .recordStats()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("loading from CacheLoader datasource ");
                        return "target value";
                    }
                });


        Future submit = executorService
                .submit(() -> {
                    while (true) {
                        String value = cache.get("key");
                        System.out.println(value);
                        System.out.println(cache.stats());

                        try {
                            //线程挂起时长超过300毫秒，缓存会过期，从cacheLoader中加载
                            //反之，若少于300毫秒，缓存永远不会过期
                            TimeUnit.MILLISECONDS.sleep(50);
                        } catch (InterruptedException e1) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
        Thread.sleep(1000);
        submit.cancel(true);

        Thread.sleep(5000);

        System.out.println("last:" + cache.stats());
        String value = cache.get("key");
        System.out.println("last:" + value);
    }

    @Test
    public void testMultiThreadAndReload() throws InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                //缓存项在创建后，在给定时间内没有被读/写访问，则清除。
                .expireAfterAccess(100, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("loading from CacheLoader datasource ");
                        Thread.sleep(5000);
                        return "target value";
                    }
                });

        //并发装载资源，后续线程自动挂起等待
        Thread thread1 = new Thread(() -> getAndReload(cache));
        Thread thread2 = new Thread(() -> getAndReload(cache));
        Thread thread3 = new Thread(() -> getAndReload(cache));

        thread1.start();
        thread2.start();
        thread3.start();
        Thread.sleep(8000);
    }

    /**
     * 测试定时刷新，如果只设置定时刷新，缓存是否不会清除？
     *
     * @throws InterruptedException
     */
    @Test
    public void testRefreshAfterWrite() throws InterruptedException, ExecutionException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                //通过定时刷新可以让缓存项保持可用，但请注意：缓存项只有在被检索时才会真正刷新，
                //即只有刷新间隔时间到了你再去get(key)才会重新去执行Loading否则就算刷新间隔时间到了也不会执行loading操作。
                //因此，如果你在缓存上同时声明expireAfterWrite和refreshAfterWrite，缓存并不会因为刷新盲目地定时重置，
                //如果缓存项没有被检索，那刷新就不会真的发生，缓存项在过期时间后也变得可以回收。
                .refreshAfterWrite(300, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        value++;
                        System.out.println("loading from CacheLoader datasource ");
                        return "target value " + value;
                    }
                });


        //get与getIfPresent区别：
        // get：缓存中有对应的值则返回，没有则使用CacheLoader load方法
        // getIfPresent:缓存中有对应的值则返回，没有则返回NULL

        //首次get，从loader中加载data
        String result = cache.get("key");
        System.out.println(Thread.currentThread().getName() + ":get from cache:" + result);

        //缓存数量为1个
        System.out.println(Thread.currentThread().getName() + ":当前缓存数量:" + cache.size());
        Thread.sleep(1000);

        //1秒后查询缓存数量依然为1个
        System.out.println(Thread.currentThread().getName() + ":当前缓存数量:" + cache.size());

        //1秒后，首先触发refresh机制，加载新缓存
        result = cache.getIfPresent("key");
        System.out.println(Thread.currentThread().getName() + ":get from cache:" + result);
        Thread.sleep(8000);

// print
//        loading from CacheLoader datasource
//        main:get from cache:target value 2
//        main:当前缓存数量:1
//        main:当前缓存数量:1
//        loading from CacheLoader datasource
//        main:get from cache:target value 3
    }

    /**
     * 测试loadingCache refresh机制
     *
     * @throws InterruptedException
     */
    @Test
    public void testMultiThreadAndReloadAsync() throws InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .refreshAfterWrite(100, TimeUnit.MILLISECONDS)
                .removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> removalNotification) {
                        System.out.println(Thread.currentThread().getName() + "-remove key:" + removalNotification.getKey());
                        System.out.println(Thread.currentThread().getName() + "-remove value:" + removalNotification.getValue());
                    }
                })
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws InterruptedException {
                        System.out.println(Thread.currentThread().getName() + "start loading");
                        value++;
                        String output = String.valueOf(value);
                        Thread.sleep(1000L);
                        System.out.println(Thread.currentThread().getName() + "load from db2:" + output);
                        return output;
                    }
                });

        //此外需要注意一个点，这里的定时并不是真正意义上的定时。Guava cache的刷新需要依靠用户请求线程，让该线程去进行load方法的调用，所以如果一直没有用户尝试获取该缓存值，则该缓存也并不会刷新。
        for (int i = 0; i < 5; i++) {
            Thread thread3 = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));

                        getAndReload(cache);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            thread3.start();
        }

        Thread.sleep(800000L);
    }

    /**
     * 测试loadingCache refresh机制，通过get方法的callable回调
     *
     * @throws InterruptedException
     */
    @Test
    public void testMultiThreadAndReloadAsyncAndGetFunction() throws InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .refreshAfterWrite(100, TimeUnit.MILLISECONDS)
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> removalNotification) {
                        System.out.println(Thread.currentThread().getName() + "-remove key:" + removalNotification.getKey());
                        System.out.println(Thread.currentThread().getName() + "-remove value:" + removalNotification.getValue());
                    }
                })
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws InterruptedException {
                        System.out.println(Thread.currentThread().getName() + "start loading");
                        value++;
                        String output = String.valueOf(value);
                        Thread.sleep(1000L);
                        System.out.println(Thread.currentThread().getName() + "load from db2:" + output);
                        return output;
                    }
                });

        //此外需要注意一个点，这里的定时并不是真正意义上的定时。Guava cache的刷新需要依靠用户请求线程，让该线程去进行load方法的调用，所以如果一直没有用户尝试获取该缓存值，则该缓存也并不会刷新。
        for (int i = 0; i < 5; i++) {
            Thread thread3 = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));

                        try {
                            String result = cache.get("key", () -> {
                                System.out.println(Thread.currentThread().getName() + "start loading from get()");
                                value++;
                                String output = String.valueOf(value);
                                Thread.sleep(1000L);
                                System.out.println(Thread.currentThread().getName() + "load from  get() db2:" + output);
                                return output;
                            });
                            CacheStats cacheStats = cache.stats();

                            System.out.println(Thread.currentThread().getName() + ":get from cache:" + result);
                            System.out.println(Thread.currentThread().getName() + ":cache info:" + cacheStats.toString());

                        } catch (ExecutionException ex) {
                            ex.printStackTrace();
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            thread3.start();
        }

        Thread.sleep(800000L);
    }

    private void getAndReload(LoadingCache<String, String> cache) {
        try {
            String result = cache.get("key");
            System.out.println(Thread.currentThread().getName() + ":get from cache:" + result);

        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }
}
