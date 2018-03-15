package com.cloudaping.cloudaping_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProductActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<MyData> data_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //display the right navigation drawer
        displayRightNavigation();
        productConnection();



    }
    public void productConnection(){
        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        data_list = new ArrayList<>();
        load_data_from_server("0");

        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CustomAdapter(this, data_list);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size() - 1) {
                    load_data_from_server(String.valueOf(data_list.get(data_list.size() - 1).getId()));
                }

            }
        });
    }

        /**
         * load data from server
         */
    private void load_data_from_server(String id) {
            AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
                @Override
                protected Void doInBackground(String... Strings) {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://cloudaping.com/android/androidPopular.php?id=" + Strings[0])
                            .build();
                    try {
                        Response response = client.newCall(request).execute();

                        JSONArray array = new JSONArray(response.body().string());

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject object = array.getJSONObject(i);

                            MyData data = new MyData(object.getInt("product_id"), object.getString("product_name"),
                                    object.getString("product_mainImage"));

                            data_list.add(data);
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        System.out.println("End of content");
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    adapter.notifyDataSetChanged();
                }
            };
            task.execute(id);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            drawer.openDrawer(GravityCompat.END);
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayRightNavigation(){
        final NavigationView navigationViewRight = (NavigationView) findViewById(R.id.nav_view_right);
        navigationViewRight.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_right);
                LinearLayout gallery;
                // Handle navigation view item clicks here.
                int id = item.getItemId();
                if(id == R.id.nav_price){
                    gallery = (LinearLayout) navigationView.getMenu().findItem(R.id.nav_price).getActionView();
                    ImageView imageView= (ImageView) gallery.findViewById(R.id.icon_up);
                    imageView.setRotation(imageView.getRotation()+180);
                    itemId(R.id.nav_price,navigationView,gallery);

                }else if (id == R.id.nav_sale) {
                    gallery = (LinearLayout) navigationView.getMenu().findItem(R.id.nav_sale).getActionView();
                    ImageView imageView= (ImageView) gallery.findViewById(R.id.icon_up);
                    imageView.setRotation(imageView.getRotation()+180);
                    itemId(R.id.nav_sale,navigationView,gallery);

                } else if (id == R.id.nav_review) {
                    gallery = (LinearLayout) navigationView.getMenu().findItem(R.id.nav_review).getActionView();
                    ImageView imageView= (ImageView) gallery.findViewById(R.id.icon_up);
                    imageView.setRotation(imageView.getRotation()+180);
                    itemId(R.id.nav_review,navigationView,gallery);

                } else if (id == R.id.nav_updateTime) {
                    gallery = (LinearLayout) navigationView.getMenu().findItem(R.id.nav_updateTime).getActionView();
                    ImageView imageView= (ImageView) gallery.findViewById(R.id.icon_up);
                    imageView.setRotation(imageView.getRotation()+180);
                    itemId(R.id.nav_updateTime,navigationView,gallery);
                }
//                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//                drawer.closeDrawer(GravityCompat.END);
                return true;

            }

            /**
             * find the other three item when click one item, then reset them.
             * @param resource
             * @param navigationView
             * @param gallery
             */
            public void itemId(int resource,NavigationView navigationView,LinearLayout gallery){
                int []allResource=new int[]{R.id.nav_price,R.id.nav_sale,R.id.nav_review,R.id.nav_updateTime};
                HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
                ImageView imageView1= (ImageView) gallery.findViewById(R.id.icon_up);
                imageView1.setImageResource(R.drawable.ic_arrow_upward_black_24dp);

                for (int i=0;i<allResource.length;i++){
                    map.put(allResource[i],allResource[i]);
                }
                if(map.get(resource)==resource){
                    map.remove(resource);
                }else{
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                }



                for (Integer value : map.values()){

                    gallery = (LinearLayout) navigationView.getMenu().findItem(value).getActionView();
                    ImageView imageView= (ImageView) gallery.findViewById(R.id.icon_up);
                    imageView.setImageResource(R.drawable.ic_remove_black_24dp);
                }
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent=new Intent(ProductActivity.this,MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_order) {

        } else if (id == R.id.nav_payments) {

        } else if (id == R.id.nav_favourite) {

        } else if (id == R.id.nav_resetPassword) {

        } else if (id == R.id.nav_aboutUs) {

        } else if (id == R.id.nav_contactUs) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
