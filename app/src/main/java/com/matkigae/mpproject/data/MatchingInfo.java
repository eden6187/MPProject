package com.matkigae.mpproject.data;

public class MatchingInfo {
    String providerId;
    String consumerId;

    public MatchingInfo(){

    }

    public MatchingInfo(String providerId, String consumerId) {
        this.providerId = providerId;
        this.consumerId = consumerId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }
}
