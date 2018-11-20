package com.example.think.eduhelper.Post.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.example.think.eduhelper.Chat.utils.ItemClickSupport;
import com.example.think.eduhelper.Post.Adaptor.EditMyPostAdaptor;
import com.example.think.eduhelper.Post.Adaptor.PostAdaptor;
import com.example.think.eduhelper.Post.core.getPost.GetPostConstractor;
import com.example.think.eduhelper.Post.core.getPost.GetPostPresenter;
import com.example.think.eduhelper.Post.model.ItemView.TitleChild;
import com.example.think.eduhelper.Post.model.ItemView.TitleChildMy;
import com.example.think.eduhelper.Post.model.ItemView.TitleParent;
import com.example.think.eduhelper.Post.model.ItemView.TitleParentMy;
import com.example.think.eduhelper.Post.model.Post;
import com.example.think.eduhelper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPostsFragment extends Fragment implements GetPostConstractor.View, SwipeRefreshLayout.OnRefreshListener,ItemClickSupport.OnItemClickListener {
    private RecyclerView mRecyclerViewMyPostsListing;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private EditMyPostAdaptor mPostsListingRecyclerAdapter;
    private GetPostPresenter mGetPostPresenter;
    private FragmentManager fragmentManager;

    public MyPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_posts, container, false);
        bindViews(view);
        init();
        return view;
    }


    private void bindViews(View view){
        mRecyclerViewMyPostsListing = view.findViewById(R.id.myPost_listing_recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_myPosts_layout);

    }

    public void init(){
        mGetPostPresenter = new GetPostPresenter(this);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        getMyPosts();
        mRecyclerViewMyPostsListing.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    public void getMyPosts(){
        mGetPostPresenter.getMyPosts();
    }

    @Override
    public void onRefresh() {
        getMyPosts();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

    }

    @Override
    public void onGetAllPostsSuccess(List<Post> posts) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        List<TitleParentMy> parents = new ArrayList<>();
        for(Post post:posts){
            List<Object>  titleChildren = new ArrayList<>();
            TitleParentMy parent = new TitleParentMy(post.getCourse(),post.getTitle(),post.getTopic());
            TitleChildMy child = new TitleChildMy(post.getContent());
            titleChildren.add(child);
            parent.setChildrenList(titleChildren);
            parents.add(parent);
        }
        mPostsListingRecyclerAdapter = new EditMyPostAdaptor(getContext(),parents,posts);
        mPostsListingRecyclerAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener(){
            @Override
            public void onParentExpanded(int parentPosition) {
                Post post = mPostsListingRecyclerAdapter.getPost(parentPosition);
                String status;
                if(post.isStatus()){
                    status = "closed";
                }else{
                    status = "still Open";
                }
                Toast.makeText(getContext(),"This post is currently "+ status,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onParentCollapsed(int parentPosition) {

            }
        });


        mRecyclerViewMyPostsListing.setAdapter(mPostsListingRecyclerAdapter);
        mPostsListingRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetAllPostsFailure(String message) {

    }

}
