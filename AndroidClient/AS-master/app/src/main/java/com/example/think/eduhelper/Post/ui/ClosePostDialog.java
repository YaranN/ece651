package com.example.think.eduhelper.Post.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.think.eduhelper.Post.core.addPost.AddPostPresenter;
import com.example.think.eduhelper.Post.model.Post;
import com.example.think.eduhelper.R;

public class ClosePostDialog extends DialogFragment {

    private AddPostPresenter mAddPostPresenter;
    private Context context;
    private AlertDialog.Builder builder;
    private Post post;
    private TextView course;
    private TextView title;


    public ClosePostDialog(){}


    public void bindViews(){
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.close_post_dialog,null);
        builder.setView(view);
        course = view.findViewById(R.id.cancel_post_course);
        title = view.findViewById(R.id.cancel_post_title);
    }

    public void init(AddPostPresenter mAddPostPresenter, Context context, Post post){
        this.mAddPostPresenter = mAddPostPresenter;
        this.context = context;
        this.post = post;
    }


    public void init(){
        course.setText(post.getCourse());
        title.setText(post.getTitle());
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {


        bindViews();
        init();
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Now editing the post status",Toast.LENGTH_SHORT).show();
                mAddPostPresenter.updatePostStatus(context,post);
                }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Back to my posts page",Toast.LENGTH_SHORT).show();

            }
        });


        return builder.create();
    }
}
