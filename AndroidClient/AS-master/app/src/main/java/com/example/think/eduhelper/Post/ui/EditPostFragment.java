package com.example.think.eduhelper.Post.ui;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.think.eduhelper.Chat.models.User;
import com.example.think.eduhelper.Chat.utils.Constants;
import com.example.think.eduhelper.Post.core.addPost.AddPostContractor;
import com.example.think.eduhelper.Post.core.addPost.AddPostPresenter;
import com.example.think.eduhelper.Post.model.Post;
import com.example.think.eduhelper.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditPostFragment extends Fragment implements View.OnClickListener, AddPostContractor.View {
    private EditText edit_course, edit_title, edit_content, edit_topic;
    private AddPostPresenter mAddPostPresenter;
    private ProgressDialog mProgressDialog;
    private FirebaseUser firebaseUser;
    private Button bt_edit_post_confirm;
    private long timestamp;
    private String email, uid;

    public static EditPostFragment newInstance(String course, String title, String content, String topic, long timestamp) {
        EditPostFragment fragment = new EditPostFragment();
        Bundle bundle = new Bundle(5);
        bundle.putString(Constants.ARG_COURSE, course);
        bundle.putString(Constants.ARG_TITLE, title);
        bundle.putString(Constants.ARG_CONTENT, content);
        bundle.putString(Constants.ARG_TOPIC, topic);
        bundle.putLong(Constants.ARG_TIMESTAMP, timestamp);
        fragment.setArguments(bundle);
        return fragment ;
    }


    public EditPostFragment() {
        // Required empty public constructor
    }

    public void bindViews(View view){
        edit_course = view.findViewById(R.id.edit_post_course);
        edit_content = view.findViewById(R.id.edit_post_detail);
        edit_title = view.findViewById(R.id.edit_post_subtitle);
        edit_topic = view.findViewById(R.id.edit_post_topic);
        bt_edit_post_confirm = view.findViewById(R.id.edit_post_confirm);

    }

    public void init(){
        edit_course.setText(getArguments().getString(Constants.ARG_COURSE));
        edit_title.setText(getArguments().getString(Constants.ARG_TITLE));
        edit_content.setText(getArguments().getString(Constants.ARG_CONTENT));
        edit_topic.setText(getArguments().getString(Constants.ARG_TOPIC));
        this.timestamp = getArguments().getLong(Constants.ARG_TIMESTAMP);


        bt_edit_post_confirm.setOnClickListener(this);
        mAddPostPresenter = new AddPostPresenter(this);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setIndeterminate(true);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_edit_post, container, false);

        bindViews(view);
        init();
        return view;
    }

    @Override
    public void onAddPostSuccess(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddPostFailure(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_post_confirm:
                updatePost(v);
        }
    }

    public void updatePost(View view){
        String post_course = edit_course.getText().toString();
        String post_title = edit_title.getText().toString();
        String post_detail = edit_content.getText().toString();
        String post_topic = edit_topic.getText().toString();

        User user = new User(firebaseUser.getUid(),firebaseUser.getEmail(),Constants.ARG_FIREBASE_TOKEN);
        Post post = new Post(post_course, post_title,post_detail, firebaseUser.getUid(),timestamp,post_topic, user);
        if (post_course.isEmpty() || post_title.isEmpty() || post_detail.isEmpty()) {
            Toast.makeText(getActivity(), "Please complete information", Toast.LENGTH_SHORT).show();
        } else {
            mProgressDialog.show();
            mAddPostPresenter.addPost(getActivity(),post);
            //mRegisterPresenter.register(SignUpActivity.this, userEmail, userPassword);
        }
        Toast.makeText(getActivity(), "Please complete information", Toast.LENGTH_SHORT).show();
    }


}
