package com.example.think.eduhelper.Post.Adaptor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.example.think.eduhelper.Chat.utils.Constants;
import com.example.think.eduhelper.Post.core.addPost.AddPostContractor;
import com.example.think.eduhelper.Post.core.addPost.AddPostPresenter;
import com.example.think.eduhelper.Post.model.ItemView.TitleChildAccepted;
import com.example.think.eduhelper.Post.model.ItemView.TitleChildMy;
import com.example.think.eduhelper.Post.model.ItemView.TitleParentAccepted;
import com.example.think.eduhelper.Post.model.ItemView.TitleParentMy;
import com.example.think.eduhelper.Post.model.Post;
import com.example.think.eduhelper.Post.ui.ClosePostDialog;
import com.example.think.eduhelper.Post.ui.EditPostActivity;
import com.example.think.eduhelper.Post.ui.ViewHolder_Posts.AcceptedPostChildViewHolder;
import com.example.think.eduhelper.Post.ui.ViewHolder_Posts.EditMyPostChildViewHolder;
import com.example.think.eduhelper.Post.ui.ViewHolder_Posts.TitleParentViewholder;
import com.example.think.eduhelper.R;

import java.util.List;

public class AcceptedPostAdaptor extends ExpandableRecyclerAdapter<TitleParentAccepted, TitleChildAccepted,TitleParentViewholder, AcceptedPostChildViewHolder>{
    LayoutInflater layoutInflater;
    List<Post> posts;
    Context context;
    private ProgressDialog mProgressDialog;



    public AcceptedPostAdaptor(Context context, List<TitleParentAccepted> parentItemList, List<Post> posts){
        super(parentItemList);
        this.posts = posts;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle(R.string.loading);
        mProgressDialog.setMessage("Please wait....");
        mProgressDialog.setIndeterminate(true);
    }


    public Post getPost(int position) {
        return posts.get(position);
    }

    @NonNull
    @Override
    public TitleParentViewholder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_parent, parentViewGroup, false);
        return new TitleParentViewholder(view);
    }

    @NonNull
    @Override
    public AcceptedPostChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_accepted_posts_children, childViewGroup, false);
        return new AcceptedPostChildViewHolder(view);
    }


    @Override
    public void onBindParentViewHolder(@NonNull TitleParentViewholder parentViewHolder, int parentPosition, @NonNull TitleParentAccepted parent) {
        TitleParentAccepted titleParentAccepted = parent;
        String category = titleParentAccepted.getPosterEmail().substring(0, 1);

        parentViewHolder.courseName.setText(titleParentAccepted.getTitle());
        parentViewHolder.subtitle.setText(titleParentAccepted.getMaintopic());
        parentViewHolder.posterEmail.setText(category);
    }

    @Override
    public void onBindChildViewHolder(@NonNull AcceptedPostChildViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull TitleChildAccepted child) {
        final TitleChildAccepted titleChild = child;
        childViewHolder.my_detailText.setText(titleChild.getDetailText());
    }

}
