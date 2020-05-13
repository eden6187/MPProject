package com.example.mpproject.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.mpproject.Fragments.PetcareListFragment;
import com.example.mpproject.R;

public class PetCareActivity extends AppCompatActivity {

    public void initView(){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petcare);

        if(findViewById(R.id.petcare_fragment_container)!=null){
            PetcareListFragment mPetcareListFragment = new PetcareListFragment();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.petcare_fragment_container, mPetcareListFragment).commit();
            if(savedInstanceState != null){
                return;
            }
        }

    }
}
