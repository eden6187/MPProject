package com.example.mpproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mpproject.Fragments.PetcareListFragment;
import com.example.mpproject.Fragments.ShopinfoFragment;
import com.example.mpproject.R;
import com.example.mpproject.listeners.NavigationViewItemListener;
import com.google.android.material.navigation.NavigationView;

public class PetCareActivity extends AppCompatActivity implements PetcareListFragment.onShopSelectedListener{
    PetcareListFragment mPetcareListFragment = new PetcareListFragment();
    NavigationView mNv;
    DrawerLayout mDl;
    Toolbar mTb;

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

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petcare);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.petcare_mode, menu);
        return true;
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

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case R.id.petcare_option_list_mode:
                break;

            case R.id.petcare_option_map_mode:
                Intent intent_map = new Intent(PetCareActivity.this, MapActivity.class);
                startActivity(intent_map);
                break;

            case android.R.id.home: // 좌측의 햄버거 메뉴 버튼을 눌렀을 때
                mDl.openDrawer(mNv); //Drawer의 NavigationView가 튀어나오도록 해준다.
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
