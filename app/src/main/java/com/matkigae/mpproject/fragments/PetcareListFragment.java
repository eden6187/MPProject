package com.matkigae.mpproject.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
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
import com.matkigae.mpproject.ui.PetCareActivity;

import java.util.ArrayList;
import java.util.Collections;

public class PetcareListFragment extends ListFragment {
    OnShopSelectedListener mCallback;
    PetcareListViewAdapter adapter;
    FirebaseDatabase mDb = FirebaseDatabase.getInstance();



    public PetcareListFragment(Context context) {
        // Required empty public constructor
        adapter = new PetcareListViewAdapter(context);
    }

    public void setOnShopSelectedListener(OnShopSelectedListener listener){
        this.mCallback = listener;
    }


    public interface OnShopSelectedListener{
        public void onShopSelected(PetcareInfo item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        setListAdapter(adapter);
        initializeDataFromDB();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initializeDataFromDB(){ /** 이상 없이 잘 작동함 **/

        DatabaseReference ref = mDb.getReference().child("providers");
        adapter.clearAll();
        Query query = ref;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot provider : dataSnapshot.getChildren()){
                    PetcareInfo info = provider.getValue(PetcareInfo.class);
                    adapter.addItem(info);
                    adapter.sortByPrice();
                }
                adapter.notifyDataSetChanged();
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