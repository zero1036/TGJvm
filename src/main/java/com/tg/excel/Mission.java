package com.tg.excel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by linzc on 2018/11/27.
 */
public class Mission {
@JsonIgnore
    private String actId;
    @JsonIgnore
    private String actTitle;
    @JsonIgnore
    private String actBeginDate;
    @JsonIgnore
    private String actEndDate;

    @JsonProperty("_id")
    private String id;

    @JsonProperty("Mid")
    private Integer Mid;

    @JsonProperty("ScheduleRule")
    private ScheduleRule ScheduleRule;

    @JsonProperty("BeginDate")
    private String BeginDate;

    @JsonProperty("EndDate")
    private String EndDate;

    @JsonProperty("CycleType")
    private Integer CycleType;

    @JsonProperty("CountInCycle")
    private Integer CountInCycle;

    @JsonProperty("Target")
    private String target;

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActTitle() {
        return actTitle;
    }

    public void setActTitle(String actTitle) {
        this.actTitle = actTitle;
    }

    public String getActBeginDate() {
        return actBeginDate;
    }

    public void setActBeginDate(String actBeginDate) {
        this.actBeginDate = actBeginDate;
    }

    public String getActEndDate() {
        return actEndDate;
    }

    public void setActEndDate(String actEndDate) {
        this.actEndDate = actEndDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMid() {
        return Mid;
    }

    public void setMid(Integer mid) {
        Mid = mid;
    }

    public ScheduleRule getScheduleRule() {
        return ScheduleRule;
    }

    public void setScheduleRule(ScheduleRule scheduleRule) {
        ScheduleRule = scheduleRule;
    }

    public String getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(String beginDate) {
        BeginDate = beginDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public Integer getCycleType() {
        return CycleType;
    }

    public void setCycleType(Integer cycleType) {
        CycleType = cycleType;
    }

    public Integer getCountInCycle() {
        return CountInCycle;
    }

    public void setCountInCycle(Integer countInCycle) {
        CountInCycle = countInCycle;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
