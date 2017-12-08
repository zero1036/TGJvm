package com.tg.guava.eventBus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.*;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

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
}
