package com.matkigae.mpproject.ui;

import android.os.Bundle;

import com.matkigae.mpproject.R;
import com.matkigae.mpproject.data.PetcareInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;


public class PetcareRegisterActivity extends AppCompatActivity {
    FirebaseDatabase mDb = FirebaseDatabase.getInstance();
    DatabaseReference ref = mDb.getReference().child("providers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pecare_register);

        Button btn = findViewById(R.id.button_register);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PetcareInfo newInfo = new PetcareInfo(R.drawable.ic_shopinfo_default,R.drawable.star_image,"SHOP1","0","0","0");
                registerNewPetcareProvider(newInfo);
            }
        });
    }

    public void registerNewPetcareProvider(PetcareInfo info){
        ref.push().setValue(info);
    }

}
