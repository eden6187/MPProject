package com.matkigae.mpproject.ui;

import android.os.Bundle;

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
import com.matkigae.mpproject.listeners.RvAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class PetcareRegisterActivity extends AppCompatActivity {
    FirebaseDatabase mDb = FirebaseDatabase.getInstance();
    EditText mEtShopId;
    Switch mSwtichGPS;
    Spinner mSpinnerServiceType;
    RecyclerView mRv;
    RvAdapter mRvAdapter;

    private void initView(){
        mEtShopId = findViewById(R.id.edittext_register_servicename);
        mSwtichGPS = findViewById(R.id.switch_register_gpspermission);
        mSpinnerServiceType = findViewById(R.id.spinner_regsiter_servicetype);
        mRv = findViewById(R.id.recyclerview_petcare_register);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petcare_register);
        initView();

        ArrayList<MatchingInfo> dummy_data = new ArrayList<MatchingInfo>();

        dummy_data.add(new MatchingInfo("prd1","b"));
        dummy_data.add(new MatchingInfo("prd2","b"));
        dummy_data.add(new MatchingInfo("prd3","b"));

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        mRvAdapter = new RvAdapter(dummy_data);
        mRv.setAdapter(mRvAdapter);
        mRv.setLayoutManager(layoutManager);

        Button btn = findViewById(R.id.button_regsiter_registertodb);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shopId = mEtShopId.getText().toString();
                int shopImage = R.drawable.ic_shopinfo_default;
                int cooridnate = 0;

                PetcareInfo newInfo = new PetcareInfo(R.drawable.ic_shopinfo_default,shopId,0,0,0,0, UserInfo.getInstance().getmEmailAddress().toString());
                registerNewPetcareProvider(newInfo);
            }
        });

        DatabaseReference mRef_matching = mDb.getReference().child("matching");
        mRef_matching.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    MatchingInfo candidate = snapshot.getValue(MatchingInfo.class);
                    if(candidate.getProviderId().equals(UserInfo.getInstance().getmEmailAddress())){
                        mRvAdapter.addItem(candidate);
                    }
                }
                mRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference ref_matching = mDb.getReference().child("matching");
        ref_matching.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                dataSnapshot.getValue().toString();
//                mRvAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG,"Succees");
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG,"Succees");
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG,"Succees");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG,"Succees");
            }
        });
    }

    DatabaseReference ref = mDb.getReference().child("providers");
    public void registerNewPetcareProvider(PetcareInfo info){
        final PetcareInfo newInfo = info;
        Query query = ref.orderByChild("mUserId").equalTo(info.getmUserId());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(PetcareRegisterActivity.this,"현재는 하나의 Service만 제공하실 수 있습니다.",Toast.LENGTH_LONG).show();
                }else{
                    ref.push().setValue(newInfo);
                    Toast.makeText(PetcareRegisterActivity.this, " 등록이 완료 되었습니다.",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

}
