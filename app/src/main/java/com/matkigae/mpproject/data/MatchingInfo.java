package com.matkigae.mpproject.data;

import java.util.HashMap;

public class MatchingInfo {
    PetcareInfo info;
    //년도 4자리 월 2자리 일 2자리 시 2자리 분 2자리
    String startTime;
    String endTime;
    

    public MatchingInfo(){ }

    public MatchingInfo(PetcareInfo info , String startTime, String endTime) {
        this.info = info;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public PetcareInfo getInfo() {
        return info;
    }

    public void setInfo(PetcareInfo info) {
        this.info = info;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
