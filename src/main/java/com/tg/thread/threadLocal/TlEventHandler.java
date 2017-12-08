package com.tg.thread.threadLocal;

import com.tg.mongo.bean.Customer;

import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;

/**
 * Created by linzc on 2017/8/21.
 */
public class TlEventHandler {
    public ThreadLocal<Customer> customerThreadLocal = new ThreadLocal<>();

    public void invoke(Customer customer, Consumer<Customer> consumer) {
        try {
            consumer.accept(customer);
            Thread.sleep(1000);
            System.out.println("Thread " + Thread.currentThread().getName() + " name " + customer.getName());
        } catch (InterruptedException ex) {
        }
    }

    public void invoke2(Customer customer, Consumer<Customer> consumer) {
        try {
            customerThreadLocal.set(customer);
            consumer.accept(customerThreadLocal.get());
            Thread.sleep(1000);
            //无效：多线程下输出如下：原因ThreadLocal.set()设置的对象是分别指向主线程的Customer的
            //            Thread Thread-1 name eh-2
            //            Thread Thread-0 name eh-2
            System.out.println("Thread " + Thread.currentThread().getName() + " name " + customerThreadLocal.get().getName());
        } catch (InterruptedException ex) {
        }
    }

    public void invoke3(Customer customer, Consumer<Customer> consumer) {
        try {
            Customer target = copy(customer);
            consumer.accept(target);
            Thread.sleep(1000);
            System.out.println("Thread " + Thread.currentThread().getName() + " name " + target.getName());
        } catch (InterruptedException ex) {
        }
    }

    private Customer copy(Customer source) {
        Customer customer = new Customer();
        customer.setName(source.getName());
        return customer;
    }
}
