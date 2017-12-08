package com.tg.mongo.bean;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * RuntimeEvent
 * Created by linzc on 2017/6/2.
 */
@Entity(value = "RuntimeEvent", noClassnameStored = true)
public class RuntimeEvent<T> {

    @Entity(noClassnameStored = true)
    public class EventNum{
        @Property("mid")
        private String mid ;

        @Property("num")
        private Long num;
    }
    /**
     * id
     */
    @Id
    private ObjectId id;

    /**
     * 客户Id
     */
    @Property("cid")
    private String customerId;

    /**
     * 事件名称、交换器名称
     */
    @Property("evt")
    private String eventName;

    /**
     * 发生时间
     */
    @Property("time")
    private Date time;

    /**
     * 所属活动Id集合
     */
    @Embedded("aids")
    private List<EventNum> activityIds;

    /**
     * 事件序列化数据
     */
    @Property("data")
    private Object data;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<EventNum> getActivityIds() {
        return activityIds;
    }

    public void setActivityIds(List<EventNum> activityIds) {
        this.activityIds = activityIds;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
