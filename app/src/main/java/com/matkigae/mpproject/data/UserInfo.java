package com.matkigae.mpproject.data;

import java.util.HashMap;

public class UserInfo {
    private static UserInfo instance = null;
    private static String emailAddress = "ANONYMOUS";
    private static String myProviderId = "NONE";

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

    public static void setMyProviderId(String newID){
        UserInfo.myProviderId = newID;
    }

    public static String getMyProviderId() {
        return myProviderId;
    }

    public static HashMap<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("emailAddress", UserInfo.emailAddress);
        return map;
    }
}
