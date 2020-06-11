package com.matkigae.mpproject.listeners;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matkigae.mpproject.R;
import com.matkigae.mpproject.data.MatchingInfo;
import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter {
    public ArrayList<MatchingInfo> infos = new ArrayList<MatchingInfo>();

    public RvAdapter(ArrayList<MatchingInfo> infos) {
        this.infos = infos;
    }

    public static class RvViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;

        private RvViewHolder(@NonNull View itemView) {
            super(itemView);

             textView = itemView.findViewById(R.id.petcare_title_petcarelistview_item);
        }

        private void onBind(MatchingInfo info){
            textView.setText(info.getConsumerId());
        }
    }

    @Override
    public long getItemId(int position) {
        return infos.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.petcarelistview_item,parent,false);
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
