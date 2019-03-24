package com.tg.guava.loadingCache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CacheTest {

    @Test
    public void testUsual() throws InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                //缓存项在创建后，在给定时间内没有被读/写访问，则清除。
                .expireAfterAccess(100, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("loading from CacheLoader datasource ");
                        Thread.sleep(500);
                        return "test";
                    }
                });

        Thread thread1 = new Thread(() -> {
            getAndReload(cache);
        });
        Thread thread2 = new Thread(() -> {
            getAndReload(cache);
        });

        thread1.start();
        thread2.start();
        Thread.sleep(2000);
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
                        return "test";
                    }
                });

        getAndReload(cache);
        getAndReload(cache);
        Thread.sleep(200);
        getAndReload(cache);
    }

    private void getAndReload(Cache<String, String> cache) {
        try {
            String result = cache.get("key", () -> {
                System.out.println("loading from datasource");
                return "test";
            });
            System.out.println(Thread.currentThread().getName() + ":" + result);
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }


    private void getOnly(LoadingCache<String, String> cache) {
        try {
            String result = cache.get("key");
            System.out.println(result);
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }
}
