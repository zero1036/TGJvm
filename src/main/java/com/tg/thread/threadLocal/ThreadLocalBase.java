package com.tg.thread.threadLocal;

import com.tg.mongo.bean.Customer;

/**
 * Created by linzc on 2016/11/9.
 */
public class ThreadLocalBase {
    public static void main2(String[] args) {
        final Customer customer = new Customer();
        customer.setName("TG");

        ThreadLocal<Customer> tl = new ThreadLocal<>();
        tl.set(customer);

        //输出：TG
        System.out.println(tl.get().getName());

        customer.setName("Sar");
        //输出：Sar
        System.out.println(tl.get().getName());
    }

    public static void main3(String[] args) throws InterruptedException {
        final Customer customer = new Customer();
        customer.setName("TG");

        TlEventHandler handler1 = new TlEventHandler();
        TlEventHandler handler2 = new TlEventHandler();

        Thread thread1 = new Thread(() -> {
            handler1.invoke(customer, c -> c.setName("eh-1"));
        });
        Thread thread2 = new Thread(() -> {
            handler2.invoke(customer, c -> c.setName("eh-2"));
        });

        thread1.start();
        thread2.start();
        Thread.sleep(3000);

        //Thread Thread-0 name eh-2
        //Thread Thread-1 name eh-2
        //eh-2
        System.out.println(customer.getName());
    }

    public static void main(String[] args) throws InterruptedException {
        final Customer customer = new Customer();
        customer.setName("TG");

        TlEventHandler handler1 = new TlEventHandler();
        TlEventHandler handler2 = new TlEventHandler();

        Thread thread1 = new Thread(() -> {
            handler1.invoke3(customer, c -> c.setName("eh-1"));
        });
        Thread thread2 = new Thread(() -> {
            handler2.invoke3(customer, c -> c.setName("eh-2"));
        });

        thread1.start();
        thread2.start();
        Thread.sleep(3000);

        //Thread Thread-0 name eh-2
        //Thread Thread-1 name eh-2
        //eh-2
        System.out.println(customer.getName());
    }


}




