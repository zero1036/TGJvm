package com.tg.rxjava;

//import io.reactivex.Observable;
//import io.reactivex.Observer;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.observables.AsyncOnSubscribe;
import rx.observables.SyncOnSubscribe;
import rx.schedulers.Schedulers;
//import org.reactivestreams.Subscriber;

/**
 * Created by linzc on 2017/7/25.
 */
public class rxjavaTest {

    public static void main(String[] args) throws Exception {
//        Observable<Integer> observable = Observable.


        Observable<Integer> observable = Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> observer) {
                        for (int i = 0; i < 5; i++) {
                            observer.onNext(i);
                        }
                        observer.onCompleted();
                    }
                });
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("runnint " + integer);
            }
        });

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("runnint2 " + integer);
            }
        });
        System.out.println("other...");
    }


    public static void main2(String[] args) throws Exception {
        Observable<Integer> observable = Observable.just(1, 2);
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io());


        observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error" + e);
            }

            @Override
            public void onNext(Integer result) {
                System.out.println("result = " + result);
            }
        });
        System.out.println("other...");
        Thread.sleep(5000); //耗时的操作
    }



    public static void main3(String[] args) throws Exception {
        Observable<Long> observable = Observable.just(1, 2)
                .subscribeOn(Schedulers.io()).map(new Func1<Integer, Long>() {
                    @Override
                    public Long call(Integer t) {
                        try {
                            Thread.sleep(1000); //耗时的操作
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return (long) (t * 2);
                    }
                });

        observable.subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error" + e);
            }

            @Override
            public void onNext(Long result) {
                System.out.println("result2 = " + result);
            }
        });

        observable.subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error" + e);
            }

            @Override
            public void onNext(Long result) {
                System.out.println("result = " + result);
            }
        });
        System.out.println("other...");
        Thread.sleep(5000); //耗时的操作
    }
}
