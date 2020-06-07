package com.example.mpproject.data;

import com.example.mpproject.R;
import com.example.mpproject.fragments.PetcareInfo;

public class Data {
    Data(){

    }

    public static PetcareInfo[] petcareInfos = {

            /**
            MEMBER OF SHOPINFO
            private Drawable mIcon;
            private Drawable mStar;
            private String mPetcareTitle = "NoneTitled";
            private String mPetcareRatingnum = "0";
            private String mPetcareReviewcount = "0";
            private String mPetcareDistance = "0";
             **/


            new PetcareInfo(R.drawable.ic_shopinfo_default,R.drawable.star_image,"SHOP1","0","0","0"),
            new PetcareInfo(R.drawable.ic_shopinfo_default,R.drawable.star_image,"SHOP2","0","0","0"),
            new PetcareInfo(R.drawable.ic_shopinfo_default,R.drawable.star_image,"SHOP3","0","0","0"),
    };


}
