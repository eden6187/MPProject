package com.matkigae.mpproject.fragments;

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
        initializeDataFromDB();
        setDataBaseAdapter();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initializeDataFromDB(){ /** 이상 없이 잘 작동함 **/
        DatabaseReference ref = mDb.getReference().child("providers");
        Query query = ref;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot provider : dataSnapshot.getChildren()){
                    PetcareInfo info = provider.getValue(PetcareInfo.class);
                    adapter.addItem(info);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"서버에 문제가 발생하였습니다.",Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
    ** 현재 Provider tree에 있는 정보를 전부 가져와서 Adapter에 추가시켜주는 Method
     * 정상작
    **/
    public void setDataBaseAdapter(){
        DatabaseReference ref = mDb.getReference().child("providers");
        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
//                    adapter.addItem(data.getValue(PetcareInfo.class));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    adapter.addItem(data.getValue(PetcareInfo.class));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    adapter.addItem(data.getValue(PetcareInfo.class));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    adapter.addItem(data.getValue(PetcareInfo.class));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"서버에 문제가 발생하였습니다.",Toast.LENGTH_LONG).show();
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