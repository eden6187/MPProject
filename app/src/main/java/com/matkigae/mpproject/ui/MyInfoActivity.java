package com.matkigae.mpproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.matkigae.mpproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.matkigae.mpproject.data.MatchingInfo;
import com.matkigae.mpproject.listeners.PetcareRegisterRecyclerViewAdapter;

import java.util.ArrayList;

public class MyInfoActivity extends AppCompatActivity {
    Toolbar mTb;
    private FirebaseAuth mAuth;
    TextView mTvUserEmailAddress;
    Button mBtnLogout;
    Button mBtnWithdraw;
    RecyclerView mRv;
    PetcareRegisterRecyclerViewAdapter mRvAdapter;
    ArrayList<MatchingInfo> mRequests = new ArrayList<MatchingInfo>();


    private void initView(){
        mTb = findViewById(R.id.toolbar_myinfo);
        setSupportActionBar(mTb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRv = findViewById(R.id.recyclerview_myinfo);

        mBtnLogout = findViewById(R.id.button_myinfo_logout);
        mBtnWithdraw = findViewById(R.id.button_myinfo_withdrawal);
        mTvUserEmailAddress = findViewById(R.id.textview_loginactivity_useremail);
        mTvUserEmailAddress.setText(mAuth.getCurrentUser().getEmail());
    }

    public void initializeData(){
        Query query = FirebaseDatabase.getInstance().getReference().child("matchinginfo");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MatchingInfo candidate = snapshot.getValue(MatchingInfo.class);
                    if(candidate.getInfo().getmUserId().equals(FirebaseAuth.getInstance().getUid())){
                        Log.d("have","have");
                        mRvAdapter.addItem(candidate);
                    }
                }
                mRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        mAuth = FirebaseAuth.getInstance();
        mRvAdapter = new PetcareRegisterRecyclerViewAdapter(mRequests);
        initView();
        mRv.setAdapter(mRvAdapter);
        initializeData();

        mRv.setLayoutManager(new LinearLayoutManager(this));

        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singOut();
                finishAffinity();
            }
        });

        mBtnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revokeAccess();;
                finishAffinity();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home: // 백버튼이 눌렸을 때
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void singOut(){
        FirebaseAuth.getInstance().signOut();
    }

    public void revokeAccess(){
        mAuth.getCurrentUser().delete();
    }
}
