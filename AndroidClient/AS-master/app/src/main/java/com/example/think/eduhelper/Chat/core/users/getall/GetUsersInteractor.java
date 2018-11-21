package com.example.think.eduhelper.Chat.core.users.getall;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.think.eduhelper.Chat.models.Chat;
import com.example.think.eduhelper.Chat.models.User;
import com.example.think.eduhelper.Chat.utils.Constants;
import com.example.think.eduhelper.Post.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class GetUsersInteractor implements GetUsersContract.Interactor {
    private static final String TAG = "GetUsersInteractor";

    private GetUsersContract.OnGetAllUsersListener mOnGetAllUsersListener;

    public GetUsersInteractor(GetUsersContract.OnGetAllUsersListener onGetAllUsersListener) {
        this.mOnGetAllUsersListener = onGetAllUsersListener;
    }


    @Override
    public void getAllUsersFromFirebase() {
        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                List<User> users = new ArrayList<>();
                while (dataSnapshots.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                    User user = dataSnapshotChild.getValue(User.class);
                    if (!TextUtils.equals(user.uid, FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        users.add(user);
                    }
                }
                mOnGetAllUsersListener.onGetAllUsersSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetAllUsersListener.onGetAllUsersFailure(databaseError.getMessage());
            }
        });
    }

    @Override
    public void getHelpersFromFirebase() {
        final List<User> users = new ArrayList<>();
        final Map<String, User> uidUser = new HashMap<String, User>() {
        };

        final String curr_UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                while (dataSnapshots.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                    Post post = dataSnapshotChild.getValue(Post.class);
                    // except yourself
                    if (TextUtils.equals(post.getUid(), curr_UID)) {
                        List<User> received_users = post.getAcceptors();
                        for (User user : received_users) {
                            if(!TextUtils.equals(user.uid, curr_UID)){
                            uidUser.put(user.uid, user);
                            }
                        }
                    }
                }
                for (Map.Entry<String, User> user : uidUser.entrySet()) {
                    users.add(user.getValue());
                }
                mOnGetAllUsersListener.onGetAllUsersSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetAllUsersListener.onGetAllUsersFailure(databaseError.getMessage());
            }
        });
    }


    @Override
    public void getSelectedUsersFromFirebase() {
        // store format in database is "Accepted"->"UID"->"accepted task posted" -> user
        final List<User> accepted_users=new ArrayList<>();
        final Map<String, User> uidUser = new HashMap<String, User>() {
        };
        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_CHAT_ACCEPTED).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots=dataSnapshot.getChildren().iterator();
                while (dataSnapshots.hasNext()){
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                    Post post = dataSnapshotChild.getValue(Post.class);
                    uidUser.put(post.getUid(),post.getUser());
                    // followed could add more condition to justify whether the person should be shown in this page
                }
                for (Map.Entry<String, User> user : uidUser.entrySet()) {
                    accepted_users.add(user.getValue());
                }

                mOnGetAllUsersListener.onGetAllUsersSuccess(accepted_users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetAllUsersListener.onGetAllUsersFailure(databaseError.getMessage());
            }
        });

    }


}
