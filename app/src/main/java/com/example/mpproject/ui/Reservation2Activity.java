package com.example.mpproject.ui;

import android.content.DialogInterface;
import android.content.Intent;
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

public class Reservation2Activity extends AppCompatActivity {
    NavigationView mNv;
    DrawerLayout mDl;
    Toolbar mTb;
    Button mBtnReserve;

    private int mReserved_year = 0;
    private int mReserved_month = 0;
    private int mReserved_day = 0;

    private int mReserved_hour = 0;
    private int mReserved_minute = 0;

    private void initView(){
        mTb = findViewById(R.id.toolbar);
        setSupportActionBar(mTb);
        //SetToolbar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //햄버거 메뉴 활성화
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);
        //햄버거 메뉴 아이콘 설정

        mBtnReserve = findViewById(R.id.button_DoReservation);

        mNv = findViewById(R.id.nav_view);
        mDl = findViewById(R.id.drawer_layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        initView();
        mNv.setNavigationItemSelectedListener(new NavigationViewItemListener(this));

        CalendarView cal = (CalendarView)findViewById(R.id.Widget_Select_Date); // 예약 선택 달력에 리스너 달아주기 위한 CalendarView 객체 생성
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() { // 리스너 생성
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                mReserved_year = year;
                mReserved_month = (month + 1);
                mReserved_day = dayOfMonth;
            }
        });

        TimePicker timePicker = (TimePicker)findViewById(R.id.Widget_Select_Time); // 예약 선택 시간에 리스너 달아주기 위한 TimePicker 객체 생성
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() { // 리스너 생성
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mReserved_hour = hourOfDay;
                mReserved_minute = minute;
            }
        });

        mBtnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(v);
            }
        });
    }

    public void open(View w){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this); // 알림창 띄우기위한 객체
        alertDialogBuilder.setMessage(mReserved_year + "년 " + mReserved_month + "월" + mReserved_day + "일, " + mReserved_hour + "시" +
                mReserved_minute + "분에 예약하시겠습니까?"); // 예약 확인 알림창 띄우기
        alertDialogBuilder.setPositiveButton("예약", new DialogInterface.OnClickListener() { // 예약하기 결정되었을 때 이벤트 처리
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 결제하기 창으로 이동하는 이벤트 구현 필요
                Intent intent = new Intent(Reservation2Activity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() { // 예약 취소했을 때 이벤트 처리
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Reservation2Activity.this, "예약이 취소되었습니다.", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

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
