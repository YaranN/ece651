package com.example.think.eduhelper.Post.Adaptor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.example.think.eduhelper.Chat.utils.Constants;
import com.example.think.eduhelper.Post.ui.ClosePostDialog;
import com.example.think.eduhelper.Post.core.addPost.AddPostContractor;
import com.example.think.eduhelper.Post.core.addPost.AddPostPresenter;
import com.example.think.eduhelper.Post.model.ItemView.TitleChildMy;
import com.example.think.eduhelper.Post.model.ItemView.TitleParentMy;

import com.example.think.eduhelper.Post.model.Post;
import com.example.think.eduhelper.Post.ui.EditPostActivity;
import com.example.think.eduhelper.Post.ui.ViewHolder_Posts.EditMyPostChildViewHolder;
import com.example.think.eduhelper.Post.ui.EditPostFragment;
import com.example.think.eduhelper.Post.ui.ViewHolder_Posts.TitleParentViewholder;
import com.example.think.eduhelper.R;

import java.util.List;

public class EditMyPostAdaptor extends ExpandableRecyclerAdapter<TitleParentMy, TitleChildMy,TitleParentViewholder, com.example.think.eduhelper.Post.ui.ViewHolder_Posts.EditMyPostChildViewHolder>
implements AddPostContractor.View{
    LayoutInflater layoutInflater;
    List<Post> posts;
    Context context;
    private AddPostPresenter mAddPostPresenter;
    private ProgressDialog mProgressDialog;



    public EditMyPostAdaptor(Context context, List<TitleParentMy> parentItemList, List<Post> posts){
        super(parentItemList);
        this.posts = posts;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
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
    public EditMyPostChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view;
        switch (viewType){
            case R.layout.list_my_posts_children_closed:
                view =  layoutInflater.inflate(R.layout.list_my_posts_children_closed, childViewGroup, false);
                break;
            default:
                view =  layoutInflater.inflate(R.layout.list_my_posts_children, childViewGroup, false);
                break;
        }
        return new EditMyPostChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull TitleParentViewholder parentViewHolder, final int parentPosition, @NonNull TitleParentMy parent) {
        TitleParentMy titleParentMy = parent;
        String category = titleParentMy.getPosterEmail().substring(0, 1);

        parentViewHolder.courseName.setText(titleParentMy.getTitle());
        parentViewHolder.subtitle.setText(titleParentMy.getMaintopic());
        parentViewHolder.posterEmail.setText(category);
    }

    @Override
    public void onBindChildViewHolder(@NonNull EditMyPostChildViewHolder childViewHolder, final int parentPosition, int childPosition, @NonNull TitleChildMy child) {
        final TitleChildMy titleChild = child;
        childViewHolder.my_detailText.setText(titleChild.getDetailText());
        childViewHolder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = posts.get(parentPosition);
                //Toast.makeText(context, "Edit operation in EditMyPostAdaptor.onBindChildViewHolder", Toast.LENGTH_SHORT).show();
                if(!post.isStatus()) {
                    //post.setStatus(true);
                    ClosePostDialog closePostDialog = new ClosePostDialog();
                    closePostDialog.init(mAddPostPresenter, context, post);
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    closePostDialog.show(fragmentManager, "Open the cancel dialog...");
                }
                else{
                    Toast.makeText(context,R.string.closed_post_warning,Toast.LENGTH_SHORT).show();
                }
                //mAddPostPresenter.updatePostStatus(context,post);

            }
        });

        childViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = posts.get(parentPosition);
                //EditPostFragment editPostFragment=  EditPostFragment.newInstance(post.getCourse(),post.getTitle(),post.getContent(),post.getTopic(),(long)post.getTimestamp());

                Intent intent = new Intent(context, EditPostActivity.class);
                intent.putExtra(Constants.ARG_COURSE, post.getCourse());
                intent.putExtra(Constants.ARG_TITLE, post.getTitle());
                intent.putExtra(Constants.ARG_CONTENT, post.getContent());
                intent.putExtra(Constants.ARG_TOPIC, post.getTopic());
                intent.putExtra(Constants.ARG_TIMESTAMP, post.getTimestamp());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        Boolean status = posts.get(parentPosition).isStatus();
        if(status){
            return R.layout.list_my_posts_children_closed;
        }
        return R.layout.list_my_posts_children;
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
