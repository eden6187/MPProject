package com.matkigae.mpproject.listeners;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.maps.model.LatLng;
import com.matkigae.mpproject.data.PetcareInfo;
import com.matkigae.mpproject.R;
import com.matkigae.mpproject.ui.PetcareRegisterActivity;

import java.util.ArrayList;

public class PetcareListViewAdapter extends BaseAdapter {
    private ArrayList<PetcareInfo> itemList = new ArrayList<PetcareInfo>();

    private static final String tag = "googlemap_location";



    public PetcareListViewAdapter() {
    //    mGPSTracker = new GPSTracker(context);
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
    //    Location currentLocation = mGPSTracker.getLocation();
    //    Log.e(tag, "GPSlocation:"+mGPSTracker.getLatitude()+"  "+mGPSTracker.getLongitude());
    //    Location shopLocation = new Location("shopPoint");
    //    shopLocation.setLatitude(listViewItem.getmXcoordinate());
    //    shopLocation.setLongitude(listViewItem.getmYcoordinate());
    //    String distance = String.valueOf(currentLocation.distanceTo(shopLocation));
    //    distanceTextview.setText(distance);

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

}
