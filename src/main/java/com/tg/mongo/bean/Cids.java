package com.tg.mongo.bean;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by linzc on 2017/8/2.
 */
@Entity(noClassnameStored = true)
public class Cids {

    @Property("name")
    private String name;

    @Property("id")
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
