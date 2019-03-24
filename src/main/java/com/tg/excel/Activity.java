package com.tg.excel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by linzc on 2018/11/27.
 */
public class Activity {




    @JsonProperty("_id")
    private String id;

    @JsonProperty("Title")
    private String Title;

    @JsonProperty("BeginDate")
    private String BeginDate;

    @JsonProperty("EndDate")
    private String EndDate;

    @JsonProperty("Missions")
    private List<Mission> Missions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
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

    public List<Mission> getMissions() {
        return Missions;
    }

    public void setMissions(List<Mission> missions) {
        Missions = missions;
    }
}
