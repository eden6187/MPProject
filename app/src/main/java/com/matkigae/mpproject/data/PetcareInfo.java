package com.matkigae.mpproject.data;

import android.os.Parcel;
import android.os.Parcelable;

public class PetcareInfo implements Parcelable {

    private String mUserId = "ANONYMOUS";
    private int mIcon = -1;
    private int mStar = -1;
    private double mPetcareRatingnum = 0;
    private double mXcoordinate = 0;
    private double mYcoordinate = 0;
    private String mPetcareTitle = "ANONYMOUS";
    private int mPetcareReviewcount = 0;

    private PetcareInfo(Parcel src){
        this.mUserId = src.readString();
        this.mIcon = src.readInt();
        this.mStar = src.readInt();
        this.mPetcareRatingnum = src.readDouble();
        this.mPetcareReviewcount = src.readInt();
        this.mPetcareTitle = src.readString();
        this.mXcoordinate = src.readDouble();
        this.mYcoordinate = src.readDouble();
    }

    public PetcareInfo(){ } // 지우지 마세요 Firebase 연동시 필요합니다.

    public PetcareInfo(int icon, String title, double ratingnum, int reviewcnt,
                       double xcor, double ycor, String userId)
    {
        this.mUserId = userId;
        this.mIcon = icon;
        this.mPetcareRatingnum = ratingnum;
        this.mPetcareReviewcount = reviewcnt;
        this.mPetcareTitle = title;
        this.mXcoordinate = xcor;
        this.mYcoordinate = ycor;
    }

    //*** Getter And Setter ***//
    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String userId) {
        mUserId = userId;
    }

    public int getmIcon() { return mIcon; }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public int getmStar() { return mStar; }

    public void setmStar(int mStar) {
        this.mStar = mStar;
    }

    public double getmPetcareRatingnum() {
        return mPetcareRatingnum;
    }

    public void setmPetcareRatingnum(double mPetcareRatingnum) { this.mPetcareRatingnum = mPetcareRatingnum; }

    public double getmXcoordinate() {
        return mXcoordinate;
    }

    public void setmXcoordinate(double mXcoordinate) {
        this.mXcoordinate = mXcoordinate;
    }

    public double getmYcoordinate() {
        return mYcoordinate;
    }

    public void setmYcoordinate(double mYcoordinate) {
        this.mYcoordinate = mYcoordinate;
    }

    public String getmPetcareTitle() {
        return mPetcareTitle;
    }

    public void setmPetcareTitle(String mPetcareTitle) {
        this.mPetcareTitle = mPetcareTitle;
    }

    public int getmPetcareReviewcount() {
        return mPetcareReviewcount;
    }

    public void setmPetcareReviewcount(int mPetcareReviewcount) { this.mPetcareReviewcount = mPetcareReviewcount; }

    /*** Parcelabel methods ***/

    public static final Creator<PetcareInfo> CREATOR = new Creator<PetcareInfo>() {
        @Override
        public PetcareInfo createFromParcel(Parcel source) {
            return new PetcareInfo(source);
        }

        @Override
        public PetcareInfo[] newArray(int size) {
            return new PetcareInfo[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mUserId);
        dest.writeInt(this.mIcon);
        dest.writeInt(this.mStar);
        dest.writeDouble(this.mPetcareRatingnum);
        dest.writeInt(this.mPetcareReviewcount);
        dest.writeString(this.mPetcareTitle);
        dest.writeDouble(this.mXcoordinate);
        dest.writeDouble(this.mYcoordinate);
    }
}
