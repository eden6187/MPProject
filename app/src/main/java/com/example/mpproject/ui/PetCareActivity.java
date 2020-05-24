package com.example.mpproject.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mpproject.Fragments.PetcareListFragment;
import com.example.mpproject.Fragments.ShopinfoFragment;
import com.example.mpproject.R;

public class PetCareActivity extends AppCompatActivity implements PetcareListFragment.onShopSelectedListener{
    PetcareListFragment mPetcareListFragment = new PetcareListFragment();

    public void initView(){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petcare);

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
