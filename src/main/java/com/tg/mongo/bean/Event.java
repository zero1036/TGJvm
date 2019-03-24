package com.tg.mongo.bean;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

import java.util.List;

/**
 * Created by linzc on 2018/5/24.
 */

@Entity(value = "EventModel", noClassnameStored = true)
public class Event {
    /**
     * id
     */
    @Property("_id")
    private ObjectId id;

    /**
     * 事件名
     */
    @Property("EventName")
    private String eventName;

    /**
     * 事件描述
     */
    @Property("EventDescription")
    private String eventDescription;

    /**
     * 调度表达式
     */
    @Property("Expression")
    private String scheduleExpression;

    /**
     * 业务链：事件对应所有业务单元
     */
    @Embedded("Tasks")
    private List<String> tasks;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getScheduleExpression() {
        return scheduleExpression;
    }

    public void setScheduleExpression(String scheduleExpression) {
        this.scheduleExpression = scheduleExpression;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }
}
