package com.tg.url;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by linzc on 2018/4/10.
 */
public class TestObject {

    private BigDecimal account;

    @ParseUrlIgnore
    private Integer id;

    private String name;

    private List<Integer> cars;

    private List<Child> children;

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getCars() {
        return cars;
    }

    public void setCars(List<Integer> cars) {
        this.cars = cars;
    }


    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }


}
