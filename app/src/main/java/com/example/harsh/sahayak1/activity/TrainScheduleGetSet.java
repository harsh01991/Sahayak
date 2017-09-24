package com.example.harsh.sahayak1.activity;

import java.util.ArrayList;

/**
 * Created by harsh on 13-12-2016.
 */

public class TrainScheduleGetSet {
    private int halt;
    private String distance;
    private String code;
    private String fullname;
    private int day;
    private String scharr;
    private String schdep;
    private ArrayList<String> schedule;

    public int getHalt() {
        return halt;
    }

    public void setHalt(int halt) {
        this.halt = halt;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getScharr() {
        return scharr;
    }

    public void setScharr(String scharr) {
        this.scharr = scharr;
    }

    public String getSchdep() {
        return schdep;
    }

    public void setSchdep(String schdep) {
        this.schdep = schdep;
    }

    public ArrayList<String> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<String> schedule) {
        this.schedule = schedule;
    }
}


