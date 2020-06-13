package com.matkigae.mpproject.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.matkigae.mpproject.R;

import java.util.HashMap;

public class PetcareInfo implements Parcelable {

    private String mUserId = "ANONYMOUS";
    private Long mIcon = (long)R.drawable.ic_shopinfo_default;
    private Long mStar = (long)R.drawable.star_image;
    private double mPetcareRatingnum = 0.1;
    private double mXcoordinate = 0.1;
    private double mYcoordinate = 0.1;
    private String mPetcareTitle = "ANONYMOUS";
    private Long mPetcareReviewcount = new Long(0);

    private PetcareInfo(Parcel src){
        this.mUserId = src.readString();
        this.mIcon = src.readLong();
        this.mStar = src.readLong();
        this.mPetcareRatingnum = src.readDouble();
        this.mPetcareReviewcount = src.readLong();
        this.mPetcareTitle = src.readString();
        this.mXcoordinate = src.readDouble();
        this.mYcoordinate = src.readDouble();
    }

    public PetcareInfo(){ } // 지우지 마세요 Firebase 연동시 필요합니다.

    public PetcareInfo(int icon, String title, double ratingnum, int reviewcnt,
                       double xcor, double ycor, String userId)
    {
        this.mUserId = userId;
        this.mIcon = (long)icon;
        this.mPetcareRatingnum = ratingnum;
        this.mPetcareReviewcount = (long)reviewcnt;
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

    public long getmIcon() { return mIcon; }

    public void setmIcon(int mIcon) {
        this.mIcon = (long)mIcon;
    }

    public long getmStar() { return mStar; }

    public void setmStar(int mStar) {
        this.mStar = (long)mStar;
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

    public long getmPetcareReviewcount() {
        return (long)mPetcareReviewcount;
    }

    public void setmPetcareReviewcount(int mPetcareReviewcount) { this.mPetcareReviewcount = (long)(int)mPetcareReviewcount; }

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
        dest.writeLong(this.mIcon);
        dest.writeLong(this.mStar);
        dest.writeDouble(this.mPetcareRatingnum);
        dest.writeLong(this.mPetcareReviewcount);
        dest.writeString(this.mPetcareTitle);
        dest.writeDouble(this.mXcoordinate);
        dest.writeDouble(this.mYcoordinate);
    }

    public HashMap<String,Object> toMap(){
        HashMap<String, Object> post = new HashMap<String,Object>();

        post.put("mUserId",this.mUserId);
        post.put("mIcon",this.mIcon);
        post.put("mStar",this.mStar);
        post.put("mPetcareRatingnum",this.mPetcareRatingnum);
        post.put("mXcoordinate",this.mXcoordinate);
        post.put("mYcoordinate", this.mYcoordinate);
        post.put("mPetcareTitle",this.mPetcareTitle);
        post.put("mPetcareReviewcount",this.mPetcareReviewcount);

        return post;
    }
}
