package com.tg.hystrix.hystrixObservableCommand;

import com.google.common.collect.Lists;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by linzc on 2019/2/14.
 */
public class SingleObservableCommand extends HystrixObservableCommand<Integer> {

    public List<Integer> list;

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public SingleObservableCommand(List<Integer> list) {
        super(HystrixCommandGroupKey.Factory.asKey("usercommand"));// 调用父类构造方法
        this.list = list;
    }


    @Override
    protected Observable<Integer> construct() {
        System.out.println(Thread.currentThread().getName() + "is running......");
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            /*
             * Observable有三个关键的事件方法，分别为onNext，onCompleted，onError
             */
            @Override
            public void call(Subscriber<? super Integer> observer) {
                try {// 写业务逻辑，注意try-catch
                    if (!observer.isUnsubscribed()) {
                        for (Integer id : list) {

                            Integer out = id + 1;
                            System.out.println("请求中：in " + id + " out " + out);
                            TimeUnit.SECONDS.sleep(5);
                            observer.onNext(out);
                        }
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    protected Observable<Integer> resumeWithFallback() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> observer) {
                try {
                    if (!observer.isUnsubscribed()) {


                        observer.onNext(-1);
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public static void main(String[] args) throws InterruptedException {
        List<Integer> input = Lists.newArrayList(1, 3, 5, 7, 9);
        List<Integer> output = Lists.newArrayList();

        SingleObservableCommand command = new SingleObservableCommand(input);
        Observable<Integer> observe = command.observe();
        observe.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("聚合完了所有的查询请求!");
                System.out.println("聚合结果："+output);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onNext(Integer out) {
                output.add(out);
            }

        });
        TimeUnit.SECONDS.sleep(60);
    }
}
