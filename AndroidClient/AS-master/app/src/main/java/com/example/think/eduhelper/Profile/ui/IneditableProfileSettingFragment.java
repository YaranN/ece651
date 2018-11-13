package com.example.think.eduhelper.Profile.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Pair;

import com.example.think.eduhelper.Profile.core.UpdateProfileContract;
import com.example.think.eduhelper.Profile.core.UpdateProfilePresenter;
import com.example.think.eduhelper.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class IneditableProfileSettingFragment extends PreferenceFragment implements UpdateProfileContract.View {
    public SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private UpdateProfilePresenter mUpdateProfilePresenter;
    private String uid;
    public IneditableProfileSettingFragment() {
        //required empty public constructor
    }

    public void setUID(String uid){
        this.uid = uid;
    }

    public void init() {
        mUpdateProfilePresenter = new UpdateProfilePresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.profilepreferenceineditable);
        mUpdateProfilePresenter.getProfile(getActivity(), uid);
    }


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        readInformation();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    public void readInformation(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    @Override
    public void onUpdateProfileSuccess(String message){

    }

    @Override
    public void onUpdateProfileFailure(String message){

    }

    @Override
    public void onGetProfileSuccess(List<Pair<String, String>> userProfile) {
        Preference preference;
        for (Pair<String, String> infoPair: userProfile) {
            String key= infoPair.first;
            preference = findPreference(key);
            preference.setSummary(infoPair.second);
        }
    }

}
