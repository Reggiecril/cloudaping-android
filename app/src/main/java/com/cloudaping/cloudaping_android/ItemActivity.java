package com.cloudaping.cloudaping_android;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;


public class ItemActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE ="com.cloudaping.cloudaping_android.extra.MESSAGE";
    ViewPager itemViewPager;
    private RequestQueue mQueue;
    private TextView nowPrice,originPrice,review,name;
    private ImageView imageView;
    private  Spinner spinner;
    private ArrayList<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
    private SharedPreference sharedPreference =new SharedPreference();
    private Session session;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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
        shoppingCartBeanList=sharedPreference.getShoppingCart(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (shoppingCartBeanList!=null) {
            if (shoppingCartBeanList.size() == 0) {
                fab.setVisibility(View.INVISIBLE);
            } else {
                fab.setVisibility(View.VISIBLE);
                new QBadgeView(this).bindTarget(fab).setBadgeNumber(shoppingCartBeanList.size());

            }
        } else {
            fab.setVisibility(View.INVISIBLE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ItemActivity.this,ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
        //
        nowPrice=(TextView)findViewById(R.id.txt_item_nowPrice);
        originPrice=(TextView)findViewById(R.id.txt_item_originPrice);
        originPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        review=(TextView)findViewById(R.id.txt_item_review_count);
        name=(TextView)findViewById(R.id.txt_item_name);


        //get data from sever
        mQueue = Volley.newRequestQueue(this);
        //receive message from other activity
        Intent intent = getIntent();
        String message = intent.getStringExtra(CustomAdapter.EXTRA_MESSAGE);


        String shopping_message=intent.getStringExtra(ShoppingCartAdapter.shop);
        ToastUtil.showL(this,shopping_message);
        if (shopping_message!=null){
            message=shopping_message;
        }
        jsonParse(message);
        //ViewPager
        imageView = (ImageView)findViewById(R.id.imageView_item);

        spinner = (Spinner) findViewById(R.id.item_planets_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //Button click to add cart
        Button btn_cart=(Button)findViewById(R.id.btn_item_addCart);
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String message = intent.getStringExtra(CustomAdapter.EXTRA_MESSAGE);
                String shopping_message=intent.getStringExtra(ShoppingCartAdapter.shop);
                if (shopping_message!=null){
                    message=shopping_message;
                }
                RequestQueue cart_Queue=Volley.newRequestQueue(ItemActivity.this);
                String url = "http://cloudaping.com/android/androidItem.php?id="+Integer.valueOf(message);

                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("item");

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);
                                        ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
                                        session=new Session(ItemActivity.this);
                                        shoppingCartBean.setCustomerID(session.getCustomerID());
                                        shoppingCartBean.setShoppingName(object.getString("product_name"));
                                        shoppingCartBean.setAttribute("");
                                        Double price=Double.valueOf(object.getString("product_nowPrice"));
                                        shoppingCartBean.setPrice(price);
                                        shoppingCartBean.setId(object.getInt("product_id"));
                                        int count=Integer.valueOf(spinner.getSelectedItem().toString());
                                        shoppingCartBean.setCount(count);
                                        String imageUrl="http://cloudaping.com/assets/images/"+object.getString("product_mainImage");
                                        shoppingCartBean.setImageUrl(imageUrl);
                                        sharedPreference.addShoppingCart(ItemActivity.this,shoppingCartBean);
                                        Intent intent=new Intent(ItemActivity.this,ShoppingCartActivity.class);
                                        startActivity(intent);
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

                cart_Queue.add(request);

            }
        });


//        Toast.makeText(this, data_list.get(0).getDescription(), Toast.LENGTH_SHORT).show();


    }

    private void jsonParse(String id) {

        String url = "http://cloudaping.com/android/androidItem.php?id="+id;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("item");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
//                              data = new MyData(object.getInt("product_id"), object.getString("product_name"),object.getString("product_mainImage"), object.getString("product_type"),object.getString("product_originPrice"), object.getString("product_quantity"), object.getString("product_category"), object.getString("product_trader_id"),object.getString("product_updateDate"),object.getString("product_sell"), object.getString("product_nowPrice"));
                                nowPrice.setText("£"+object.getString("product_nowPrice"));
                                originPrice.setText("Old: £"+object.getString("product_originPrice"));
                                review.setText(object.getString("product_review"));
                                name.setText(object.getString("product_name"));
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
