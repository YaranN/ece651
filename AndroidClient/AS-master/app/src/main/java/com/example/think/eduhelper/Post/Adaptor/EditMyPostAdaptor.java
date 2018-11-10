package com.example.think.eduhelper.Post.Adaptor;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.example.think.eduhelper.Post.core.addPost.AddPostContractor;
import com.example.think.eduhelper.Post.core.addPost.AddPostPresenter;
import com.example.think.eduhelper.Post.model.ItemView.TitleChild;
import com.example.think.eduhelper.Post.model.ItemView.TitleChildMy;
import com.example.think.eduhelper.Post.model.ItemView.TitleParentMy;

import com.example.think.eduhelper.Post.model.Post;
import com.example.think.eduhelper.Post.ui.ViewHolder_Posts.EditMyPostChildViewHolder;
import com.example.think.eduhelper.Post.ui.ViewHolder_Posts.TitleParentViewholder;
import com.example.think.eduhelper.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class EditMyPostAdaptor extends ExpandableRecyclerAdapter<TitleParentMy, TitleChildMy,TitleParentViewholder, com.example.think.eduhelper.Post.ui.ViewHolder_Posts.EditMyPostChildViewHolder>
implements AddPostContractor.View{
    LayoutInflater layoutInflater;
    List<Post> posts;
    Context context;
    private AddPostPresenter mAddPostPresenter;
    private ProgressDialog mProgressDialog;
    private FirebaseUser firebaseUser;


    public EditMyPostAdaptor(Context context, List<TitleParentMy> parentItemList, List<Post> posts){
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
    public com.example.think.eduhelper.Post.ui.ViewHolder_Posts.EditMyPostChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_my_posts_children, childViewGroup, false);
        return new com.example.think.eduhelper.Post.ui.ViewHolder_Posts.EditMyPostChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull TitleParentViewholder parentViewHolder, final int parentPosition, @NonNull TitleParentMy parent) {
        TitleParentMy titleParentMy = parent;
        String category = titleParentMy.getPosterEmail().substring(0, 1);

        parentViewHolder.courseName.setText(titleParentMy.getTitle());
        parentViewHolder.subtitle.setText(titleParentMy.getMaintopic());
        parentViewHolder.posterEmail.setText(category);
        // 可以在这里把那个按钮加进来，这样应该可以
    }

    @Override
    public void onBindChildViewHolder(@NonNull EditMyPostChildViewHolder childViewHolder, final int parentPosition, int childPosition, @NonNull TitleChildMy child) {
        final TitleChildMy titleChild = child;
        childViewHolder.my_detailText.setText(titleChild.getDetailText());
        childViewHolder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = posts.get(parentPosition);
                Toast.makeText(context, "Edit operation in EditMyPostAdaptor.onBindChildViewHolder", Toast.LENGTH_SHORT).show();
                post.setStatus(true);
                mAddPostPresenter.updatePostStatus(context,post);

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
