package com.tg.mongo.bean;

import lombok.Getter;
import lombok.Setter;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

import java.util.ArrayList;

/**
 * 条件模板
 * Created by chipg on 2016/10/18.
 */
@Setter
@Getter
@Entity(value = "ConditionTemplate", noClassnameStored = true)
public class ConditionTemplate extends BaseEntity {
    /**
     * key
     */
    @Property("Key")
    private String key;

    /**
     * 标题
     */
    @Property("Title")
    private String title;

    /**
     * 模板信息，直接保存html
     */
    @Property("Template")
    private String template;

    /**
     * 是否删除
     */
    @Property("IsDel")
    private boolean deleted;
}
