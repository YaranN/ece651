package com.example.think.eduhelper.Profile.ui;

import android.content.SharedPreferences;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.think.eduhelper.R;

public class ShowOtherProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_other_profile);
        uid = getIntent().getExtras().getString("uid");

        if (findViewById(R.id.fragment_container_showpreference) != null) {
            if (savedInstanceState != null) {
                return;
            }
            IneditableProfileSettingFragment ineditableProfileSettingFragment = new IneditableProfileSettingFragment();
            ineditableProfileSettingFragment.setUID(uid);
            getFragmentManager().beginTransaction().replace(R.id.fragment_container_showpreference, ineditableProfileSettingFragment).addToBackStack(null).commit();
        }
        toolbar = findViewById(R.id.default_toolBar);
        toolbar.setTitle("Personal Info");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}
