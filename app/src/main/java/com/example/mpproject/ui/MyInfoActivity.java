package com.example.mpproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mpproject.R;

public class MyInfoActivity extends AppCompatActivity {
    Toolbar mTb;

    private void initView(){
        mTb = findViewById(R.id.toolbar_myinfo);
        setSupportActionBar(mTb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        initView();
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
}