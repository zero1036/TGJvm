package com.tg.base.observer;

import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by linzc on 2017/6/9.
 */
public class ObjserverTest {
    public class SimpleObservable extends Observable {
        private int data = 0;

        public int getData() {
            return data;
        }

        public void setData(int i) {
            if (this.data != i) {
                this.data = i;
                setChanged();

                //只有在setChange()被调用后，notifyObservers()才会去调用update()，否则什么都不干。
                notifyObservers();
            }
        }
    }

    /**
     * 观察者类 = Listener
     */
    public class SimpleObserver implements Observer {
        public void update(Observable observable, Object data) {  // data为任意对象，用于传递参数
            System.out.println("Data has changed to" + ((SimpleObservable) observable).getData());
        }
    }

    @Test
    public void test1() {
        SimpleObservable doc = new SimpleObservable();

        SimpleObserver target;
        doc.addObserver(new SimpleObserver());
        doc.addObserver(target = new SimpleObserver());

        doc.setData(1);
        doc.setData(2);
        doc.setData(2);

        doc.deleteObserver(target);//取消监听

        doc.setData(3);

    }
}
