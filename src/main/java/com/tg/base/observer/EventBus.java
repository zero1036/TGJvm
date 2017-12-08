package com.tg.base.observer;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * EventBus
 * Created by linzc on 2017/6/9.
 */
public class EventBus<T> {
    /**
     * 发布者
     *
     * @param <T> 事件类型
     */
    private class SimpleObservable<T> extends Observable {
        public void notify(T event) {
            setChanged();
            //只有在setChange()被调用后，notifyObservers()才会去调用update()，否则不处理。
            notifyObservers(event);
        }
    }

    /**
     * 观察者类Listener
     */
    public class SimpleObserver implements Observer {

        private EventHandler handler;

        public SimpleObserver(EventHandler<T> eventHandler) {
            handler = eventHandler;
        }

        public void update(Observable observable, Object data) {  // data为任意对象，用于传递参数
            System.out.println("Data has changed to " + data.toString());
            handler.handle((T) data);
        }
    }

    /**
     * 主题-观察者池
     */
    private final Map<String, Observer> observerPool = Maps.newConcurrentMap();

    /**
     * 发布者
     */
    private final SimpleObservable observable = new SimpleObservable();

    /**
     * 注册事件处理器
     *
     * @param eventHandler 事件处理器
     */
    public void register(EventHandler<T> eventHandler) {
        synchronized (observerPool) {
            if (!observerPool.containsKey(eventHandler.getTopic())) {
                SimpleObserver observer = new SimpleObserver(eventHandler);
                observerPool.put(eventHandler.getTopic(), observer);
                observable.addObserver(observer);
            }
        }
    }

    /**
     * 销毁事件处理器
     *
     * @param topic 主题
     */
    public void unregister(String topic) {
        synchronized (observerPool) {
            if (observerPool.containsKey(topic)) {
                Observer observer = observerPool.get(topic);//通过主题获取观察者，再销毁
                observable.deleteObserver(observer);
                observerPool.remove(topic);
            }
        }
    }

    /**
     * 发布事件
     *
     * @param event 事件
     */
    public void post(T event) {
        observable.notify(event);
    }
}
