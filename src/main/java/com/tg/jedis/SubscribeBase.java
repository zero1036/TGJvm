package com.tg.jedis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * SubscribeBase
 * Created by linzc on 2017/7/4.
 */
public class SubscribeBase {
    private Jedis jedis;

//    private JedisPool

    @Before
    public void setup() {
        //连接redis服务器，192.168.0.100:6379
        jedis = new Jedis("192.168.105.84", 6379);
        jedis.select(5);
//        //权限认证
//        jedis.auth("admin");
    }

    /**
     * redis存储字符串
     */
    @Test
    public void testString() {
        RedisMsgPubSubListener listener = new RedisMsgPubSubListener();
        jedis.subscribe(listener, "listenerTest");
    }

    @Test
    public void testPublish() throws Exception{
//        jedis
        jedis.publish("listenerTest", "我是天才");
//        Thread.sleep(5000);
//        jedis.publish("listenerTest", "我牛逼");
//        Thread.sleep(5000);
//        jedis.publish("listenerTest", "哈哈");
    }

    public class RedisMsgPubSubListener extends JedisPubSub {
        @Override
        public void unsubscribe() {
            super.unsubscribe();
        }

        @Override
        public void unsubscribe(String... channels) {
            super.unsubscribe(channels);
        }

        @Override
        public void subscribe(String... channels) {
            super.subscribe(channels);
        }

        @Override
        public void psubscribe(String... patterns) {
            super.psubscribe(patterns);
        }

        @Override
        public void punsubscribe() {
            super.punsubscribe();
        }

        @Override
        public void punsubscribe(String... patterns) {
            super.punsubscribe(patterns);
        }

        @Override
        public void onMessage(String channel, String message) {
            System.out.println("channel:" + channel + "receives message :" + message);
            this.unsubscribe();
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {

        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
            System.out.println("channel:" + channel + "is been subscribed:" + subscribedChannels);
        }

        @Override
        public void onPUnsubscribe(String pattern, int subscribedChannels) {

        }

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {

        }

        @Override
        public void onUnsubscribe(String channel, int subscribedChannels) {
            System.out.println("channel:" + channel + "is been unsubscribed:" + subscribedChannels);
        }
    }
}
