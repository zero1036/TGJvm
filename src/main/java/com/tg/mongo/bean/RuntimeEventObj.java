package com.tg.mongo.bean;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;

/**
 * RuntimeEvent
 * Created by linzc on 2017/6/2.
 */
@Entity(value = "RuntimeEventObj", noClassnameStored = true)
public class RuntimeEventObj {
    /**
     * key
     */
    @Id
    private ObjectId id;

    @Property("Cid")
    private Integer customerId;

    @Property("Evt")
    private String event;

    /**
     * 优先取事件发生时间
     */
    @Property("Time")
    private Date createDate;

    @Property("Data")
    private Object data;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
