package com.tg.guava.eventBus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;

/**
 * Created by linzc on 2018/5/8.
 */
public class MultiSubscriber {
    @Test
    public void runTest() throws Exception {
        EventBus eventBus = new EventBus();

        Object subscriber1 = new Object() {
            @Subscribe
            public void handle(Account account) {
                System.out.println("接收器1正处理：" + account + ";hash:" + account.hashCode());
                account.setName("tg1");
            }
        };
        Object subscriber2 = new Object() {
            @Subscribe
            public void handle(Account account) {
                System.out.println("接收器2正处理：" + account + ";hash:" + account.hashCode());
                account.setName("tg2");
            }
        };

        eventBus.register(subscriber1);
        eventBus.register(subscriber2);

        Account account = new Account();
        account.setName("mark");
        eventBus.post(account);
        System.out.println("处理完成：" + account + ";hash:" + account.hashCode());
    }

    public class Account {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
