package com.cloudaping.cloudaping_android;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ItemActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE ="com.cloudaping.cloudaping_android.extra.MESSAGE";
    ViewPager itemViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        itemViewPager = (ViewPager)findViewById(R.id.viewPager_item);
        String [] images={"http://cloudaping.com/assets/images/1491587284_screen_shot_2017-04-07_at_10.47.03_am.jpg",
                "http://cloudaping.com/assets/images/d7e5ee71fa9452b10dbcda80c0b5f3c4.jpg",
                "http://cloudaping.com/assets/images/beats-ad-800x332.jpg",
                "http://cloudaping.com/assets/images/xbox one commercial $399.png",
                "http://cloudaping.com/assets/images/Screen-Shot-2014-01-02-at-4.27.12-AM.png"};
        ViewPagerAdapter itemViewPagerAdapter=new ViewPagerAdapter(this,R.id.imageView_item,images);
        itemViewPager.setAdapter(itemViewPagerAdapter);

        Intent intent = getIntent();
        String message = intent.getStringExtra(CustomAdapter.EXTRA_MESSAGE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
