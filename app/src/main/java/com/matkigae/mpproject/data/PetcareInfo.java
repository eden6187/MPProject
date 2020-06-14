package com.matkigae.mpproject.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;
import java.util.HashMap;

public class PetcareInfo implements Parcelable, Comparable<PetcareInfo> {

    private String mUserId = "ANONYMOUS";
    private double mXcoordinate = 0.1;
    private double mYcoordinate = 0.1;
    private String mPetcareTitle = "ANONYMOUS";
    private String mPetcareInfo = "NONE";
    private String mPetcareIntro = "NONE";
    private String mAvailableDate ="0000000";
    private String mPrice = "0";
    private String mAddress = "수원시";

    public PetcareInfo(){ } // 지우지 마세요 Firebase 연동시 필요합니다.

    /*** Getter And Setter ***/
    public String getmUserId() { return mUserId; }

    public void setmUserId(String mUserId) { this.mUserId = mUserId; }

    public double getmXcoordinate() { return mXcoordinate; }

    public void setmXcoordinate(double mXcoordinate) { this.mXcoordinate = mXcoordinate; }

    public double getmYcoordinate() { return mYcoordinate; }

    public void setmYcoordinate(double mYcoordinate) { this.mYcoordinate = mYcoordinate; }

    public String getmPetcareTitle() { return mPetcareTitle; }

    public void setmPetcareTitle(String mPetcareTitle) { this.mPetcareTitle = mPetcareTitle; }

    public String getmPetcareInfo() { return mPetcareInfo; }

    public void setmPetcareInfo(String mPetcareInfo) { this.mPetcareInfo = mPetcareInfo; }

    public String getmPetcareIntro() { return mPetcareIntro; }

    public void setmPetcareIntro(String mPetcareIntro) { this.mPetcareIntro = mPetcareIntro; }

    public String getmAvailableDate() { return mAvailableDate; }

    public void setmAvailableDate(String mAvailableDate) { this.mAvailableDate = mAvailableDate; }

    public String getmPrice() { return mPrice; }

    public void setmPrice(String mPrice) { this.mPrice = mPrice; }

    public void setmAddress(String mAddress){
        this.mAddress = mAddress;
    }

    public String getmAddress(){
        return mAddress;
    }

    /*** Parcel ***/

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PetcareInfo> CREATOR = new Creator<PetcareInfo>() {
        @Override
        public PetcareInfo createFromParcel(Parcel source) {
            return new PetcareInfo(source);
        }

        @Override
        public PetcareInfo[] newArray(int size) {
            return new PetcareInfo[size];
        }
    };

    private PetcareInfo(Parcel src){
        mUserId = src.readString();
        mXcoordinate = src.readDouble();
        mYcoordinate = src.readDouble();
        mPetcareTitle = src.readString();
        mPetcareInfo = src.readString();
        mPetcareIntro = src.readString();
        mAvailableDate = src.readString();
        mPrice = src.readString();
        mAddress = src.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUserId);
        dest.writeDouble(mXcoordinate);
        dest.writeDouble(mYcoordinate);
        dest.writeString(mPetcareTitle);
        dest.writeString(mPetcareInfo);
        dest.writeString(mPetcareIntro);
        dest.writeString(mAvailableDate);
        dest.writeString(mPrice);
        dest.writeString(mAddress);
    }

    /*** FirebasePost ***/

    public HashMap<String,Object> toMap(){
        HashMap<String, Object> post = new HashMap<String,Object>();

        post.put("mUserId",this.mUserId);
        post.put("mXcoordinate",this.mXcoordinate);
        post.put("mYcoordinate",this.mYcoordinate);
        post.put("mPetcareTitle",this.mPetcareTitle);
        post.put("mPetcareInfo",this.mPetcareInfo);
        post.put("mPetcareIntro",this.mPetcareIntro);
        post.put("mAvailableDate",this.mAvailableDate);
        post.put("mPrice",this.mPrice);
        post.put("mAddress",this.mAddress);

        return post;
    }

    @Override
    public int compareTo(PetcareInfo o) {
        if(Integer.parseInt(this.mPrice) > Integer.parseInt(o.getmPrice())){
            return 1;
        }else if(Integer.parseInt(this.mPrice) < Integer.parseInt(o.getmPrice())){
            return -1;
        }
        return 0;
    }
}