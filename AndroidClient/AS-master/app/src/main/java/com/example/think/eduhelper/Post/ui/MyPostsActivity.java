package com.example.think.eduhelper.Post.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.think.eduhelper.R;
import com.example.think.eduhelper.ViewPagerAdapter;

public class MyPostsActivity extends AppCompatActivity {
    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        bindViews();
        init(savedInstanceState);
    }

    public void bindViews(){
        toolbar = findViewById(R.id.toolbar_my_posts);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager_my_posts);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragments(new MyPostsFragment(), "My Posts");
        viewPagerAdapter.addFragments(new ListAcceptedPostFragment(),"Accepted posts");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void init(Bundle savedInstanceState){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*
        if (findViewById(R.id.my_posts_listing_container)!=null){
            if (savedInstanceState!= null){
                return ;
            }
            MyPostsFragment myPostsFragment = new MyPostsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.my_posts_listing_container, myPostsFragment,null).addToBackStack("MyPostsPage");
            fragmentTransaction.commit();
        }
        */

    }

}
