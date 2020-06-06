package com.matkigae.mpproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.matkigae.mpproject.R;
import com.matkigae.mpproject.billing.BillingManager;
import com.matkigae.mpproject.listeners.NavigationViewItemListener;
import com.google.android.material.navigation.NavigationView;

public class PaymentActivity extends AppCompatActivity{
    NavigationView mNv;
    DrawerLayout mDl;
    Toolbar mTb;

    Button mBtnPay;

    private BillingProcessor mBp;
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

        mBtnPay = findViewById(R.id.button_payment_confirmPayment);

        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 인앱결제용 객체 생성
                mBm = new BillingManager(PaymentActivity.this);
                mBm.initBillingProcessor();
                mBp = mBm.getBillingProcessor();
                purchaseProduct();

            }
        });


//        mBp = new BillingProcessor(this, null, this);
//        mBp.purchase(PaymentActivity.this, "YOUR PRODUCT ID FROM GOOGLE PLAY CONSOLE HERE");
//        mBp.subscribe(PaymentActivity.this, "YOUR SUBSCRIPTION ID FROM GOOGLE PLAY CONSOLE HERE");
//        mBp.initialize();
    }

    private void purchaseProduct() {
        mBm.purchaseProduct();
    }

    protected void onActivityresult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (mBp.handleActivityResult(requestCode, resultCode, data)){
            if (resultCode == RESULT_OK) {
                // 구매 성공 시의 처리
                Toast.makeText(PaymentActivity.this, "결제 성공", Toast.LENGTH_LONG).show();
            }
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBm.releaseBillingProcessor();
    }
}
