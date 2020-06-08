package com.matkigae.mpproject.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.matkigae.mpproject.data.PetcareInfo;
import com.matkigae.mpproject.fragments.PetcareListFragment;
import com.matkigae.mpproject.fragments.PetcareinfoFragment;
import com.matkigae.mpproject.R;
import com.matkigae.mpproject.listeners.NavigationViewItemListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PetCareActivity extends AppCompatActivity implements PetcareListFragment.OnShopSelectedListener,
        OnMapReadyCallback {

    public static final int NEAREST = 1;
    FirebaseDatabase mDb = FirebaseDatabase.getInstance();
    DatabaseReference ref = mDb.getReference("providers");
    PetcareListFragment mPetcareListFragment = new PetcareListFragment();
    SupportMapFragment mMapFragment;
    Button mBtnShowList;
    Button mBtnShowMap;
    NavigationView mNv;
    DrawerLayout mDl;
    Toolbar mTb;
    GoogleMap mMap;

    public void initView(){
        mTb = findViewById(R.id.toolbar);
        setSupportActionBar(mTb);
        //SetToolbar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //햄버거 메뉴 활성화
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);
        //햄버거 메뉴 아이콘 설정

        mNv = findViewById(R.id.nav_view);
        mDl = findViewById(R.id.drawer_layout);
        mBtnShowList = findViewById(R.id.button_petcare_showlist);
        mBtnShowMap = findViewById(R.id.button_petcare_showmap);

    }

    public void getDataFromDataBase(int criteria){
        switch (criteria) {
            case NEAREST:
                for(int i = 0 ; i < 1; i++) {
                    Query query = ref.limitToFirst(10);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // 여기서 DataSnapshot을 한번에 여러개 가져오는 방법 없을까요
//                            PetcareInfo infos = (PetcareInfo) dataSnapshot.getValue(PetcareInfo.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    break;
                }
        }

        return;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petcare);

        mMapFragment = SupportMapFragment.newInstance();
        mMapFragment.getMapAsync(this);

        initView();
        mNv.setNavigationItemSelectedListener(new NavigationViewItemListener(this));

        if(findViewById(R.id.petcare_fragment_container)!=null){
            mPetcareListFragment = new PetcareListFragment();
            mPetcareListFragment.setOnShopSelectedListener(this);
            replaceFragment(mPetcareListFragment);
        }

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

        getDataFromDataBase(NEAREST);
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.petcare_fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

    }


    public void onShopSelected(int position){

        if(findViewById(R.id.petcare_fragment_container)!=null){
            PetcareinfoFragment newFragment = new PetcareinfoFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.petcare_fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else{
            PetcareinfoFragment newFragment = new PetcareinfoFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.petcare_fragment_container, newFragment);
            newFragment.updateShopinfoView(position);
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
