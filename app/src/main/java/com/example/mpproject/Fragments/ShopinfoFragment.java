package com.example.mpproject.Fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mpproject.R;
import com.example.mpproject.ui.ReservationActivity;


public class ShopinfoFragment extends Fragment {
    Button mBtnGotoRerevation;

    public ShopinfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopinfo, container, false);
        mBtnGotoRerevation = view.findViewById(R.id.button_shop_reservation);
        mBtnGotoRerevation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReservationActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args =getArguments();
        if(args!=null){
            int pos =args.getInt("position");
            updateShopinfoView(pos);
        }
    }

    public void updateShopinfoView(int position){
        ImageView mShopImage = (ImageView) getView().findViewById(R.id.imageView_shop_image);
        TextView mShopinfo = (TextView) getView().findViewById(R.id.textView_shop_info);
        TextView mShopintro = (TextView) getView().findViewById(R.id.textView_shop_introduce);
        mShopImage.setImageDrawable(getResources().getDrawable(R.drawable.dog1));
        mShopinfo.setText(Data.info[position]);
        mShopintro.setText(Data.introduces[position]);
    }
}
