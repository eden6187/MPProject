package com.example.mpproject.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.content.Context;

import com.example.mpproject.R;
import com.example.mpproject.ui.PetCareActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetcareListFragment extends ListFragment {

    public PetcareListFragment() {
        // Required empty public constructor

    }

    PetcareListViewAdapter adapter;

    public interface onShopSelectedListener{
        public void onShopSelected(int position);
    }

    onShopSelectedListener mShopSelListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        adapter = new PetcareListViewAdapter();
        setListAdapter(adapter);
        adapter.addItem(getResources().getDrawable(R.drawable.dog1, null), getResources().getDrawable(R.drawable.star_image, null), "사랑동물호텔", (float)3.5, 5, 100);
        adapter.addItem(getResources().getDrawable(R.drawable.dog2, null), getResources().getDrawable(R.drawable.star_image, null), "모두동물호텔", (float)4.8, 12, 250);
        adapter.addItem(getResources().getDrawable(R.drawable.dog3, null), getResources().getDrawable(R.drawable.star_image, null), "편안한동물호텔", (float)4.2, 15, 400);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void addItem(Drawable icon, Drawable star, String title, float ratingnum, int reviewCount, int distance){
        adapter.addItem(icon, star, title, ratingnum, reviewCount, distance);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        mShopSelListener.onShopSelected(position);
    }
}
