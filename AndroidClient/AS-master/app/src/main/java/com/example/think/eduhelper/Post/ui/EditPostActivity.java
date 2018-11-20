package com.example.think.eduhelper.Post.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.think.eduhelper.Chat.utils.Constants;
import com.example.think.eduhelper.LoginRegister.ui.LoginFragment;
import com.example.think.eduhelper.R;

public class EditPostActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditPostFragment editPostFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        toolbar = findViewById(R.id.edit_post_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        editPostFragment = getPostInfo();
        if (findViewById(R.id.edit_post_frame_container)!=null){
            if (savedInstanceState!= null){
                return ;
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.edit_post_frame_container, editPostFragment,null);
            fragmentTransaction.commit();
        }
    }

    public EditPostFragment getPostInfo(){
        String course = getIntent().getStringExtra(Constants.ARG_COURSE);
        String title = getIntent().getStringExtra(Constants.ARG_TITLE);
        String content = getIntent().getStringExtra(Constants.ARG_CONTENT);
        String topic = getIntent().getStringExtra(Constants.ARG_TOPIC);
        Long timestamp = getIntent().getLongExtra(Constants.ARG_TIMESTAMP,-1);
        Toast.makeText(this,course,Toast.LENGTH_LONG).show();
        return EditPostFragment.newInstance(course,title,content,topic,(long)timestamp);
    }
}
