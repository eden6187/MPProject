package com.example.mpproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mpproject.data.Data;
import com.example.mpproject.listeners.PetcareListViewAdapter;
import com.google.firebase.database.FirebaseDatabase;

public class PetcareListFragment extends ListFragment {
    OnShopSelectedListener mCallback;
    PetcareListViewAdapter mAdapter;
    FirebaseDatabase mDb = FirebaseDatabase.getInstance();

    public PetcareListFragment() {
        // Required empty public constructor
    }

    public void setOnShopSelectedListener(OnShopSelectedListener listener){
        this.mCallback = listener;
    }

    public interface OnShopSelectedListener {
        public void onShopSelected(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAdapter = new PetcareListViewAdapter();
        setListAdapter(mAdapter);

        mAdapter.addItem(Data.petcareInfos[0]);
        mAdapter.addItem(Data.petcareInfos[1]);
        mAdapter.addItem(Data.petcareInfos[2]);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        mCallback.onShopSelected(position);
    }
}
