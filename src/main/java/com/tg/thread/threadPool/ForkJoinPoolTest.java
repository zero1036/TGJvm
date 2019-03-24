package com.tg.thread.threadPool;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.tg.jedis.RedisUtils;
import org.junit.Test;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by linzc on 2018/11/28.
 */
public class ForkJoinPoolTest {
    public static void main2(String[] args) throws Exception {
        PrintTask task = new PrintTask(0, 300);
        //创建实例，并执行分割任务
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(task);
        //线程阻塞，等待所有任务完成
        pool.awaitTermination(2, TimeUnit.SECONDS);
        pool.shutdown();
    }


//    public static void main3(String[] args) throws Exception {

    @Test
    public void test2() throws Exception {
        JedisPoolConfig config = new JedisPoolConfig();
        //池对象，即jedisPool最大数量
        config.setMaxTotal(3);
        config.setMaxIdle(3);
        //从池中获取池对象，缺少时等待，等待超过毫秒数放弃等待
        config.setMaxWaitMillis(1000);

        final String key = "MissionsCycle:5b5ea81ac270ed1374df77a1:a270b755-b725-474a-a1d0-a81d72f92d5e:0:0:1";
        final long start = 0;
        final long end = 32768;

//        final String key = "testKey";
//        final long start = 0;
//        final long end = 31;
        final long factor = 8;
        final int sleepSecond = 20;
        JedisPool jedisPool = new JedisPool(config, "192.168.107.16", 6379, 3000, null, 4);

        Stopwatch duration = Stopwatch.createStarted();
        List<Long> list = Lists.newArrayList();
        for (long i = start; i <= end; i++) {
            final long index = i;
            Boolean eval = RedisUtils.eval(jedisPool, jedis -> jedis.getbit(key, index));
            if (eval != null && eval) {
                System.out.println("index:" + index);
                list.add(index);
            }
        }
        duration.stop();
        long elapsed = duration.elapsed(TimeUnit.SECONDS);
        System.out.println("执行时长" + elapsed);
        System.out.println("长度" + list.size());

        TimeUnit.MINUTES.sleep(10);
    }

//    public static void main(String[] args) throws Exception {

    @Test
    public void test3() throws Exception {
        JedisPoolConfig config = new JedisPoolConfig();
        //池对象，即jedisPool最大数量
        config.setMaxTotal(3);
        config.setMaxIdle(3);
        //从池中获取池对象，缺少时等待，等待超过毫秒数放弃等待
        config.setMaxWaitMillis(100000);

        final String key = "MissionsCycle:5b5ea81ac270ed1374df77a1:a270b755-b725-474a-a1d0-a81d72f92d5e:0:0:1";
        final long start = 0;
        final long end = 32768 - 1;

//        final String key = "testKey";
//        final long start = 0;
//        final long end = 31;
        final long factor = 8;
        final int sleepSecond = 20;
        JedisPool jedisPool = new JedisPool(config, "192.168.107.16", 6379, 3000, null, 4);

        Stopwatch duration = Stopwatch.createStarted();

        BitmapTask task = new BitmapTask(jedisPool, start, end, factor, key);

        //创建实例，并执行分割任务
        ForkJoinPool pool = new ForkJoinPool(8);
        Future<Optional<List<Long>>> future = pool.submit(task);
        Optional<List<Long>> list = future.get();

        duration.stop();
        long elapsed = duration.elapsed(TimeUnit.SECONDS);
        System.out.println("执行时长" + elapsed);
        System.out.println("长度" + (list.isPresent() ? list.get().size() : 0));


//        System.out.println("result=" + (list.isPresent() ? list.get() : Lists.newArrayList()));
        //线程阻塞，等待所有任务完成
//        pool.awaitTermination(sleepSecond, TimeUnit.SECONDS);


        pool.shutdown();
    }

}
