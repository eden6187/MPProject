package com.example.mpproject.Fragments;

import android.graphics.drawable.Drawable;

public class PetcareListViewItem {
    private Drawable mIconDrawable ;
    private Drawable mStar;
    private String mPetcareTitle ;
    private String mPetcareRatingnum;
    private String mPetcareReviewcount;
    private String mPetcareDistance;


    public void setIcon(Drawable icon) {
        mIconDrawable = icon ;
    }
    public void setStar(Drawable star){
        mStar = star;
    }
    public void setTitle(String title) {
        mPetcareTitle = title ;
    }
    public void setRatingnum(float num){
        mPetcareRatingnum = String.valueOf(num);
    }
    public void setReviewcount(int count){
        mPetcareReviewcount = "리뷰 수: " + String.valueOf(count) + "개 ";
    }
    public void setDistance(int distance){
        mPetcareDistance = String.valueOf(distance) + "m";
    }

    public Drawable getIcon() {
        return this.mIconDrawable ;
    }
    public Drawable getStar(){
        return this.mStar;
    }
    public String getTitle() {
        return this.mPetcareTitle ;
    }

    public String getRatingnum(){
        return this.mPetcareRatingnum;
    }
    public String getReviewcount(){
        return this.mPetcareReviewcount;
    }
    public String getDistance(){
        return this.mPetcareDistance;
    }

}
