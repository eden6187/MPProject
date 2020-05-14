package com.example.mpproject.Fragments;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mpproject.R;

import java.util.ArrayList;

public class PetcareListViewAdapter extends BaseAdapter {
    private ArrayList<PetcareListViewItem> ItemList = new ArrayList<PetcareListViewItem>();

    public PetcareListViewAdapter(){

    }

    @Override
    public int getCount() {
        return ItemList.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.petcarelistview_item, parent, false);
        }
        ImageView iconImageView = (ImageView) view.findViewById(R.id.petcare_image_petcarelistview_item);
        ImageView starImageView = (ImageView) view.findViewById(R.id.petcare_star_petcarelistview_item);
        TextView titleTextView = (TextView) view.findViewById(R.id.petcare_title_petcarelistview_item);
        TextView ratingnumTextView = (TextView) view.findViewById(R.id.petcare_rating_num_petcarelistview_item);
        TextView reviewCountTextView = (TextView) view.findViewById(R.id.petcare_reviewcount_petcarelistview_item);
        TextView distanceTextView = (TextView) view.findViewById(R.id.petcare_distance_petcarelistview_item);
        PetcareListViewItem listViewItem = ItemList.get(position);

        iconImageView.setImageDrawable(listViewItem.getIcon());
        starImageView.setImageDrawable(listViewItem.getStar());
        titleTextView.setText(listViewItem.getTitle());
        ratingnumTextView.setText(listViewItem.getRatingnum());
        reviewCountTextView.setText(listViewItem.getReviewcount());
        distanceTextView.setText(listViewItem.getDistance());

        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public PetcareListViewItem getItem(int position) {
        return ItemList.get(position);
    }

    public void addItem(Drawable icon, Drawable star, String title, float ratingnum, int reviewCount, int distance){
        PetcareListViewItem item = new PetcareListViewItem();

        item.setIcon(icon);
        item.setStar(star);
        item.setTitle(title);
        item.setRatingnum(ratingnum);
        item.setReviewcount(reviewCount);
        item.setDistance(distance);

        ItemList.add(item);
    }
}
