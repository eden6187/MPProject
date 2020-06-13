package com.matkigae.mpproject.data;

import java.util.HashMap;

public class MatchingInfo {
    String providerTitle;
    String consumerId;

    public MatchingInfo(){ }

    public MatchingInfo(String providerId, String consumerId) {
        this.providerTitle = providerId;
        this.consumerId = consumerId;
    }

    public String getProviderTitle() {
        return providerTitle;
    }

    public void setProviderTitle(String providerTitle) {
        this.providerTitle = providerTitle;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public HashMap<String, Object> toMap(){
        HashMap<String, Object> post = new HashMap<>();
        post.put("providerTitle", this.providerTitle);
        post.put("consumerId",this.consumerId);
        return post;
    }
}
