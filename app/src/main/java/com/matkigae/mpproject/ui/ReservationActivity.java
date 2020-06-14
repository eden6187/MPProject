package com.matkigae.mpproject.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.matkigae.mpproject.R;
import com.matkigae.mpproject.data.PetcareInfo;
import com.matkigae.mpproject.listeners.NavigationViewItemListener;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReservationActivity extends AppCompatActivity {
    NavigationView mNv;
    DrawerLayout mDl;
    Toolbar mTb;
    Button mBtnReserve;
    EditText mtextViewIfAvailable;
    TextView mETAvailableWeek;
    PetcareInfo mPetcareInfo;

    String mAvailableWeek;
    public static Context context_reservation; // context 변수 선언

    private int mReserved_year = 0;
    private int mReserved_month = 0;
    private int mReserved_day = 0;
    private int mReserverd_weekDay;
    private int mReserved_hour = 0;
    private int mReserved_minute = 0;


    private int mCurr_year = 0;
    private int mCurr_month = 0;
    private int mCurr_day = 0;

    private int mCurr_hour = 0;
    private int mCurr_minute = 0;

    private boolean availableTime;
    String mSelectedTime;
    ArrayList<String> mWeekDayArray = new ArrayList<String>();

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

        mtextViewIfAvailable = findViewById(R.id.Text_Reservation_IfAvailable);
        mETAvailableWeek = findViewById(R.id.edittext_weekAvailable);

        Calendar cal = Calendar.getInstance();
        mReserved_minute = cal.get(Calendar.MINUTE);
        mCurr_minute = cal.get(Calendar.MINUTE);
        //24 hour format
        mReserved_hour = cal.get(Calendar.HOUR_OF_DAY);
        mCurr_hour = cal.get(Calendar.HOUR_OF_DAY);

        mReserved_day = cal.get(Calendar.DAY_OF_MONTH);
        mReserved_year = cal.get(Calendar.YEAR);
        mReserved_month = cal.get(Calendar.MONTH);
        mReserverd_weekDay = cal.get(Calendar.DAY_OF_WEEK);
        mCurr_day = cal.get(Calendar.DAY_OF_MONTH);
        mCurr_year = cal.get(Calendar.YEAR);
        mCurr_month = cal.get(Calendar.MONTH);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        initView();
        Intent intent = getIntent();
        mPetcareInfo = (PetcareInfo) intent.getParcelableExtra("petcareinfo");
        mAvailableWeek = mPetcareInfo.getmAvailableDate();
        System.out.println("파이어베이스로부터 데이터가 제대로 들어오는지 확인 : " + mAvailableWeek);

        context_reservation = this;

        mNv.setNavigationItemSelectedListener(new NavigationViewItemListener(this));
        // 불가 요일 받아와서 보여주기.

        String[] weekDay = {"일", "월", "화", "수", "목", "금", "토"};
        String totalWeekDay = "";

        for (int i=0; i<mAvailableWeek.length(); i++){
            if (mAvailableWeek.charAt(i) == '1'){
               totalWeekDay = totalWeekDay + " " + weekDay[i];
               System.out.println("예약 가능 요일 책정 : " + weekDay[i]);
               mWeekDayArray.add(weekDay[i]);
            }
        }
        System.out.println("요일 구분 제대로 되는지 확인 : " + totalWeekDay);
        mETAvailableWeek.setText(totalWeekDay+"요일에만 예약이 가능합니다.");
        mETAvailableWeek.invalidate();

        final CalendarView cal = (CalendarView)findViewById(R.id.Widget_Select_Date); // 예약 선택 달력에 리스너 달아주기 위한 CalendarView 객체 생성
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() { // 리스너 생성
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                mReserved_year = year;
                mReserved_month = (month);
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
                try {

                    mSelectedTime = String.format("%04d%02d%02d%02d%02d", mReserved_year, mReserved_month+1,
                            mReserved_day, mReserved_hour, mReserved_minute);
                    Date currdate = Calendar.getInstance().getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                    String now = dateFormat.format(currdate);
                    int compare = now.compareTo(mSelectedTime);

                    // 요일 구분
                    Date nDate = dateFormat.parse(mSelectedTime);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(nDate);
                    int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
                    // 1~7 : 일월화수목금토
                    System.out.println("제대로 요일이 나오는가?? : " + dayNum);
                    String day = "";
                    switch(dayNum){
                        case 1:
                            day = "일";
                            break ;
                        case 2:
                            day = "월";
                            break ;
                        case 3:
                            day = "화";
                            break ;
                        case 4:
                            day = "수";
                            break ;
                        case 5:
                            day = "목";
                            break ;
                        case 6:
                            day = "금";
                            break ;
                        case 7:
                            day = "토";
                            break ;
                    }
                    if (mWeekDayArray.indexOf(day) < 0){
                        compare = 1;
                    };

//                    Log.d("TEST", "compare 값 : " + compare + "선택 값 : " + mSelectedTime + "오늘 날짜 : " + now);
                    if (compare <= 0){
                        availableTime = true;
                    } else {
                        availableTime = false;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                open(v);
            }
        });
    }

    public void open(View w){
        if (availableTime != true) {
            mtextViewIfAvailable.setText("예약이 불가능한 시간입니다.");
            mtextViewIfAvailable.invalidate();
        } else {
            mtextViewIfAvailable.setText("예약 가능한 시간입니다!");
            mtextViewIfAvailable.invalidate();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this); // 알림창 띄우기위한 객체
            alertDialogBuilder.setMessage(mReserved_year + "년 " + (mReserved_month+1) + "월" + mReserved_day + "일, " + mReserved_hour + "시" +
                    mReserved_minute + "분을 시작 시간으로 하시겠습니까?"); // 예약 확인 알림창 띄우기
            alertDialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() { // 예약하기 결정되었을 때 이벤트 처리
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 다음 예약 창으로 이동하는 이벤트 구현
                    Intent intent = new Intent(ReservationActivity.this, Reservation2Activity.class);
                    intent.putExtra("petcareinfo", mPetcareInfo);
                    intent.putExtra("startdate", mSelectedTime);
                    startActivity(intent);
                }
            });

            alertDialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() { // 예약 취소했을 때 이벤트 처리
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ReservationActivity.this, "시간 설정이 취소되었습니다.", Toast.LENGTH_LONG).show();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


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