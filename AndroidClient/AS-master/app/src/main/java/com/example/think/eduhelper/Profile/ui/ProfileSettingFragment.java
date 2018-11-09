package com.example.think.eduhelper.Profile.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceManager;
import android.preference.PreferenceFragment;
import android.preference.Preference;
import android.support.v7.preference.EditTextPreference;
import android.view.View;
import android.widget.Toast;

import com.example.think.eduhelper.Profile.core.UpdateProfileContract;
import com.example.think.eduhelper.Profile.core.UpdateProfilePresenter;
import com.example.think.eduhelper.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileSettingFragment extends PreferenceFragment implements UpdateProfileContract.onProfileDatabaseListener, UpdateProfileContract.View {
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
        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                android.preference.Preference preference = findPreference(key);
                if(!(preference instanceof MultiSelectListPreference)) {
                    String preferences = sharedPreferences.getString(key, "message perconversion");

                    //interactor database operation
                    mUpdateProfilePresenter.updateProfile(getActivity(), firebaseUser.getUid(), key, preferences);

                    preference.setSummary(preferences);
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
    public void onSuccess(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);

    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
    }
}
