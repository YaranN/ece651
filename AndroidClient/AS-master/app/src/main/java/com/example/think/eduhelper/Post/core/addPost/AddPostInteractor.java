package com.example.think.eduhelper.Post.core.addPost;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.think.eduhelper.Chat.models.User;
import com.example.think.eduhelper.Chat.utils.Constants;
import com.example.think.eduhelper.Post.model.Post;
import com.example.think.eduhelper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPostInteractor implements AddPostContractor.Interactor {
    private AddPostContractor.OnPostDatabaseListener mOnPostDatabaseListener;
    private User currUser;

    public AddPostInteractor(AddPostContractor.OnPostDatabaseListener mOnPostDatabaseListener) {
        this.mOnPostDatabaseListener = mOnPostDatabaseListener;
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = firebaseUser.getUid();
        String userEmail = firebaseUser.getEmail();
        String databaseToken = Constants.ARG_FIREBASE_TOKEN;
        currUser = new User(userID,userEmail,databaseToken);
    }

    @Override
    public void addPostToDatabase(final Context context, Post post) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child(Constants.ARG_POSTS)
            .child(post.getUid()+"_"+String.valueOf(post.getTimestamp()))
            .setValue(post)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                mOnPostDatabaseListener.onSuccess(context.getString(R.string.post_successfully_added));
            } else {
                mOnPostDatabaseListener.onFailure(context.getString(R.string.post_unable_to_add));
            }
        }
    });
}

    @Override
    public void addSelectedPostToDatabase(final Context context, Post post) {
        // firstly add the selected post into accepted -> currUserID ->(Post containing posters' information)
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        // excluded those who want to accept their own post
        if(!TextUtils.equals(post.getUid(), FirebaseAuth.getInstance().getCurrentUser().getUid())){
            database.child(Constants.ARG_SELECTED_POSTS)
                    .child(currUser.uid)
                    .child(post.getUid()+"_"+post.getTimestamp())
                    .setValue(post)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mOnPostDatabaseListener.onSuccess(context.getString(R.string.Mission_accepted_successfully));
                            } else {
                                mOnPostDatabaseListener.onFailure(context.getString(R.string.post_unable_to_add));
                            }
                        }
                    });

        }else
            {
                mOnPostDatabaseListener.onFailure(context.getString(R.string.add_my_post_failure));
            }
    }

    @Override
    public void updatePostInDatabase(final Context context, Post post) {
        String postID = post.getUid()+"_"+post.getTimestamp();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child(Constants.ARG_POSTS)
                    .child(postID)
                    .setValue(post)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mOnPostDatabaseListener.onSuccess(context.getString(R.string.Post_updated_successfully));
                            } else {
                                mOnPostDatabaseListener.onFailure(context.getString(R.string.Post_updated_fail));
                            }
                        }
                    });
    }
}
