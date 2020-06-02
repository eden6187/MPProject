package com.example.mpproject.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mpproject.Fragments.PetcareListFragment;
import com.example.mpproject.Fragments.PetcareMapFragment;
import com.example.mpproject.Fragments.ShopinfoFragment;
import com.example.mpproject.R;
import com.example.mpproject.listeners.NavigationViewItemListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

public class PetCareActivity extends AppCompatActivity implements PetcareListFragment.onShopSelectedListener,
        OnMapReadyCallback
{
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
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.petcare_fragment_container, mPetcareListFragment).commit();
            if(savedInstanceState != null){
                return;
            }
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
                replaceFragment(new PetcareListFragment());
            }
        });
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

    public void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.petcare_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onShopSelected(int position){

        if(findViewById(R.id.petcare_fragment_container)!=null){
            ShopinfoFragment newFragment = new ShopinfoFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.petcare_fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else{
            ShopinfoFragment newFragment = new ShopinfoFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.petcare_fragment_container, newFragment);
            newFragment.updateShopinfoView(position);
        }
    }

}
