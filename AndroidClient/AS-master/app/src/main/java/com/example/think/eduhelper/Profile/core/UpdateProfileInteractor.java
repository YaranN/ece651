package com.example.think.eduhelper.Profile.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.widget.Toast;

import com.example.think.eduhelper.Chat.utils.Constants;
import com.example.think.eduhelper.Profile.models.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.think.eduhelper.R;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UpdateProfileInteractor implements UpdateProfileContract.Interactor {
    private UpdateProfileContract.onProfileDatabaseListener mOnProfileDatabaseListener;
    private DatabaseReference userProfilesRef;

    public UpdateProfileInteractor(UpdateProfileContract.onProfileDatabaseListener mOnProfileDatabaseListener) {
        this.mOnProfileDatabaseListener = mOnProfileDatabaseListener;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        this.userProfilesRef = database.child(Constants.ARG_PROFILE);
    }

    @Override
    public void updateProfileToDatabase(final Context context, String uid, String key, String preferences) {
        DatabaseReference userProfile = this.userProfilesRef.child(uid);
        //update profile item with uid;
        Map<String, Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put(key, preferences);
        //not sure if update would work //
        // when there is no profile item with uid in table Profile
        userProfile.updateChildren(hopperUpdates)
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

    @Override
    public void getProfileFromDatabase(Context context, String uid) {
        DatabaseReference userProfile = this.userProfilesRef.child(uid);

        userProfile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List< Pair<String, String> > profileInfo = new ArrayList<>();
                Iterator<DataSnapshot> profileInfoSnapshot = dataSnapshot.getChildren().iterator();
                while (profileInfoSnapshot.hasNext()) {
                    DataSnapshot infoPairSnapshot = profileInfoSnapshot.next();
                    String key = infoPairSnapshot.getKey();
                    String val = infoPairSnapshot.getValue().toString();
                    Pair<String, String> infoPair = new Pair<>(key, val);
                    profileInfo.add(infoPair);
                    //System.out.println("key: " + key+"; value: "+val);
                }
                mOnProfileDatabaseListener.onGetUserProfile(profileInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
