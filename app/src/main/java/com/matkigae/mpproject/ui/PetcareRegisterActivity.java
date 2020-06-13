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
import com.matkigae.mpproject.listeners.PetcareRegisterRecyclerViewAdapter;

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

import java.util.ArrayList;
import java.util.HashMap;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class PetcareRegisterActivity extends AppCompatActivity {
    FirebaseDatabase mDb = FirebaseDatabase.getInstance();
    EditText mEtShopId;
    Switch mSwtichGPS;
    Spinner mSpinnerServiceType;
    RecyclerView mRv;
    PetcareRegisterRecyclerViewAdapter mPetcareRegisterRecyclerViewAdapter;

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

//        dummy_data.add(new MatchingInfo("prd1","b"));
//        dummy_data.add(new MatchingInfo("prd2","b"));
//        dummy_data.add(new MatchingInfo("prd3","b"));

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        mPetcareRegisterRecyclerViewAdapter = new PetcareRegisterRecyclerViewAdapter(dummy_data);
        mRv.setAdapter(mPetcareRegisterRecyclerViewAdapter);
        mRv.setLayoutManager(layoutManager);

        Button btn = findViewById(R.id.button_regsiter_registertodb);

        Query query = mDb.getReference().child("matching");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    MatchingInfo info = data.getValue(MatchingInfo.class);
                    if (info != null) {
                        if (info.getProviderTitle().equals(UserInfo.getMyProviderId())) {
                            mPetcareRegisterRecyclerViewAdapter.addItem(info);
                        }
                    }
                }
                mPetcareRegisterRecyclerViewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shopId = mEtShopId.getText().toString();
                int shopImage = R.drawable.ic_shopinfo_default;
                /** 여기다 해당 상점에 대한 정보를 얻어 오는 코드를 넣어주면 된다. **/

                PetcareInfo newInfo = new PetcareInfo(R.drawable.ic_shopinfo_default,shopId,1.1,0,1.1,1.1, UserInfo.getInstance().getmEmailAddress().toString());
                DatabaseReference ref = mDb.getReference();
                HashMap<String,Object> post = new HashMap<String,Object>();
                post.put("/providers/" + newInfo.getmPetcareTitle(),newInfo.toMap());
                ref.updateChildren(post);

                /** Toast Message 띄어 줄 것 **/
            }
        });



        /** matching 정보 새로 발생 시 Adapter 에 ITEM 추**/
        DatabaseReference ref_matching = mDb.getReference().child("matching");
        ref_matching.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.d("data:", data.getValue().toString());
//                    MatchingInfo info = data.getValue(MatchingInfo.class);
//                    if (info != null) {
//                        if (info.getProviderTitle().equals(UserInfo.getMyProviderId())) {
//                            mPetcareRegisterRecyclerViewAdapter.addItem(info);
//                        }
//                    }
                }
                mPetcareRegisterRecyclerViewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }


}
