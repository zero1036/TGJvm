package com.tg.mongo.bean;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by linzc on 2018/5/24.
 */
public class BaseEntity {
    /**
     * id
     */
    @Id
    private ObjectId id;

    public ObjectId getId() {
        return this.id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getOid() {
        return this.id.toString();
    }
}
