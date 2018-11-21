package com.example.think.eduhelper.Post.core.getPost;

import com.example.think.eduhelper.Post.model.Post;

import java.util.List;

public interface GetPostConstractor {
    interface View {
        void onGetAllPostsSuccess(List<Post> posts);

        void onGetAllPostsFailure(String message);
    }

    interface Presenter {
        void getAllPosts();
        void getAcceptedPosts();
        void getMyPosts();

    }

    interface Interactor {
        // get
        void getAllPostsFromFirebase();
        void getAcceptedPostsFromFirebase();
        void getMyPostsFromFirebase();


    }

    interface OnGetAllPostsListener {
        void onGetAllPostsSuccess(List<Post> posts);

        void onGetAllPostsFailure(String message);
    }

}
