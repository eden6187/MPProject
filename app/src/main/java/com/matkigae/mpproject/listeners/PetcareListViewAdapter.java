package com.matkigae.mpproject.listeners;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.matkigae.mpproject.data.PetcareInfo;
import com.matkigae.mpproject.R;

import java.util.ArrayList;

public class PetcareListViewAdapter extends BaseAdapter {
    private ArrayList<PetcareInfo> itemList = new ArrayList<PetcareInfo>();

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

        ImageView iconImageView = (ImageView) view.findViewById(R.id.petcare_image_petcarelistview_item);
        ImageView starImageView = (ImageView) view.findViewById(R.id.petcare_star_petcarelistview_item);
        TextView titleTextView = (TextView) view.findViewById(R.id.petcare_title_petcarelistview_item);
        TextView ratingnumTextView = (TextView) view.findViewById(R.id.petcare_rating_num_petcarelistview_item);
        TextView reviewCountTextView = (TextView) view.findViewById(R.id.petcare_reviewcount_petcarelistview_item);
        TextView distanceTextView = (TextView) view.findViewById(R.id.petcare_distance_petcarelistview_item);

        iconImageView.setImageResource((int)listViewItem.getmIcon());
        titleTextView.setText(listViewItem.getmPetcareTitle());

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
