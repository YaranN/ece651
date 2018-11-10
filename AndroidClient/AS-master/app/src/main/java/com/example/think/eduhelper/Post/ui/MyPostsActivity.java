package com.example.think.eduhelper.Post.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.think.eduhelper.R;

public class MyPostsActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        bindViews();
        init(savedInstanceState);
    }

    public void bindViews(){
        toolbar = findViewById(R.id.toolbar_my_posts);

    }

    public void init(Bundle savedInstanceState){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (findViewById(R.id.my_posts_listing_container)!=null){
            if (savedInstanceState!= null){
                return ;
            }
            MyPostsFragment myPostsFragment = new MyPostsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.my_posts_listing_container, myPostsFragment,null);
            fragmentTransaction.commit();
        }

    }

}
