package com.example.think.eduhelper.Post.Adaptor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.example.think.eduhelper.Chat.models.User;
import com.example.think.eduhelper.Post.core.addPost.AddPostContractor;
import com.example.think.eduhelper.Post.core.addPost.AddPostPresenter;
import com.example.think.eduhelper.Post.model.ItemView.TitleChild;
import com.example.think.eduhelper.Post.model.ItemView.TitleParent;
import com.example.think.eduhelper.Post.model.Post;
import com.example.think.eduhelper.Post.ui.PostsListingActivity;
import com.example.think.eduhelper.R;
import com.example.think.eduhelper.Post.ui.ViewHolder_Posts.TitleParentViewholder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class PostAdaptor extends ExpandableRecyclerAdapter<TitleParent, TitleChild,TitleParentViewholder, com.example.think.eduhelper.Post.ui.ViewHolder_Posts.ChildViewHolder>
implements AddPostContractor.View{
    LayoutInflater layoutInflater;
    List<Post> posts;
    Context context;
    private AddPostPresenter mAddPostPresenter;
    private ProgressDialog mProgressDialog;
    private FirebaseUser firebaseUser;


    public PostAdaptor(Context context, List<TitleParent> parentItemList, List<Post> posts){
        super(parentItemList);
        this.posts = posts;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mAddPostPresenter = new AddPostPresenter(this);
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
    public com.example.think.eduhelper.Post.ui.ViewHolder_Posts.ChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_children, childViewGroup, false);
        return new com.example.think.eduhelper.Post.ui.ViewHolder_Posts.ChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull TitleParentViewholder parentViewHolder, final int parentPosition, @NonNull TitleParent parent) {
        TitleParent titleParent = parent;
        String category = titleParent.getPosterEmail().substring(0, 1);

        parentViewHolder.courseName.setText(titleParent.getTitle());
        parentViewHolder.subtitle.setText(titleParent.getMaintopic());
        parentViewHolder.posterEmail.setText(category);
        // 可以在这里把那个按钮加进来，这样应该可以
    }

    @Override
    public void onBindChildViewHolder(@NonNull com.example.think.eduhelper.Post.ui.ViewHolder_Posts.ChildViewHolder childViewHolder, final int parentPosition, int childPosition, @NonNull TitleChild child) {
        final TitleChild titleChild = child;
        childViewHolder.detailText.setText(titleChild.getDetailText());
        childViewHolder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = posts.get(parentPosition);
                Toast.makeText(context, "You have selected "+posts.get(parentPosition).getTitle(), Toast.LENGTH_SHORT).show();
                post.setStatus(true);
                mAddPostPresenter.addSelectedPost(context,post);
            }
        });
    }

    @Override
    public void onAddPostSuccess(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(getContext(), PostsListingActivity.class));
    }

    @Override
    public void onAddPostFailure(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
