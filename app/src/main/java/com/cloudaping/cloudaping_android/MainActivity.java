package com.cloudaping.cloudaping_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager viewPager;
    private RecyclerView recyclerViewPopular,recyclerViewRecommend,recyclerViewNew;
    private GridLayoutManager gridLayoutManagerPopular,gridLayoutManagerRecommend,gridLayoutManagerNew;
    private CustomAdapter adapterPopular,adapterRecommend,adapterNew;
    private List<MyData> data_listPopular,data_listRecommend,data_listNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_main_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //viewPager
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        setupLaunchButton();

        //Popular RecyclerView
        recyclerViewPopular = (RecyclerView) findViewById(R.id.RecyclerViewPopular);
        data_listPopular  = new ArrayList<>();
        load_data_from_server(0,"popular");

        gridLayoutManagerPopular = new GridLayoutManager(this,2);
        recyclerViewPopular.setLayoutManager(gridLayoutManagerPopular);

        adapterPopular = new CustomAdapter(this,data_listPopular);
        recyclerViewPopular.setAdapter(adapterPopular);

        recyclerViewPopular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManagerPopular.findLastCompletelyVisibleItemPosition() == data_listPopular.size()-1){
                    load_data_from_server(data_listPopular.get(data_listPopular.size()-1).getId(),"popular");
                }

            }
        });
        //Recommend RecyclerView
        recyclerViewRecommend = (RecyclerView) findViewById(R.id.RecyclerViewRecommend);
        data_listRecommend  = new ArrayList<>();
        load_data_from_server(0,"recommend");

        gridLayoutManagerRecommend = new GridLayoutManager(this,2);
        recyclerViewRecommend.setLayoutManager(gridLayoutManagerRecommend);

        adapterRecommend = new CustomAdapter(this,data_listRecommend);
        recyclerViewRecommend.setAdapter(adapterRecommend);

        recyclerViewRecommend.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManagerRecommend.findLastCompletelyVisibleItemPosition() == data_listRecommend.size()-1){
                    load_data_from_server(data_listRecommend.get(data_listRecommend.size()-1).getId(),"recommend");
                }

            }
        });
        //New RecyclerView
        recyclerViewNew = (RecyclerView) findViewById(R.id.RecyclerViewNew);
        data_listNew  = new ArrayList<>();
        load_data_from_server(0,"new");

        gridLayoutManagerNew = new GridLayoutManager(this,2);
        recyclerViewNew.setLayoutManager(gridLayoutManagerNew);

        adapterNew = new CustomAdapter(this,data_listNew);
        recyclerViewNew.setAdapter(adapterNew);

        recyclerViewNew.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManagerNew.findLastCompletelyVisibleItemPosition() == data_listNew.size()-1){
                    load_data_from_server(data_listNew.get(data_listNew.size()-1).getId(),"new");
                }

            }
        });

    }
    private void load_data_from_server(int id,String type) {
        if (type == "popular") {
            AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
                @Override
                protected Void doInBackground(Integer... integers) {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://cloudaping.com/android/androidPopular.php?id=" + integers[0])
                            .build();
                    try {
                        Response response = client.newCall(request).execute();

                        JSONArray array = new JSONArray(response.body().string());

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject object = array.getJSONObject(i);

                            MyData data = new MyData(object.getInt("product_id"), object.getString("product_name"),
                                    object.getString("product_mainImage"));

                            data_listPopular.add(data);
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
                    adapterPopular.notifyDataSetChanged();
                }
            };
            task.execute(id);
        }else if(type == "recommend"){
            final String s="22";
            AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
                @Override
                protected Void doInBackground(Integer... integers) {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://cloudaping.com/android/androidRecommend.php?id="+ integers[0])
                            .build();
                    try {
                        Response response = client.newCall(request).execute();

                        JSONArray array = new JSONArray(response.body().string());

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject object = array.getJSONObject(i);

                            MyData data = new MyData(object.getInt("product_id"), object.getString("product_name"),
                                    object.getString("product_mainImage"));

                            data_listRecommend.add(data);
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
                    adapterRecommend.notifyDataSetChanged();
                }
            };
            task.execute(id);
        }else if(type == "new"){
            AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
                @Override
                protected Void doInBackground(Integer... integers) {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://cloudaping.com/android/androidNew.php?id=" + integers[0])
                            .build();
                    try {
                        Response response = client.newCall(request).execute();

                        JSONArray array = new JSONArray(response.body().string());

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject object = array.getJSONObject(i);

                            MyData data = new MyData(object.getInt("product_id"), object.getString("product_name"),
                                    object.getString("product_mainImage"));

                            data_listNew.add(data);
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
                    adapterNew.notifyDataSetChanged();
                }
            };
            task.execute(id);
        }


    }

    private void setupLaunchButton(){
        ImageButton button1=(ImageButton) findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,ProductActivity.class);
                startActivity(intent);
            }
        });
        ImageButton button2=(ImageButton) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent1);
            }
        });
        ImageButton button3=(ImageButton) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent2=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent2);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_main_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_order) {

        } else if (id == R.id.nav_payments) {

        } else if (id == R.id.nav_favourite) {

        } else if (id == R.id.nav_resetPassword) {

        } else if (id == R.id.nav_aboutUs) {

        } else if (id == R.id.nav_contactUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_main_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
