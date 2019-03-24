package com.tg.guava.lazyCache;

import com.google.common.cache.LoadingCache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/18.
 */
public final class CachePool {
    public static final Map<String, LoadingCache> POOL = new HashMap<>();
}
