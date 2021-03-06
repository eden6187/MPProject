package com.matkigae.mpproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.matkigae.mpproject.R;
import com.matkigae.mpproject.data.PetcareInfo;

public class PetcareinfoActivity extends AppCompatActivity {
    PetcareInfo mPetcareInfo;
    Button mBtnGotoReservation;
    TextView mTvShopInfo;
    TextView mTvShopIntro;
    ImageView mIvShopIcon;
    Button mBtnGotoGoogleMap;
    Intent mIntentToGoogle;
    private final String mPackageName = "com.google.android.apps.maps";

    public void initView(){
        mBtnGotoReservation = findViewById(R.id.button_petcareinfo_gotoreservation);
        mTvShopInfo = findViewById(R.id.textview_petacareinfo_info);
        mTvShopIntro = findViewById(R.id.textview_petcareinfo_intro);
        mIvShopIcon = findViewById(R.id.imageview_petcareinfo_shopicon);
        mBtnGotoGoogleMap = findViewById(R.id.button_shop_info_toGoogleMap);

        mIntentToGoogle = this.getPackageManager().getLaunchIntentForPackage(mPackageName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petcareinfo);
        initView();

        mPetcareInfo = getIntent().getParcelableExtra("petcareinfo");
        mBtnGotoReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetcareinfoActivity.this, ReservationActivity.class);
                intent.putExtra("petcareinfo",mPetcareInfo);
                startActivity(intent);
            }
        });
//
        mBtnGotoGoogleMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:"+mPetcareInfo.getmXcoordinate()+","+mPetcareInfo.getmYcoordinate()+"?q="+mPetcareInfo.getmAddress());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        mIvShopIcon.setImageResource(R.drawable.ic_shopinfo_default);
        mTvShopIntro.setText(mPetcareInfo.getmPetcareIntro());
        mTvShopInfo.setText(mPetcareInfo.getmPetcareInfo());
    }


}
