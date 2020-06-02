package com.example.mpproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mpproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyInfoActivity extends AppCompatActivity {
    Toolbar mTb;
    private FirebaseAuth mAuth;
    TextView mTvUserEmailAddress;
    Button mBtnLogout;
    Button mBtnWithdraw;


    private void initView(){
        mTb = findViewById(R.id.toolbar_myinfo);
        setSupportActionBar(mTb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBtnLogout = findViewById(R.id.button_myinfo_logout);
        mBtnWithdraw = findViewById(R.id.button_myinfo_withdrawal);
        mTvUserEmailAddress = findViewById(R.id.textview_loginactivity_useremail);
        mTvUserEmailAddress.setText(mAuth.getCurrentUser().getEmail());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        mAuth = FirebaseAuth.getInstance();

        initView();

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
