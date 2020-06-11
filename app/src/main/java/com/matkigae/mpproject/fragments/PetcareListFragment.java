package com.matkigae.mpproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.matkigae.mpproject.R;
import com.matkigae.mpproject.data.Data;
import com.matkigae.mpproject.data.PetcareInfo;
import com.matkigae.mpproject.listeners.PetcareListViewAdapter;

public class PetcareListFragment extends ListFragment {
    onShopSelectedListener mCallback;
    PetcareListViewAdapter adapter;
    FirebaseDatabase mDb = FirebaseDatabase.getInstance();


    public PetcareListFragment() {
        // Required empty public constructor

    }

    public void setOnShopSelectedListener(onShopSelectedListener listener){
        this.mCallback = listener;
    }


    public interface onShopSelectedListener{
        public void onShopSelected(PetcareInfo item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        adapter = new PetcareListViewAdapter();
        setListAdapter(adapter);
        getDataFromDataBase();


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
    ** 현재 Provider tree에 있는 정보를 전부 가져와서 Adapter에 추가시켜주는 Method
    **/
    DatabaseReference ref = mDb.getReference("providers");
    public void getDataFromDataBase(){
        Query query = ref;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for( DataSnapshot snapshot : dataSnapshot.getChildren()){
                        PetcareInfo data = snapshot.getValue(PetcareInfo.class);
                        addItem(data);
                        adapter.notifyDataSetChanged();
                        setListAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void addItem(PetcareInfo info){
        adapter.addItem(info);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        mCallback.onShopSelected(adapter.getItem(position));
    }
}