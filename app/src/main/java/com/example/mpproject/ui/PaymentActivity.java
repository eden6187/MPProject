package com.example.mpproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.example.mpproject.R;
import com.example.mpproject.billing.BillingManager;
import com.example.mpproject.listeners.NavigationViewItemListener;
import com.google.android.material.navigation.NavigationView;

public class PaymentActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    Button mBtnPayment;
    NavigationView mNv;
    DrawerLayout mDl;
    Toolbar mTb;
    BillingProcessor mBp;

    private BillingManager mBm;

    private void initView(){
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();
        mNv.setNavigationItemSelectedListener(new NavigationViewItemListener(this));
        mBtnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        // 인앱결제용 객체 생성
        mBm = new BillingManager(this);
        mBm.initBillingProcessor();
        mBp = new BillingProcessor(this, null, this);
        mBp.purchase(PaymentActivity.this, "YOUR PRODUCT ID FROM GOOGLE PLAY CONSOLE HERE");
        mBp.subscribe(PaymentActivity.this, "YOUR SUBSCRIPTION ID FROM GOOGLE PLAY CONSOLE HERE");
        mBp.initialize();
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case android.R.id.home: // 좌측의 햄버거 메뉴 버튼을 눌렀을 때
                mDl.openDrawer(mNv); //Drawer의 NavigationView가 튀어나오도록 해준다.
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // IBillingHandler implementation
    @Override
    public void onBillingInitialized() {
        // BillingProcessor가 실행되고 구매 준비가 되면 실행되는 메소드
    }

    @Override
    public void onProductPurchased(String productID, TransactionDetails details) {
        // 요청된 productID가 정상적으로 구매되었을 때 실행되는 메소드
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        // 에러 발생하면 호출되는 메소드
    }

    @Override
    public void onPurchaseHistoryRestored() {
        // 구글 플레이에서 productID들이 로드되고 구매 기록이 복구되었을 때 실행됨
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (!mBp.handleActivityResult(requestCode, resultCode, data)){
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (mBp != null) {
            mBp.release();
        }
        super.onDestroy();
    }


}