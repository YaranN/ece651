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
import com.example.think.eduhelper.Chat.utils.ItemClickSupport;
import com.example.think.eduhelper.Post.Adaptor.AcceptedPostAdaptor;
import com.example.think.eduhelper.Post.Adaptor.EditMyPostAdaptor;
import com.example.think.eduhelper.Post.core.getPost.GetPostConstractor;
import com.example.think.eduhelper.Post.core.getPost.GetPostPresenter;
import com.example.think.eduhelper.Post.model.ItemView.TitleChildAccepted;
import com.example.think.eduhelper.Post.model.ItemView.TitleChildMy;
import com.example.think.eduhelper.Post.model.ItemView.TitleParentAccepted;
import com.example.think.eduhelper.Post.model.ItemView.TitleParentMy;
import com.example.think.eduhelper.Post.model.Post;
import com.example.think.eduhelper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAcceptedPostFragment extends Fragment implements GetPostConstractor.View, SwipeRefreshLayout.OnRefreshListener,ItemClickSupport.OnItemClickListener {
    private RecyclerView mRecyclerViewMyPostsListing;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AcceptedPostAdaptor mPostsListingRecyclerAdapter;
    private GetPostPresenter mGetPostPresenter;

    public ListAcceptedPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_list_accepted_post, container, false);
        bindViews(view);
        init();
        return view;
    }

    private void bindViews(View view){
        mRecyclerViewMyPostsListing = view.findViewById(R.id.acceptedPosts_listing_recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_acceptedPosts_layout);

    }

    public void init(){
        mGetPostPresenter = new GetPostPresenter(this);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        getAcceptedPosts();
        mRecyclerViewMyPostsListing.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    public void getAcceptedPosts(){
        mGetPostPresenter.getAcceptedPosts();
    }

    @Override
    public void onRefresh() {
        getAcceptedPosts();
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
        List<TitleParentAccepted> parents = new ArrayList<>();
        for(Post post:posts){
            List<Object>  titleChildren = new ArrayList<>();
            TitleParentAccepted parent = new TitleParentAccepted(post.getCourse(),post.getTitle(),post.getTopic());
            TitleChildAccepted child = new TitleChildAccepted(post.getContent());
            titleChildren.add(child);
            parent.setChildrenList(titleChildren);
            parents.add(parent);
        }
        mPostsListingRecyclerAdapter = new AcceptedPostAdaptor(getContext(),parents,posts);
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
                Toast.makeText(getContext(),"Posted by "+ post.getUser().email +" Current status: "+status,Toast.LENGTH_LONG).show();

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
