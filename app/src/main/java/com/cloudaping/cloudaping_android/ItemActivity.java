package com.cloudaping.cloudaping_android;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;



public class ItemActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE ="com.cloudaping.cloudaping_android.extra.MESSAGE";
    ViewPager itemViewPager;
    private RequestQueue mQueue;
    private TextView nowPrice;
    private ImageView imageView;
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
        //
        nowPrice=(TextView)findViewById(R.id.txt_item_nowPrice);
        //receive message from other activity
        Intent intent = getIntent();
        String message = intent.getStringExtra(CustomAdapter.EXTRA_MESSAGE);


        //get data from sever
        mQueue = Volley.newRequestQueue(this);
        jsonParse(message);
        //ViewPager
        ImageView imageView = (ImageView)findViewById(R.id.imageView_item);




//
//        Toast.makeText(this, data_list.get(0).getDescription(), Toast.LENGTH_SHORT).show();


    }

    private void jsonParse(String id) {

        String url = "http://cloudaping.com/android/androidItem.php?id="+id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("item");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
//                                 data = new MyData(object.getInt("product_id"), object.getString("product_name"),object.getString("product_mainImage"), object.getString("product_type"),object.getString("product_originPrice"), object.getString("product_quantity"), object.getString("product_category"), object.getString("product_trader_id"),object.getString("product_updateDate"),object.getString("product_sell"), object.getString("product_nowPrice"));
                                nowPrice.setText(object.getString("product_name"));
                                Glide.with(ItemActivity.this).load("http://cloudaping.com/assets/images/"+object.getString("product_mainImage")).into(imageView);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

    }
}
