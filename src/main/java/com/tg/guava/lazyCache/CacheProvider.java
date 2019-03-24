package com.tg.guava.lazyCache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.tg.jackson.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by Administrator on 2018/6/18.
 */
public final class CacheProvider<V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheProvider.class);

    JedisCluster jedisCluster;
    String cacheType;
    String cacheKey;
    long expireAfterWriteNanos = -1L;
    long expireAfterAccessNanos = -1L;
    long duration = -1L;
    TimeUnit unit;

    public CacheProvider() {
    }

    public CacheProvider(String cacheType, String cacheKey) {
        this.cacheType = cacheType;
        this.cacheKey = cacheKey;
    }

    public static CacheProvider<Object> newProvider(String cacheType, String cacheKey) {
        return new CacheProvider(cacheType, cacheKey);
    }

    public CacheProvider<V> expireAfterAccess(long duration, TimeUnit unit) {
        Preconditions.checkState(this.expireAfterAccessNanos == -1L, "expireAfterAccess was already set to %s ns", this.expireAfterAccessNanos);
        Preconditions.checkArgument(duration >= 0L, "duration cannot be negative: %s %s", duration, unit);
        this.expireAfterAccessNanos = unit.toNanos(duration);
        this.duration = duration;
        this.unit = unit;
        return this;
    }

    public void refresh(String targetCacheType, String targetCacheKey) {
        if (CachePool.POOL.containsKey(targetCacheType)) {
            String key = concatKey(targetCacheType, targetCacheKey);
            LoadingCache loadingCache = CachePool.POOL.get(cacheType);
            loadingCache.refresh(key);
        }
    }

    public <V1 extends V> Optional<V1> get(Callable<V1> function) {
        String key = concatKey(this.cacheType, this.cacheKey);
        LoadingCache<String, V1> loadingCache = build();
        try {
            V1 value = loadingCache.get(key, function);
            return value == null ? Optional.empty() : Optional.of(value);
        } catch (ExecutionException ex) {
            LOGGER.debug("", ex);
            return Optional.empty();
        }
    }

    public <V1 extends V> Optional<V1> get(Callable<V1> callback, TypeReference<V1> typeReference, Function<V1, CacheOptional> condition) {
        String key = concatKey(this.cacheType, this.cacheKey);
        LoadingCache<String, V1> loadingCache = build();

        //从本地缓存获得，若没有缓存，返回null，不检索资源
        try {

            V1 value = loadingCache.get(key, () -> {
                if (jedisCluster == null) {
                    return callback.call();
                }

                String remoteCacheJson = jedisCluster.get(key);
                if (Strings.isNullOrEmpty(remoteCacheJson)) {
                    V1 var1;
                    try {
                        var1 = callback.call();
                    } catch (Exception ex) {
                        if (ex instanceof RuntimeException) {
                            throw (RuntimeException) ex;
                        } else {
                            throw new RuntimeException(ex.getMessage());
                        }
                    }

                    //异步刷新缓存
                    CompletableFuture.runAsync(() -> {
                        if (validateNull(var1)) {
                            return;
                        }
                        String var3 = "";
                        if (typeReference.getType().getTypeName().equals(String.class.getTypeName())) {
                            var3 = var1.toString();
                        } else {
                            try {
                                var3 = JacksonUtils.encode(var1);
                            } catch (IOException ex) {
//                                LOGGER.error("{}对象序列化异常", cacheType.getLabel(), ex);
                                return;
                            }
                        }

                        final String var2 = var3;
                        jedisCluster.setex(key, 10, var2);
                    });

                    return validateNull(var1) ? null : var1;
                } else {
                    if (typeReference.getType().getTypeName().equals(String.class.getTypeName())) {
                        return Strings.isNullOrEmpty(remoteCacheJson) ? null : (V1) remoteCacheJson;
                    } else {
                        try {
                            V1 var2 = JacksonUtils.decode(remoteCacheJson, typeReference);
                            return validateNull(var2) ? null : var2;
                        } catch (IOException ex) {
//                            LOGGER.error("{}对象反序列化异常", cacheType.getLabel(), ex);
                            return null;
                        }
                    }
                }
            });
            return value == null ? Optional.empty() : Optional.of(value);
        } catch (ExecutionException ex) {
            LOGGER.debug("", ex);
            return Optional.empty();
        }
    }

    private String concatKey(String targetCacheType, String targetCacheKey) {
        return String.format("%s:%s", targetCacheType, targetCacheKey);
    }

    private <V1 extends V> LoadingCache<String, V1> build() {
        synchronized (CachePool.POOL) {
            if (CachePool.POOL.containsKey(cacheType)) {
                return CachePool.POOL.get(cacheType);
            } else {
                LoadingCache<String, V1> cache = CacheBuilder.newBuilder()
                        .expireAfterAccess(this.duration, this.unit)
                        .build(new CacheLoader<String, V1>() {
                            @Override
                            public V1 load(String key) throws Exception {
                                return null;
                            }
                        });

                CachePool.POOL.put(cacheType, cache);
                return cache;
            }
        }
    }

    /**
     * 验证空值
     *
     * @param var1
     * @param <T>
     * @return
     */
    private <T> boolean validateNull(T var1) {
        if (var1 == null) {
            return true;
        }
        if (var1 instanceof String
                && Strings.isNullOrEmpty(var1.toString())) {
            return true;
        }
        if (var1 instanceof Collection && ((Collection) var1).isEmpty()) {
            return true;
        }
        if (var1 instanceof Map && ((Map) var1).isEmpty()) {
            return true;
        }
        return false;
    }
}
