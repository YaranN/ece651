package com.example.think.eduhelper.Post.core.getPost;


import android.text.TextUtils;

import com.example.think.eduhelper.Chat.utils.Constants;
import com.example.think.eduhelper.Post.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetPostInteractor implements GetPostConstractor.Interactor {
    private static final String TAG = "GetPostsInteractor";
    private GetPostConstractor.OnGetAllPostsListener mOnGetAllPostsListener;

    public GetPostInteractor(GetPostConstractor.OnGetAllPostsListener mOnGetAllPostsListener) {
        this.mOnGetAllPostsListener = mOnGetAllPostsListener;
    }

    @Override
    public void getAllPostsFromFirebase() {
        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                List<Post> posts = new ArrayList<>();
                while (dataSnapshots.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                    Post post = dataSnapshotChild.getValue(Post.class);
                    // except yourself
                    posts.add(post);
                }
                mOnGetAllPostsListener.onGetAllPostsSuccess(posts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetAllPostsListener.onGetAllPostsFailure(databaseError.getMessage());
            }
        });
    }

    @Override
    public void getMyPostsFromFirebase() {
        final String curr_UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                List<Post> posts = new ArrayList<>();
                while (dataSnapshots.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                    Post post = dataSnapshotChild.getValue(Post.class);
                    // except yourself
                    if(TextUtils.equals(post.getUid(), curr_UID)){
                        posts.add(post);
                    }
                }
                mOnGetAllPostsListener.onGetAllPostsSuccess(posts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetAllPostsListener.onGetAllPostsFailure(databaseError.getMessage());
            }
        });
    }

}
