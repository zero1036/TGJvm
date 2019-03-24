package com.tg.jedis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by linzc on 2017/3/2.
 */
public class jedisBase {
    private Jedis jedis;

//    private JedisPool

    @Before
    public void setup() {
        //连接redis服务器，192.168.0.100:6379
        jedis = new Jedis("192.168.105.84", 6379);
        jedis.select(0);
//        //权限认证
//        jedis.auth("admin");
    }

    /**
     * redis存储字符串
     */
    @Test
    public void testString() {
        //-----添加数据----------
        jedis.set("name", "xinxin");//向key-->name中放入了value-->xinxin
        System.out.println(jedis.get("name"));//执行结果：xinxin

        jedis.append("name", " is my lover"); //拼接
        System.out.println(jedis.get("name"));

        jedis.del("name");  //删除某个键
        System.out.println(jedis.get("name"));
        //设置多个键值对
        jedis.mset("name", "liuling", "age", "23", "qq", "476777XXX");
        jedis.incr("age"); //进行加1操作
        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));

    }

    @Test
    public void testLua() {
        String script = "redis.call('set',KEYS[1],KEYS[2]);" +
                "local r=redis.call('get',KEYS[1]);" +
                "return r;";
        Object o = jedis.eval(script, 2, "abc", "3");
        System.out.println(o.toString());
    }

    /**
     * redis操作Map
     */
    @Test
    public void testMap() {
        //-----添加数据----------
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "xinxin");
        map.put("age", "22");
        map.put("qq", "123456");
        jedis.hmset("user", map);
        //取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
        //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
        List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
        System.out.println(rsmap);

        //删除map中的某个键值
        jedis.hdel("user", "age");
        System.out.println(jedis.hmget("user", "age")); //因为删除了，所以返回的是null
        System.out.println(jedis.hlen("user")); //返回key为user的键中存放的值的个数2
        System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true
        System.out.println(jedis.hkeys("user"));//返回map对象中的所有key
        System.out.println(jedis.hvals("user"));//返回map对象中的所有value

        Iterator<String> iter = jedis.hkeys("user").iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + jedis.hmget("user", key));
        }
    }

    /**
     * jedis操作List
     */
    @Test
    public void testList() {
        //开始前，先移除所有的内容
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework", 0, -1));
        //先向key java framework中存放三条数据
        jedis.lpush("java framework", "spring");
        jedis.lpush("java framework", "struts");
        jedis.lpush("java framework", "hibernate");
        //再取出所有数据jedis.lrange是按范围取出，
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        System.out.println(jedis.lrange("java framework", 0, -1));

        jedis.del("java framework");
        jedis.rpush("java framework", "spring");
        jedis.rpush("java framework", "struts");
        jedis.rpush("java framework", "hibernate");
        System.out.println(jedis.lrange("java framework", 0, -1));
    }

    /**
     * jedis操作Set
     */
    @Test
    public void testSet() {
        //添加
        jedis.sadd("user", "liuling");
        jedis.sadd("user", "xinxin");
        jedis.sadd("user", "ling");
        jedis.sadd("user", "zhangxinxin");
        jedis.sadd("user", "who");
        //移除noname
        jedis.srem("user", "who");
        System.out.println(jedis.smembers("user"));//获取所有加入的value
        System.out.println(jedis.sismember("user", "who"));//判断 who 是否是user集合的元素
        System.out.println(jedis.srandmember("user"));
        System.out.println(jedis.scard("user"));//返回集合的元素个数
    }

    @Test
    public void test() throws InterruptedException {
        //jedis 排序
        //注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
        jedis.del("a");//先清除数据，再加入数据进行测试
        jedis.rpush("a", "1");
        jedis.lpush("a", "6");
        jedis.lpush("a", "3");
        jedis.lpush("a", "9");
        System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
        System.out.println(jedis.sort("a")); //[1, 3, 6, 9]  //输入排序后结果
        System.out.println(jedis.lrange("a", 0, -1));
    }

    @Test
    public void testRedisPool() {
//        RedisUtil.getJedis().set("newname", "中文测试");
//        System.out.println(RedisUtil.getJedis().get("newname"));
    }

    @Test
    public void testHashgetall() {
        Map<String, String> map = jedis.hgetAll("ACTIVITY_STORE_HASH:304");
        System.out.println(map.size());
    }

    /**
     * 当jedisPool中jedis资源数不超过maxTotal，超过的第N个从jedisPool中获取jedis的请求会线程挂起，挂起时间为maxWaitMills；
     * 当过长中
     * @throws InterruptedException
     */
    @Test
    public void testJedisPoolOverTime() throws InterruptedException {
        JedisPoolConfig config = new JedisPoolConfig();
        //池对象，即jedisPool最大数量
        config.setMaxTotal(3);
        config.setMaxIdle(3);
        //从池中获取池对象，缺少时等待，等待超过毫秒数放弃等待
        config.setMaxWaitMillis(5000);
//        config.setBlockWhenExhausted(false);

        final int sleepSecond = 5;
        JedisPool jedisPool = new JedisPool(config, "192.168.107.16", 6379, 3000, null, 4);

        for (int i = 1; i <= 5; i++) {
            final int index = i;
            if (index == 5) {
                System.out.println("asset");
            }

            new Thread(() -> {
                action(jedisPool, index, sleepSecond);
            }).start();

//            CompletableFuture.runAsync(() -> {
//                action(jedisPool, index, sleepSecond);
//            });
        }

        System.out.println("end");
        TimeUnit.SECONDS.sleep(600);

    }

    private void action(JedisPool jedisPool, int index, int sleepSecond) {
        System.out.println("Thread-" + index + "-begin");

        String result = RedisUtils.eval(jedisPool, jedis1 -> {
            String value = jedis1.get("testKey");
            System.out.println("Thread-" + index + "-" + value);
            try {
                TimeUnit.SECONDS.sleep(sleepSecond);
            } catch (InterruptedException ex) {

            }
            return value;
        });
    }


    @Test
    public void testJedisPoolOverTime3() throws InterruptedException {
        JedisPoolConfig config = new JedisPoolConfig();
        //池对象，即jedisPool最大数量
        config.setMaxTotal(3);
        config.setMaxIdle(3);
        //从池中获取池对象，缺少时等待，等待超过毫秒数放弃等待
        config.setMaxWaitMillis(100000);

        final int sleepSecond = 20;
        JedisPool jedisPool = new JedisPool(config, "192.168.107.16", 6379, 3000, null, 4);

        for (int i = 1; i <= 5; i++) {
            final int index = i;
            if (index == 5) {
                System.out.println("asset");
            }

            new Thread(() -> {
                CompletableFuture.runAsync(() -> {
                    System.out.println("Thread-" + index + "-begin");

                    RedisUtils.eval(jedisPool, jedis1 -> {

                        try {
                            TimeUnit.SECONDS.sleep(sleepSecond);
                        } catch (InterruptedException ex) {

                        }

                        String value = jedis1.get("testKey");
                        System.out.println("Thread-" + index + "-" + value);
                        return value;
                    });

                    CompletableFuture.runAsync(() -> {
                        RedisUtils.eval(jedisPool, jedis1 -> {
                            String value = jedis1.get("testKey");
                            System.out.println("Task-" + index + "-" + value);
                            return value;
                        });
                    });
                });
            }).start();
        }

        System.out.println("end");
        TimeUnit.SECONDS.sleep(600);

    }
}
