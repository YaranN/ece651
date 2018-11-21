package com.example.think.eduhelper.Post.ui.ViewHolder_Posts;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.think.eduhelper.Post.model.Post;
import com.example.think.eduhelper.R;

public class AcceptedPostChildViewHolder extends com.bignerdranch.expandablerecyclerview.ChildViewHolder {
    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public TextView my_detailText;
    public AcceptedPostChildViewHolder(@NonNull final View itemView) {
        super(itemView);
        my_detailText = (TextView) itemView.findViewById(R.id.accepted_post_detail);
    }

}
