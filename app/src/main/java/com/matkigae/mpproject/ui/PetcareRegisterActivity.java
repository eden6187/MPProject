package com.matkigae.mpproject.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.matkigae.mpproject.R;
import com.matkigae.mpproject.data.MatchingInfo;
import com.matkigae.mpproject.data.PetcareInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.matkigae.mpproject.data.UserInfo;
import com.matkigae.mpproject.listeners.PetcareRegisterRecyclerViewAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class PetcareRegisterActivity extends AppCompatActivity {
    FirebaseDatabase mDb = FirebaseDatabase.getInstance();
    Button mBtnRegister;
    EditText mEtPrice;
    EditText mEtPetcareInfo;
    EditText mEtPetcareIntro;
    EditText mEtPetcareTitle;
    EditText mEtPetcareAddress;
    ActionBar mActionbar;
    ToggleButton[] toggleButtons= new ToggleButton[7];
    PetcareRegisterRecyclerViewAdapter mPetcareRegisterRecyclerViewAdapter;
    Button mBtnCheckAddress;
    String mAddress;

    private View mLayout;
    LatLng mAddressLatLng;
    private boolean mCheckAddress = false;


    private void initView(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setHomeButtonEnabled(true);

        mBtnRegister = findViewById(R.id.button_regsiter_registertodb);
        mEtPetcareInfo = findViewById(R.id.edittext_register_petcareinfo);
        mEtPetcareIntro = findViewById(R.id.edittext_register_petcareinfointro);
        mEtPetcareTitle = findViewById(R.id.edittext_register_petcareinfotitle);
        mEtPrice = findViewById(R.id.edittext_register_price);
        mEtPetcareAddress = findViewById(R.id.edittext_register_address);
        toggleButtons[0] = findViewById(R.id.togglebutton_register_mon);
        toggleButtons[1] = findViewById(R.id.togglebutton_register_tue);
        toggleButtons[2] = findViewById(R.id.togglebutton_register_wend);
        toggleButtons[3] = findViewById(R.id.togglebutton_register_thur);
        toggleButtons[4] = findViewById(R.id.togglebutton_register_fri);
        toggleButtons[5] = findViewById(R.id.togglebutton_register_sat);
        toggleButtons[6] = findViewById(R.id.togglebutton_register_sun);

        mBtnCheckAddress = findViewById(R.id.button_register_check_address);
        mLayout = findViewById(R.id.petcare_register_layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petcare_register);


        initView();
        mBtnCheckAddress.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mAddressLatLng = getLastLocation();
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mCheckAddress){
                    popupMessage(mCheckAddress);
                }else{
                    String availableDate = "";
                    for(ToggleButton button : toggleButtons){
                        if(button.isChecked()){ availableDate = availableDate + "1"; }
                        else{ availableDate = availableDate + "0"; }
                    }

                    String petCareTitle = mEtPetcareTitle.getText().toString();
                    String petCareInfo = mEtPetcareInfo.getText().toString();
                    String petCareIntro = mEtPetcareIntro.getText().toString();
                    String petCarePrice = mEtPrice.getText().toString();


                    PetcareInfo newInfo = new PetcareInfo();
                    newInfo.setmUserId(FirebaseAuth.getInstance().getUid());
                    newInfo.setmAvailableDate(availableDate);
                    newInfo.setmPetcareInfo(petCareInfo);
                    newInfo.setmPetcareIntro(petCareIntro);
                    newInfo.setmPetcareTitle(petCareTitle);
                    newInfo.setmPrice(petCarePrice);
                    newInfo.setmXcoordinate(mAddressLatLng.latitude);
                    newInfo.setmYcoordinate(mAddressLatLng.longitude);

                    registerPetCareInfo(newInfo);
                }
            }
        });
    }
    private void registerPetCareInfo(PetcareInfo newInfo){
        DatabaseReference ref = mDb.getReference().child("providers");
        ref.child(newInfo.getmPetcareTitle()).setValue(newInfo);
    }

    public LatLng getLastLocation(){
        final Geocoder geocoder = new Geocoder(this);
        String str = mEtPetcareAddress.getText().toString();
        List<Address> list = null;
        Double mLat = 0.0;
        Double mLng = 0.0;
        LatLng mLatLng = null;
        try{
            list = geocoder.getFromLocationName(str, 10);
        }catch(IOException io){
            io.printStackTrace();
        }
        if(list!=null){
            if(list.size()==0){
                mCheckAddress = false;
                popupMessage(mCheckAddress);
            }
            else{
                mLat = list.get(0).getLatitude();
                mLng = list.get(0).getLongitude();
                mLatLng = new LatLng(mLat, mLng);
                mCheckAddress = true;
                popupMessage(mCheckAddress);
                mAddress = list.get(0).getAddressLine(0);
            }
        }
        return mLatLng;
    }

    void popupMessage(boolean check){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(check){
            builder.setMessage("확인되었습니다.");
        }else{
            builder.setMessage("주소가 올바르지 않습니다.");
        }
        builder.setNeutralButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }

    public String getAddress(){
        return mAddress;
    }
    public LatLng getAddressLatLng(){
        return mAddressLatLng;
    }

}