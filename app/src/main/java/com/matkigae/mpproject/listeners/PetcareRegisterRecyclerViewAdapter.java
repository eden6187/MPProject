package com.matkigae.mpproject.listeners;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.matkigae.mpproject.R;
import com.matkigae.mpproject.data.MatchingInfo;
import com.matkigae.mpproject.data.PetcareInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class PetcareRegisterRecyclerViewAdapter extends RecyclerView.Adapter {
    public ArrayList<MatchingInfo> infos = new ArrayList<MatchingInfo>();

    public PetcareRegisterRecyclerViewAdapter(ArrayList<MatchingInfo> infos) {
        this.infos = infos;
    }

    public static class RvViewHolder extends RecyclerView.ViewHolder{
        private TextView clientId;
        private TextView starttime;
        private TextView enttime;

        private RvViewHolder(@NonNull View itemView) {
            super(itemView);

            enttime = itemView.findViewById(R.id.recyclerview_myinfo_end_time);
            starttime = itemView.findViewById(R.id.recyclerview_myinfo_start_time);
            clientId = itemView.findViewById(R.id.recyclerview_myinfo_item_client);

        }

        private void onBind(MatchingInfo info){
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user_list").child(info.getClientId());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    HashMap<String,Object> info = (HashMap<String,Object>)dataSnapshot.getValue();
                    String id = (String) info.get("emailAddress");
                    clientId.setText(id);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            enttime.setText(info.getEndTime());
            starttime.setText(info.getStartTime());
        }
    }

    @Override
    public long getItemId(int position) {
        return infos.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myinfo_recyclerview_item,parent,false);
        return new RvViewHolder(view);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RvViewHolder vh = (RvViewHolder)holder;
        vh.onBind(infos.get(position));
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public void addItem(MatchingInfo info){
        this.infos.add(info);
    }

}