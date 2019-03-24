package com.tg.excel;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by linzc on 2018/11/27.
 */
public class ScheduleRule {
    @JsonProperty("EventNameOrCron")
    private String EventNameOrCron;

    public String getEventNameOrCron() {
        return EventNameOrCron;
    }

    public void setEventNameOrCron(String eventNameOrCron) {
        EventNameOrCron = eventNameOrCron;
    }
}

