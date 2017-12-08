package com.tg.base.observer;

import org.junit.Test;

/**
 * Created by linzc on 2017/6/9.
 */
public class EventBusTest {


    public class MQEventHandler implements EventHandler<String> {
        private String topic;

        public MQEventHandler(String topic) {
            this.topic = topic;
        }

        @Override
        public String getTopic() {
            return this.topic;
        }

        @Override
        public void setTopic(String topic) {
            this.topic = topic;
        }

        @Override
        public void handle(String event) {
            System.out.println("Topic " + topic + " m handling event:" + event);
        }
    }

    @Test
    public void test() {
        EventBus<String> eventBus = new EventBus<>();

        eventBus.register(new MQEventHandler("1"));
        eventBus.register(new MQEventHandler("2"));
        eventBus.register(new MQEventHandler("3"));

        eventBus.post("EA");
        eventBus.post("EB");

        eventBus.unregister("1");
        eventBus.post("EC");
    }
}
