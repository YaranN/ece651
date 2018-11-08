package com.example.think.eduhelper.Post.ui.ViewHolder_Posts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.think.eduhelper.Chat.utils.ItemClickSupport;
import com.example.think.eduhelper.Post.model.Post;
import com.example.think.eduhelper.R;

public class ChildViewHolder extends com.bignerdranch.expandablerecyclerview.ChildViewHolder implements ItemClickSupport.OnItemClickListener {
    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public TextView detailText;
    public Button accept;
    private Post post;
    public ChildViewHolder(@NonNull final View itemView) {
        super(itemView);
        detailText = (TextView) itemView.findViewById(R.id.detail);
        accept = (Button) itemView.findViewById(R.id.bt_accept);

        String detail = detailText.getText().toString();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

    }
}
