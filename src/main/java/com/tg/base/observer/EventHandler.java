package com.tg.base.observer;

/**
 * Created by linzc on 2017/6/9.
 */
public interface EventHandler<T> {

    String getTopic();

    void setTopic(String topic);

    void handle(T event);
}
