package com.tg.guava.lazyCache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/6/18.
 */
public class CacheTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheTest.class);


    @Test
    public void test1() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(90000, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("loading from cache");
                        return "test";
                    }
                });
    }

    @Test
    public void test2() {
        Optional<Integer> optional = CacheProvider.newProvider("cacheType", "cacheKey")
                .expireAfterAccess(900, TimeUnit.SECONDS)
                .get(() -> {
                    return 199;
                }, new TypeReference<Integer>() {
                }, (v) -> v.equals(199) ? new CacheOptional() : null);

        System.out.println("output:" + (optional.isPresent() ? optional.get().toString() : "null"));

        optional = CacheProvider.newProvider("cacheType", "cacheKey")
                .expireAfterAccess(900, TimeUnit.SECONDS)
                .get(() -> {
                    return 199;
                }, new TypeReference<Integer>() {
                }, (v) -> v.equals(199) ? new CacheOptional() : null);

        System.out.println("output:" + (optional.isPresent() ? optional.get().toString() : "null"));
    }
}
