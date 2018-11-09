package com.example.think.eduhelper.Post.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import com.example.think.eduhelper.Chat.ui.Activities.ChatActivity;
import com.example.think.eduhelper.Chat.utils.ItemClickSupport;
import com.example.think.eduhelper.Post.model.ItemView.TitleChild;
import com.example.think.eduhelper.Post.model.ItemView.TitleParent;
import com.example.think.eduhelper.Post.core.getPost.GetPostConstractor;
import com.example.think.eduhelper.Post.core.getPost.GetPostPresenter;
import com.example.think.eduhelper.Post.model.Post;
import com.example.think.eduhelper.R;
import com.example.think.eduhelper.Post.Adaptor.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostListingFragment extends Fragment implements GetPostConstractor.View, SwipeRefreshLayout.OnRefreshListener,ItemClickSupport.OnItemClickListener {
    private RecyclerView mRecyclerViewAllPostsListing;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private PostAdaptor mPostsListingRecyclerAdapter;

    private GetPostPresenter mGetPostPresenter;



    public PostListingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_post_listing, container, false);
        bindViews(view);
        init();
        return view;
    }

    public void bindViews(View v){
        mRecyclerViewAllPostsListing = v.findViewById(R.id.post_listing_recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_posts_layout);
    }

    public void init(){
        mGetPostPresenter = new GetPostPresenter(this);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        getPosts();
        mRecyclerViewAllPostsListing.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        getPosts();
    }

    @Override
    public void onGetAllPostsSuccess(List<Post> posts) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        List<TitleParent> parents = new ArrayList<>();
        for(Post post:posts){
            List<Object>  titleChildren = new ArrayList<>();
            TitleParent parent = new TitleParent(post.getCourse(),post.getTitle(),post.getTopic());
            TitleChild child = new TitleChild(post.getContent());
            titleChildren.add(child);
            parent.setChildrenList(titleChildren);
            parents.add(parent);
        }
        mPostsListingRecyclerAdapter = new PostAdaptor(getContext(),parents,posts);
        mPostsListingRecyclerAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener(){
            @Override
            public void onParentExpanded(int parentPosition) {
                Post post = mPostsListingRecyclerAdapter.getPost(parentPosition);
                Toast.makeText(getContext(),"Posted by "+ post.getUser().email,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onParentCollapsed(int parentPosition) {

            }
        });



        mRecyclerViewAllPostsListing.setAdapter(mPostsListingRecyclerAdapter);
        mPostsListingRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetAllPostsFailure(String message) {

    }

    @Override
    public void onGetSelectedPostsSuccess(List<Post> posts) {

    }

    @Override
    public void onGetSelectedPostssFailure(String message) {
    }

    public void getPosts(){
        mGetPostPresenter.getAllPosts();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        /*ChatActivity.startActivity(getActivity(),
                mPostsListingRecyclerAdapter.getPost(position).email,
                mPostsListingRecyclerAdapter.getPost(position).getUid(),
                mPostsListingRecyclerAdapter.getPost(position).ge.firebaseToken);
                */
    }
}
