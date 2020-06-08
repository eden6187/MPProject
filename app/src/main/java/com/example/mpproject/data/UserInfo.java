package com.example.mpproject.data;

import android.hardware.usb.UsbRequest;

public class UserInfo {
    private static UserInfo instance = null;
    private String mEmailAddress = "ANONYMOUS";

    private UserInfo(){ }

    public static UserInfo getInstance(){
        if(instance == null){
            instance = new UserInfo();
        }
        return UserInfo.instance;
    }

    public String getmEmailAddress() {
        return this.mEmailAddress;
    }

    public boolean setEmailAddress(String address){
        if (mEmailAddress.equals("ANONYMOUS")){
            mEmailAddress = address;
            return true;
        }else{
            return false;
        }
    }
}
