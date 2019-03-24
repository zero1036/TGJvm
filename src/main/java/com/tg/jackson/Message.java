package com.tg.jackson;

/**
 * Created by linzc on 2018/4/9.
 */
public class Message<T> {

    private Integer id;

    private T data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
