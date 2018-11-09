package com.example.think.eduhelper.Profile.core;

import android.content.Context;

public class UpdateProfilePresenter implements UpdateProfileContract.Presenter, UpdateProfileContract.onProfileDatabaseListener {
    private UpdateProfileContract.View mView;
    private UpdateProfileInteractor mUpdateProfileInteractor;

    public UpdateProfilePresenter(UpdateProfileContract.View mView) {
        this.mView = mView;
        mUpdateProfileInteractor = new UpdateProfileInteractor(this);
    }

    @Override
    public void updateProfile(Context context, String uid, String key, String preferences) {
        mUpdateProfileInteractor.updateProfileToDatabase(context, uid, key, preferences);
    }

    @Override
    public void onSuccess(String message) {
        mView.onUpdateProfileSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mView.onUpdateProfileFailure(message);
    }

}
