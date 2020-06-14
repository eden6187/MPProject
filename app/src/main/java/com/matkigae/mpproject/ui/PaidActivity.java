package com.matkigae.mpproject.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.matkigae.mpproject.R;
import com.matkigae.mpproject.data.PetcareInfo;
import com.matkigae.mpproject.listeners.NavigationViewItemListener;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class PaidActivity extends AppCompatActivity {

    Button mBtnGoToList;
    NavigationView mNv;
    DrawerLayout mDl;
    Toolbar mTb;

    TextView mTvCost;
    TextView mTvName;
    TextView mTvLocation;
    TextView mTvStart;
    TextView mTvEnd;

    PetcareInfo mPetcareInfo;

    public void initView() {
        mTb = findViewById(R.id.toolbar);
        setSupportActionBar(mTb);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //햄버거 메뉴 활성화
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);
        //햄버거 메뉴 아이콘 설정

        mNv = findViewById(R.id.nav_view);
        mDl = findViewById(R.id.drawer_layout);
        mBtnGoToList = findViewById(R.id.button_back_to_list);

        mTvCost = findViewById(R.id.text_paid_cost);
        mTvName = findViewById(R.id.text_paid_provName);
        mTvLocation = findViewById(R.id.text_paid_provLocation);
        mTvStart = findViewById(R.id.text_paid_provStart);
        mTvEnd = findViewById(R.id.text_paid_provEnd);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donereservation);
        initView();
        mNv.setNavigationItemSelectedListener(new NavigationViewItemListener(this));

        Intent intent = getIntent();
        mPetcareInfo = intent.getParcelableExtra("petcareinfo");
        final String mStartTime = intent.getStringExtra("startdate");
        final String mEndTime = intent.getStringExtra("enddate");

        System.out.println(mStartTime);
        System.out.println(mEndTime);

        mTvName.setText(mPetcareInfo.getmPetcareTitle() + "에 예약이 완료되었습니다!");
//        mTvLocation.setText(mPetcareInfo.getmUserId() + "의 주소는\n" + );
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
        String start = "";
        String end = "";
        try {
            Date date1 = simpleDateFormat.parse(mStartTime);
            Date date2 = simpleDateFormat.parse(mEndTime);
            start = simpleDateFormat2.format(date1);
            end = simpleDateFormat2.format(date2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println(start);
        System.out.println(end);

        mTvStart.setText("예약은 " + start + "에 시작되고");
        mTvEnd.setText(end + "에 종료됩니다.");
        mTvCost.setText("총 " + mPetcareInfo.getmPrice() + "원이 결제되었습니다.");

        mBtnGoToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaidActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home: // 좌측의 햄버거 메뉴 버튼을 눌렀을 때
                mDl.openDrawer(mNv); //Drawer의 NavigationView가 튀어나오도록 해준다.
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

