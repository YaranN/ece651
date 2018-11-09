package com.example.think.eduhelper.Profile.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.think.eduhelper.Chat.utils.Constants;
import com.example.think.eduhelper.Profile.models.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.think.eduhelper.R;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileInteractor implements UpdateProfileContract.Interactor {
    private UpdateProfileContract.onProfileDatabaseListener mOnProfileDatabaseListener;

    public UpdateProfileInteractor(UpdateProfileContract.onProfileDatabaseListener mOnProfileDatabaseListener) {
        this.mOnProfileDatabaseListener = mOnProfileDatabaseListener;
    }

    @Override
    public void updateProfileToDatabase(final Context context, String uid, String key, String preferences) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hopperRef = database.child(Constants.ARG_PROFILE).child(uid);//update profile item with uid;
        Map<String, Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put(key, preferences);
        //not sure if update would work //
        // when there is no profile item with uid in table Profile
        hopperRef.updateChildren(hopperUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mOnProfileDatabaseListener.onSuccess(context.getString(R.string.profile_successfully_updated));
                        } else {
                            mOnProfileDatabaseListener.onFailure(context.getString(R.string.profile_unable_to_update));
                        }
                    }
                });
    }

}
