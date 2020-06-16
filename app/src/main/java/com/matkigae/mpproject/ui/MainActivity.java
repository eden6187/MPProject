package com.matkigae.mpproject.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    NavigationView mNv;
    DrawerLayout mDl;
    Toolbar mTb;
    Button mBtnGotoCareService;
    Button mBtnGotoProvideService;
    TextView mTvUserId;
    View mNavHeaderView;
    UserInfo mUserInfo;
    FirebaseDatabase mDb;
    private View mLayout;

    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    private void initView(){
        mTb = findViewById(R.id.toolbar);
        setSupportActionBar(mTb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);

        mNv = findViewById(R.id.nav_view);
        mTvUserId = mNv.findViewById(R.id.textview_navheader_userid);
        mDl = findViewById(R.id.main_layout);
        mNavHeaderView = mNv.getHeaderView(0);
        mTvUserId = mNavHeaderView.findViewById(R.id.textview_navheader_userid);
        mBtnGotoProvideService = findViewById(R.id.button_main_gotoprovideservice);
        mBtnGotoCareService = (Button)findViewById(R.id.button_main_gotocareactivity);
        mLayout = findViewById(R.id.main_layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkRunTimePermission();

//        // 위치 권한
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                        PERMISSIONS_REQUEST_CODE);
//            }
//        }


        // User 객체 Singleton 으로 생성
        mUserInfo = UserInfo.getInstance();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            mUserInfo.setEmailAddress(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }

        //DataBase 초기화 User 정보 초기화 및 기존 사용자에 대한 정보는 추가하지 않도록 Query 작성
        mDb = FirebaseDatabase.getInstance();
        final DatabaseReference ref = mDb.getReference();
        HashMap<String,Object> post = new HashMap<String, Object>();
        post.put("/user_list/" + FirebaseAuth.getInstance().getUid(),UserInfo.toMap());
        ref.updateChildren(post);

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


    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case android.R.id.home: // 좌측의 햄버거 메뉴 버튼을 눌렀을 때
                mDl.openDrawer(mNv); //Drawer의 NavigationView가 튀어나오도록 해준다.
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Snackbar.make(mLayout, "위치 사용이 승인되었습니다",
                        Snackbar.LENGTH_SHORT)
                        .show();
            } else {
                // Permission request was denied.
                Snackbar.make(mLayout,"위치 사용",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /*** 아래부턴 런타임 퍼미션 처리를 위한 메소드. ***/

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    private boolean checkPermission() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED;
    }



}
