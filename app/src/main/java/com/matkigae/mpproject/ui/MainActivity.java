package com.matkigae.mpproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.matkigae.mpproject.R;
import com.matkigae.mpproject.data.UserInfo;
import com.matkigae.mpproject.listeners.NavigationViewItemListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {
    NavigationView mNv;
    DrawerLayout mDl;
    Toolbar mTb;
    Button mBtnGotoCareService;
    Button mBtnGotoProvideService;
    TextView mTvUserId;
    View mNavHeaderView;
    UserInfo mUserInfo;
    FirebaseDatabase mDb;

    private void initView(){
        mTb = findViewById(R.id.toolbar);
        setSupportActionBar(mTb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);

        mNv = findViewById(R.id.nav_view);
        mTvUserId = mNv.findViewById(R.id.textview_navheader_userid);
        mDl = findViewById(R.id.drawer_layout);
        mNavHeaderView = mNv.getHeaderView(0);
        mTvUserId = mNavHeaderView.findViewById(R.id.textview_navheader_userid);
        mBtnGotoProvideService = findViewById(R.id.button_main_gotoprovideservice);
        mBtnGotoCareService = (Button)findViewById(R.id.button_main_gotocareactivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // User 객체 Singleton 으로 생성
        mUserInfo = UserInfo.getInstance();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            mUserInfo.setEmailAddress(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }

        //DataBase 초기화 User 정보 초기화 및 기존 사용자에 대한 정보는 추가하지 않도록 Query 작성
        mDb = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDb.getReference().child("userinfo");

        Query query = ref.orderByChild("mEmailAddress").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object info = dataSnapshot.getValue();
                if (info == null){
                    addUserInfo();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        initView();

        mTvUserId.setText(mUserInfo.getmEmailAddress());
        mNv.setNavigationItemSelectedListener(new NavigationViewItemListener(this));
        mBtnGotoCareService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PetCareActivity.class);
                startActivity(intent);
            }
        });
        mBtnGotoProvideService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PetcareRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addUserInfo(){
        DatabaseReference ref = mDb.getReference();
        ref.child("userinfo").push().setValue(mUserInfo);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case android.R.id.home: // 좌측의 햄버거 메뉴 버튼을 눌렀을 때
                mDl.openDrawer(mNv); //Drawer의 NavigationView가 튀어나오도록 해준다.
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
