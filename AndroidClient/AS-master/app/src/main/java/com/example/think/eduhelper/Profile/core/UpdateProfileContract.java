package com.example.think.eduhelper.Profile.core;

import android.content.Context;

import com.example.think.eduhelper.Profile.models.Profile;

public interface UpdateProfileContract {
    interface View {
        void onUpdateProfileSuccess(String message);

        void onUpdateProfileFailure(String message);
    }

    interface Presenter {
        void updateProfile(Context context, String uid, String key, String preferences);
    }

    interface Interactor {
        void updateProfileToDatabase(Context context, String uid, String key, String preferences);
    }

    interface onProfileDatabaseListener {
        void onSuccess(String message);

        void onFailure(String message);
    }


}
