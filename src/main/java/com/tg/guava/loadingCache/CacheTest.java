package com.tg.guava.loadingCache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by linzc on 2017/8/15.
 */
public class CacheTest {
    /**
     * 重要loadingCache最好是单例
     *
     * @param pa
     */
    public static void main(String... pa) throws InterruptedException {
//        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
//                .expireAfterAccess(100, TimeUnit.MILLISECONDS)
//                .build(new CacheLoader<String, String>() {
//                    @Override
//                    public String load(String key) throws Exception {
//                        System.out.println("loading from cache");
//                        return "test";
//                    }
//                });
//
//        invoke(cache);
//        invoke(cache);
//        Thread.sleep(100);
//        invoke(cache);


        Cache<String, String> cache2 = CacheBuilder.newBuilder()
                .expireAfterAccess(100, TimeUnit.MILLISECONDS) //缓存项在创建后，在给定时间内没有被读/写访问，则清除。
                .build();

        invoke2(cache2);
        invoke2(cache2);
        Thread.sleep(100);
        invoke2(cache2);
    }

    private static void invoke(LoadingCache<String, String> cache) {
        try {
            String result = cache.get("key", ()->{
                System.out.println("loading from cache");
                return "test";
            });
            System.out.println(result);
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    private static void invoke2(Cache<String, String> cache) {
        try {
            String result = cache.get("key", () -> {
                System.out.println("loading from cache");
                return "test";
            });
            System.out.println(result);
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }
}
