package com.example.gateway.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ServiceStatOutput {


    /*
    today:今日流量
    yesterday:昨日流量
     */
    @JsonProperty("today")
    private List<Integer> today;
    @JsonProperty("yesterday")
    private List<Integer> yesterday;

    public ServiceStatOutput() {
    }

    public ServiceStatOutput(List<Integer> today, List<Integer> yesterday) {
        this.today = today;
        this.yesterday = yesterday;
    }

    public List<Integer> getToday() {
        return today;
    }

    public void setToday(List<Integer> today) {
        this.today = today;
    }

    public List<Integer> getYesterday() {
        return yesterday;
    }

    public void setYesterday(List<Integer> yesterday) {
        this.yesterday = yesterday;
    }
}
