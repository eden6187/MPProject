package com.matkigae.mpproject.data;

import java.util.HashMap;

public class UserInfo {
    private static UserInfo instance = null;
    private static String emailAddress = "ANONYMOUS";

    private UserInfo(){ }

    public static UserInfo getInstance(){
        if(instance == null){
            instance = new UserInfo();
        }
        return UserInfo.instance;
    }

    public String getmEmailAddress() {
        return this.emailAddress;
    }

    public boolean setEmailAddress(String address){
        if (emailAddress.equals("ANONYMOUS")){
            emailAddress = address;
            return true;
        }else{
            return false;
        }
    }

    public static HashMap<String, Object> getFirebasePost(){
        HashMap<String, Object> post = new HashMap<String, Object>();
        post.put("emailAddress", UserInfo.emailAddress);

        return post;
    }
}
