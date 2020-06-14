package com.matkigae.mpproject.listeners;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.DataCollectionDefaultChange;
import com.matkigae.mpproject.data.PetcareInfo;
import com.matkigae.mpproject.R;
import com.matkigae.mpproject.ui.PetcareRegisterActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PetcareListViewAdapter extends BaseAdapter {
    private ArrayList<PetcareInfo> itemList = new ArrayList<PetcareInfo>();
    private Double mX;
    private Double mY;

    GPSTracker mGPSTracker;

    public void sortByPrice(){
        Collections.sort(this.itemList);
    }

    public PetcareListViewAdapter(Context context) {
        mGPSTracker = new GPSTracker(context);
    }

    public void clearAll(){
        itemList.clear();
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.petcarelistview_item, parent, false);
        }

        PetcareInfo listViewItem = itemList.get(position);

        ImageView iconImageView = (ImageView) view.findViewById(R.id.imageview_petcareinfo_shopicon);
        TextView titleTextView = (TextView) view.findViewById(R.id.texview_petcareinfolistviewitem_title);
        TextView priceTextView = (TextView) view.findViewById(R.id.texview_petcareinfolistviewitem_price);
        TextView distanceTextview = (TextView) view.findViewById(R.id.texview_petcareinfolistviewitem_distance);
        titleTextView.setText(listViewItem.getmPetcareTitle());
        priceTextView.setText(listViewItem.getmPrice());
        Double x = Math.abs(listViewItem.getmXcoordinate()-mGPSTracker.getLatitude());
        Double y = Math.abs(listViewItem.getmYcoordinate()-mGPSTracker.getLongitude());
        String distance = String.valueOf(Math.sqrt(x*x+y*y));
        distanceTextview.setText(distance);

        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public PetcareInfo getItem(int position) {
        return itemList.get(position);
    }

    public void addItem(PetcareInfo item){
        itemList.add(item);
    }

    public void setXY(Double x, Double y){
        mX = x;
        mY = y;
    }
}
