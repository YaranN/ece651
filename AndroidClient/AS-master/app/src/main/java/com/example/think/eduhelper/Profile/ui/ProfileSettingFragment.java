package com.example.think.eduhelper.Profile.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceManager;
import android.preference.PreferenceFragment;
import android.preference.Preference;
import android.support.v7.preference.EditTextPreference;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.example.think.eduhelper.Profile.core.UpdateProfileContract;
import com.example.think.eduhelper.Profile.core.UpdateProfilePresenter;
import com.example.think.eduhelper.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProfileSettingFragment extends PreferenceFragment implements UpdateProfileContract.View {
    private Preference editTextPreference;
    public SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private FirebaseUser firebaseUser;
    private UpdateProfilePresenter mUpdateProfilePresenter;

    public ProfileSettingFragment() {
        //required empty public constructor
    }

    public void init() {
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mUpdateProfilePresenter = new UpdateProfilePresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.profilepreference);
        mUpdateProfilePresenter.getProfile(getActivity(), firebaseUser.getUid());

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                android.preference.Preference preference = findPreference(key);
                if(!(preference instanceof MultiSelectListPreference)) {
                    String preferences = sharedPreferences.getString(key, "message perconversion");
                    //interactor database operation
                    preference.setSummary(preferences);
                    mUpdateProfilePresenter.updateProfile(getActivity(), firebaseUser.getUid(), key, preferences);

                } else{
                    // for multiselected preference join items
                    String preferences = getMultiSelectedPreference(sharedPreferences.getStringSet(key, null));
                    preference.setSummary(preferences);
                    mUpdateProfilePresenter.updateProfile(getActivity(), firebaseUser.getUid(), key, preferences);
                }

            }
        };
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
        android.preference.Preference preference;
        for (Pair<String, String> infoPair: userProfile) {
            String key= infoPair.first;
            preference = findPreference(key);
            preference.setSummary(infoPair.second);
        }
    }

    public String getMultiSelectedPreference(Set<String> value) {
        List<String> summaryList = new ArrayList<>();
        for (String str : value) {
            summaryList.add(str);
        }
        String summary = TextUtils.join(", ", summaryList);
        return summary;
    }


}
