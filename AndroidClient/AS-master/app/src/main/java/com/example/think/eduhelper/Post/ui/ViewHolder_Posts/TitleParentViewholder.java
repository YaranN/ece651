package com.example.think.eduhelper.Post.ui.ViewHolder_Posts;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.example.think.eduhelper.R;

public class TitleParentViewholder extends ParentViewHolder {
    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public TextView courseName;
    public TextView subtitle;
    public TextView posterEmail;
    public TitleParentViewholder(@NonNull View itemView) {
        super(itemView);
        courseName = (TextView) itemView.findViewById(R.id.courseTitle);
        subtitle = (TextView) itemView.findViewById(R.id.parentTitle);
        posterEmail = (TextView) itemView.findViewById(R.id.expandRow);

    }
}
