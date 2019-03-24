package com.tg.mongo.bean;

/**
 * Created by linzc on 2017/4/11.
 */

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.List;

@Entity(value = "test3", noClassnameStored = true)
public class Customer {
    /**
     * key
     */
    @Id
    private ObjectId id;

    @Property("name")
    private String name;

    @Property("age")
    private Integer age;

    @Property("phone")
    private String phone;

    @Embedded("role")
    private List<String> role;

    @Property("version")
    private Integer version;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
