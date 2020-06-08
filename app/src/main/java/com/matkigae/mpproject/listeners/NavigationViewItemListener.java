package com.matkigae.mpproject.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.matkigae.mpproject.R;
import com.matkigae.mpproject.ui.MyInfoActivity;
import com.matkigae.mpproject.ui.SettingActivity;
import com.google.android.material.navigation.NavigationView;

public class NavigationViewItemListener implements NavigationView.OnNavigationItemSelectedListener {
    Context context;

    public NavigationViewItemListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch(id){

            case R.id.drawer_item_settings:
                Toast.makeText(this.context,"settings",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, SettingActivity.class);
                context.startActivity(intent);
                break;

            case R.id.drawer_item_myinfo:

                Toast.makeText(this.context,"myinfo",Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(context, MyInfoActivity.class);
                context.startActivity(intent2);
                break;

        }
        return true;
    }
}
