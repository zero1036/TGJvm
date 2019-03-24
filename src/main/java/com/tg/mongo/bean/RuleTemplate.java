package com.tg.mongo.bean;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linzc on 2018/5/24.
 */
@Setter
@Getter
@Entity(value = "RuleTemplate", noClassnameStored = true)
public class RuleTemplate extends BaseEntity implements Serializable {
    /**
     * 标题
     */
    @Property("Title")
    private String title;

    /**
     * 激活
     */
    @Property("IsActive")
    private Boolean active;

    /**
     * Key
     */
    @Property("Key")
    private String key;

    /**
     * 事件名,每个模板都必须与事件关联
     */
    @Property("EventName")
    private String eventName;

    /**
     * 条件模板集合
     */
    @Embedded("ConditionTemplates")
    private List<String> conditionTemplates;

    /**
     * 奖励发送目标，用于统计周期内完成任务次数统计
     */
    @Property("Target")
    private String target;

    /**
     * 不影响累计的条件模板Key集合
     */
    @Embedded("CalculatedCon")
    @Deprecated
    private List<String> calculatedConditions;

    /**
     * 表达式模板
     */
    @Property("ExpressionTemplate")
    private String expressionTemplate;

    /**
     * 业务处理单元集合
     */
    @Embedded("Tasks")
    private List<String> tasks;

    /**
     * 规则模板事件名称与事件模型的名称比较
     *
     * @param event 事件模型
     * @return 两者是否一致
     */
    public boolean compareEventName(Event event) {
        if (event == null) {
            return false;
        }
        if (Strings.isNullOrEmpty(this.eventName) || Strings.isNullOrEmpty(event.getEventName())) {
            return false;
        } else {
            return this.eventName.replace(" ", "").equalsIgnoreCase(event.getEventName().replace(" ", ""));
        }
    }
}
