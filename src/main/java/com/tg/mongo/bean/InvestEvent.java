package com.tg.mongo.bean;

import org.mongodb.morphia.annotations.Property;

/**
 * Created by linzc on 2017/6/2.
 */
//@Entity(value = "InvestEvent", noClassnameStored = true)
public class InvestEvent {

    @Property("Name")
    private String name;

    @Property("Amount")
    private Double amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
