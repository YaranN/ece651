package com.example.think.eduhelper.Profile.core;

import android.content.Context;
import android.util.Pair;

import com.example.think.eduhelper.Profile.models.Profile;

import java.util.List;

public interface UpdateProfileContract {
    interface View {
        void onUpdateProfileSuccess(String message);

        void onUpdateProfileFailure(String message);

        void onGetProfileSuccess(List<Pair<String, String>> userProfile);
    }

    interface Presenter {
        void updateProfile(Context context, String uid, String key, String preferences);
        void getProfile(Context context, String uid);
    }

    interface Interactor {
        void updateProfileToDatabase(Context context, String uid, String key, String preferences);
        void getProfileFromDatabase(Context context, String uid);
    }

    interface onProfileDatabaseListener {

        void onSuccess(String message);

        void onFailure(String message);

        void onGetUserProfile(List<Pair<String, String>> userProfile);
    }


}
