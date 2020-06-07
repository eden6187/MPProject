package com.example.mpproject.fragments;

public class PetcareInfo {

    private int mIcon;
    private int mStar;
    private String mPetcareTitle = "NoneTitled";
    private String mPetcareRatingnum = "0";
    private String mPetcareReviewcount = "0";
    private String mPetcareDistance = "0";

    public PetcareInfo(int icon, int star, String title, String ratingnum, String reviewcnt,
                       String distance)
    {
        this.mIcon = icon;
        this.mStar = star;
        this.mPetcareRatingnum = ratingnum;
        this.mPetcareReviewcount = reviewcnt;
        this.mPetcareTitle = title;
        this.mPetcareDistance = "0";
    }

    /**
     *Getter
     **/
    public void setIcon(int icon) {
        mIcon = icon ;
    }
    public void setStar(int star){
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

    /**
     *Setter
     **/
    public void setDistance(int distance){
        mPetcareDistance = String.valueOf(distance) + "m";
    }
    public int getIcon() {
        return this.mIcon;
    }
    public int getStar(){
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
