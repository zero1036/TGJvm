package com.tg.thread.threadPool;

import com.google.common.collect.Lists;
import com.tg.jedis.RedisUtils;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.RecursiveTask;


public class BitmapTask extends RecursiveTask<Optional<List<Long>>> {

    private JedisPool jedisPool;
    private long fragment = 50;
    private long start;
    private long end;
    private String key;

    public BitmapTask(JedisPool jedisPool, long start, long end, long fragmentSize, String key) {
        super();
        if (fragmentSize < 8 || fragmentSize % 8 != 0) {
            throw new IllegalArgumentException("fragment大小必须为8的倍数");
        }

        this.start = start;
        this.end = end;
        this.fragment = fragmentSize;
        this.jedisPool = jedisPool;
        this.key = key;
    }

    @Override
    protected Optional<List<Long>> compute() {
        List<Long> result = Lists.newArrayList();

        Long bitCountByteStart = start / 8;
        Long bitCountByteEnd = end / 8;

        final Long count = RedisUtils.eval(jedisPool, jedis -> jedis.bitcount(key, bitCountByteStart, bitCountByteEnd));
        if (count == null || count <= 0) {
            return Optional.empty();
        }
//        System.out.println("bstart:" + bitCountByteStart + ";bitCountByteEnd:" + bitCountByteEnd + ";count:" + count);

        if (end - start + 1 <= fragment) {

            result = RedisUtils.eval(jedisPool, jedis -> {
                List<Long> resultPart = Lists.newArrayList();
                for (long index = start; index <= end; index++) {
                    Boolean ok = jedis.getbit(key, index);
                    if (ok != null && ok) {
                        System.out.println("start:" + start + ";end:" + end + ";index:" + index);
                        resultPart.add(index);
                    }
                }
                return resultPart;
            });

//
//            for (long i = start; i <= end; i++) {
//
//                final long index = i;
//                Boolean ok = RedisUtils.eval(jedisPool, jedis -> jedis.getbit(key, index));
////                System.out.println("start:" + start + ";end:" + end + ";index:" + index + ";result" + ok+";count"+count);
//                if (ok != null && ok) {
//                    System.out.println("start:" + start + ";end:" + end + ";index:" + index);
//                    result.add(i);
//                }
//                if (count.intValue() == result.size()) {
//                    break;
//                }
//            }
            return Optional.of(result);
        } else {
            //继续裂变
//            Long distance = end - start + 1;
//            Long byteCount = (distance / 8) + 1;
//            Long byteCountHalf = (byteCount / 2) + 1;//1+1=2
//            Long startCut = start + (byteCountHalf * 8);
//            Long endCut = startCut + 1;


            Long middle = (start + end) / 2;
            BitmapTask left = new BitmapTask(jedisPool, start, middle, fragment, key);
            BitmapTask right = new BitmapTask(jedisPool, middle + 1, end, fragment, key);
            //并行执行两个“小任务”
            left.fork();
            right.fork();

            Optional<List<Long>> optional1 = left.join();

            Optional<List<Long>> optional2 = right.join();

            if (!optional1.isPresent() && !optional2.isPresent()) {
                return Optional.empty();
            } else if (!optional1.isPresent() && optional2.isPresent()) {
                return optional2;
            } else if (optional1.isPresent() && !optional2.isPresent()) {
                return optional1;
            } else {
                optional1.get().addAll(optional2.get());
                return optional1;
            }
        }
    }
}
