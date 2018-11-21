package com.example.think.eduhelper.Chat.ui.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.think.eduhelper.Chat.ui.Fragments.UsersFragment;
import com.example.think.eduhelper.Post.ui.ListAcceptedPostFragment;
import com.example.think.eduhelper.Post.ui.MyPostsFragment;
import com.example.think.eduhelper.R;
import com.example.think.eduhelper.ViewPagerAdapter;


public class UserListingActivity extends AppCompatActivity{
    private Toolbar mToolbar;
    private TabLayout mTabLayoutUserListing;
    private ViewPager mViewPagerUserListing;
    private ViewPagerAdapter viewPagerAdapter;



    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserListingActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, UserListingActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_listing);
        bindViews();
        init();
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_user_listing);
        mTabLayoutUserListing = (TabLayout) findViewById(R.id.tab_layout_user_listing);
        mViewPagerUserListing = (ViewPager) findViewById(R.id.view_pager_user_listing);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        UsersFragment acceptedFragment = UsersFragment.newInstance(UsersFragment.TYPE_ACCEPTED);
        UsersFragment receivedFragment = UsersFragment.newInstance(UsersFragment.TYPE_RECEIVED);
        viewPagerAdapter.addFragments(acceptedFragment, "Accepted");
        viewPagerAdapter.addFragments(receivedFragment,"Received");

        mViewPagerUserListing.setAdapter(viewPagerAdapter);
        mTabLayoutUserListing.setupWithViewPager(mViewPagerUserListing);
    }

    private void init() {
        // set the toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_listing, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
