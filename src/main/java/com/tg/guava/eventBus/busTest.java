package com.tg.guava.eventBus;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by linzc on 2017/6/12.
 */
public class busTest {
    @Test
    public void runTest() throws Exception {
        EventBus eventBus = new EventBus();

        eventBus.register(new Object() {
            @Subscribe
            public void handle(Integer num) {
                System.out.println("正处理数值：" + num);
            }

            @Subscribe
            public void handle(String txt) {
                System.out.println("正处理文本：" + txt);
            }
        });

        eventBus.post(2);
        eventBus.post("abc");
    }

    @Test
    public void testThreadSafe() throws Exception {
        EventBus eventBus = new EventBus();

        eventBus.register(new Object() {
            @Subscribe
            @AllowConcurrentEvents
            public void handle(Integer num) {
                try {

                    if (num == 0) {
                        System.out.println("Thread=" + Thread.currentThread().getName() + " sleep=" + num);
                        TimeUnit.SECONDS.sleep(5);
                    }
                    System.out.println("Thread=" + Thread.currentThread().getName() + " 正处理数值=" + num);
                } catch (InterruptedException ex) {
                }
            }
        });

        for (int i = 0; i < 10; i++) {
            final int target = i;
            CompletableFuture.supplyAsync(() -> {
                eventBus.post(target);
                return 1;
            });
        }
        TimeUnit.SECONDS.sleep(12);
    }
}
