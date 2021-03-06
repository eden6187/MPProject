package com.matkigae.mpproject.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.matkigae.mpproject.data.PetcareInfo;
import com.matkigae.mpproject.fragments.PetcareListFragment;
import com.matkigae.mpproject.R;
import com.matkigae.mpproject.listeners.NavigationViewItemListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PetCareActivity extends AppCompatActivity implements PetcareListFragment.OnShopSelectedListener,
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener
{
    PetcareListFragment mPetcareListFragment = new PetcareListFragment(this);
    SupportMapFragment mMapFragment;
    Button mBtnShowList;
    Button mBtnShowMap;
    NavigationView mNv;
    DrawerLayout mDl;
    Toolbar mTb;
    GoogleMap mMap;

    private static final String tag = "googlemap_location";

    ArrayList<PetcareInfo> mInfos = new ArrayList<PetcareInfo>();

    Context mContext = this;
    TextView title;

    private Marker currentMarker = null;


    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    boolean needRequest = false;

    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    Location mCurrentLocation;
    LatLng mCurrentPosition;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;

    private View mLayout;

    private boolean mMapStarted = false;

    private static final String TAG = "googlemap_example";


    public void initView(){
        mTb = findViewById(R.id.toolbar);
        setSupportActionBar(mTb);
        //SetToolbar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //햄버거 메뉴 활성화
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);
        //햄버거 메뉴 아이콘 설정

        mNv = findViewById(R.id.nav_view);
        mDl = findViewById(R.id.petcare_layout);
        mBtnShowList = findViewById(R.id.button_petcare_showlist);
        mBtnShowMap = findViewById(R.id.button_petcare_showmap);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petcare);
//        checkRunTimePermission();

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }

        initView();
        mNv.setNavigationItemSelectedListener(new NavigationViewItemListener(this));
        mPetcareListFragment = new PetcareListFragment(this );
        mPetcareListFragment.setOnShopSelectedListener(this);
        replaceFragment(mPetcareListFragment);

        mBtnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(mMapFragment);
            }
        });

        mBtnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(mPetcareListFragment);
            }
        });

        initializeDataFromDB();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mLayout = findViewById(R.id.petcare_layout);
        locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(UPDATE_INTERVAL_MS).setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mMapFragment = SupportMapFragment.newInstance();
        mMapFragment.getMapAsync(this);

    }

    public void replaceFragment(Fragment fragment){
        mMapStarted = false;
        if(fragment.equals(mMapFragment)){
            locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(UPDATE_INTERVAL_MS).setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(locationRequest);
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mMapFragment = SupportMapFragment.newInstance();
            mMapFragment.getMapAsync(this);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.petcare_fragment_container, fragment);
        transaction.commit();
    }

    public void onShopSelected(int position){

    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case android.R.id.home: // 좌측의 햄버거 메뉴 버튼을 눌렀을 때
                mDl.openDrawer(mNv); //Drawer의 NavigationView가 튀어나오도록 해준다.
                break;
        }
        return true;
    }


    private void initializeDataFromDB(){ /** 이상 없이 잘 작동함 **/
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("providers");
        Query query = ref;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    mInfos.add(data.getValue(PetcareInfo.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PetCareActivity.this,"서버에 문제가 발생하였습니다.",Toast.LENGTH_LONG).show();
            }
        });
    }

    /*** 지도 관련 함수들 ***/

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        setDefaultLocation();

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if(hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED){
            //퍼미션 확인
            startLocationUpdates();
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])){ //사용자가 거부한 적이 있는 경우
                Snackbar.make(mLayout, "이 화면을 실행하려면 위치 접근 권한이 필요합니다.", Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(PetCareActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                    }
                }).show();
            }else{// 처음 권한 요청을 할 경우
                ActivityCompat.requestPermissions(PetCareActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        setShopMarkers(mInfos);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng currentLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
                mMap.animateCamera(cameraUpdate);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                return false;
            }
        });

        mMap.setOnMarkerClickListener(this);
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size()-1);
                mCurrentPosition = new LatLng(location.getAltitude(), location.getLongitude());

                setCurrentLocation(location);
                mCurrentLocation = location;

                Log.e(tag, "GPSlocation:"+location.getLatitude()+"  "+location.getLongitude());
            }
        }
    };

    private void startLocationUpdates(){
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {

            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);

            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                    hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED   ) {

                return;
            }

            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

            if (checkPermission())
                mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mFusedLocationClient!=null){
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(mFusedLocationClient!=null){
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mFusedLocationClient!=null){
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFusedLocationClient!=null){
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void setCurrentLocation(Location location) {

        if (currentMarker != null) currentMarker.remove();

        if(mMapStarted == false) {

            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
            mMap.moveCamera(cameraUpdate);
        }
        mMapStarted = true;
    }

    public void setDefaultLocation() {

        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(37.28346, 127.0465);
        String markerTitle = "위치정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요";

        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mMap.moveCamera(cameraUpdate);
    }

    /*** 아래부턴 런타임 퍼미션 처리를 위한 메소드. ***/

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(PetCareActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(PetCareActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(PetCareActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(PetCareActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(PetCareActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(PetCareActivity.this, REQUIRED_PERMISSIONS,
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

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                // 퍼미션을 허용했다면 위치 업데이트를 시작합니다.
                startLocationUpdates();
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {


                    // 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();

                }else {


                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();
                }
            }
        }
    }

    /*** 아래부턴 GPS 활성화를 위한 메소드들. ***/

    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PetCareActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정해주시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        needRequest = true;
                        return;
                    }
                }
                break;
        }
    }

    /*** Marker 생성 및 설정 ***/

    public void setShopMarkers(ArrayList<PetcareInfo> Info){
        for(int i=0;i<Info.size();i++){
            MarkerOptions markerOptions = new MarkerOptions();
            LatLng mLocation = new LatLng(Info.get(i).getmXcoordinate(), Info.get(i).getmYcoordinate());
            markerOptions.position(mLocation);
            markerOptions.title(Info.get(i).getmPetcareTitle());
            markerOptions.draggable(true);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(markerOptions);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for(int i=0;i<mInfos.size();i++){
            if(marker.getTitle().equals(mInfos.get(i).getmPetcareTitle())){
                Intent intent = new Intent(this, PetcareinfoActivity.class);
                intent.putExtra("petcareinfo", mInfos.get(i));
                startActivity(intent);
            }
        }

        return false;
    }

    @Override
    public void onShopSelected(PetcareInfo item) {
        Intent intent = new Intent(this,PetcareinfoActivity.class);
        intent.putExtra("petcareinfo",item);
        startActivity(intent);
    }
}
