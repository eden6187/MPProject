package com.example.mpproject.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mpproject.R;
import com.example.mpproject.listeners.NavigationViewItemListener;
import com.google.android.material.navigation.NavigationView;

public class PaymentActivity extends AppCompatActivity {
    NavigationView mNv;
    DrawerLayout mDl;
    Toolbar mTb;
    Button mBtnGotoCareService;
    Button mBtnGotoAbandonedDogService;

    private void initView(){
        mTb = findViewById(R.id.toolbar);
        setSupportActionBar(mTb);
        //SetToolbar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //햄버거 메뉴 활성화
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);
        //햄버거 메뉴 아이콘 설정

        mNv = findViewById(R.id.nav_view);
        mDl = findViewById(R.id.drawer_layout);
        mBtnGotoAbandonedDogService = findViewById(R.id.button_main_gotoabandoneddog);
        mBtnGotoCareService = findViewById(R.id.button_main_gotocareactivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mNv.setNavigationItemSelectedListener(new NavigationViewItemListener(this));
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
