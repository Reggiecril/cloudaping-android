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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager viewPager;
    private RecyclerView recyclerViewPopular,recyclerViewRecommend,recyclerViewNew;
    private GridLayoutManager gridLayoutManagerPopular,gridLayoutManagerRecommend,gridLayoutManagerNew;
    private CustomAdapter adapterPopular,adapterRecommend,adapterNew;
    private List<MyData> data_listPopular,data_listRecommend,data_listNew;
    public static final String EXTRA_MESSAGE ="com.cloudaping.cloudaping_android.extra.MESSAGE";
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //session
        session = new Session(this);
        String string_session=session.getCustomerID();
        Toast.makeText(this, string_session, Toast.LENGTH_SHORT).show();
        //Navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_main_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (string_session==""){
            View header = getLayoutInflater().inflate(R.layout.nav_header_main,navigationView,false);
            navigationView.addHeaderView(header);
        }else{
            View header = getLayoutInflater().inflate(R.layout.loggedin_nav_header,navigationView,false);
            navigationView.addHeaderView(header);
        }


        //viewPager
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        String [] images={"http://cloudaping.com/assets/images/1491587284_screen_shot_2017-04-07_at_10.47.03_am.jpg",
                          "http://cloudaping.com/assets/images/d7e5ee71fa9452b10dbcda80c0b5f3c4.jpg",
                          "http://cloudaping.com/assets/images/beats-ad-800x332.jpg",
                          "http://cloudaping.com/assets/images/xbox one commercial $399.png",
                          "http://cloudaping.com/assets/images/Screen-Shot-2014-01-02-at-4.27.12-AM.png"};
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(this,R.id.imageView2,images);
        viewPager.setAdapter(viewPagerAdapter);

        //imageButton
        setupLaunchButton();

        /*
         *for displaying recyclerView
         */
        //Popular RecyclerView
        recyclerViewPopular = (RecyclerView) findViewById(R.id.RecyclerViewPopular);
        data_listPopular  = new ArrayList<>();
        load_data_from_server(0,"popular");

        gridLayoutManagerPopular = new GridLayoutManager(this,2);
        recyclerViewPopular.setLayoutManager(gridLayoutManagerPopular);

        adapterPopular = new CustomAdapter(this,data_listPopular);
        recyclerViewPopular.setAdapter(adapterPopular);

        //Recommend RecyclerView
        recyclerViewRecommend = (RecyclerView) findViewById(R.id.RecyclerViewRecommend);
        data_listRecommend  = new ArrayList<>();
        load_data_from_server(0,"recommend");

        gridLayoutManagerRecommend = new GridLayoutManager(this,2);
        recyclerViewRecommend.setLayoutManager(gridLayoutManagerRecommend);

        adapterRecommend = new CustomAdapter(this,data_listRecommend);
        recyclerViewRecommend.setAdapter(adapterRecommend);

        //New RecyclerView
        recyclerViewNew = (RecyclerView) findViewById(R.id.RecyclerViewNew);
        data_listNew  = new ArrayList<>();
        load_data_from_server(0,"new");

        gridLayoutManagerNew = new GridLayoutManager(this,2);
        recyclerViewNew.setLayoutManager(gridLayoutManagerNew);

        adapterNew = new CustomAdapter(this,data_listNew);
        recyclerViewNew.setAdapter(adapterNew);

    }

    /**
     * get data from website
     * @param id
     * @param type
     */
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
                                    object.getString("product_mainImage"),object.getString("product_nowPrice"));

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
                                    object.getString("product_mainImage"),object.getString("product_nowPrice"));

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
                                    object.getString("product_mainImage"),object.getString("product_nowPrice"));

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
    //to login activity
    public void ToLogin(View view){
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    //to register activity
    public void ToRegister(View view){
        Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * set action to six image button turn to product activity
     */
    private void setupLaunchButton(){
        ImageButton button1=(ImageButton) findViewById(R.id.btn_laptop);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "laptop");
                startActivity(intent);
            }
        });
        ImageButton button2=(ImageButton) findViewById(R.id.btn_mobile);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);

                intent.putExtra(EXTRA_MESSAGE, "mobile");
                startActivity(intent);
            }
        });
        ImageButton button3=(ImageButton) findViewById(R.id.btn_camera);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);

                intent.putExtra(EXTRA_MESSAGE, "camera");
                startActivity(intent);
            }
        });
        ImageButton button4=(ImageButton) findViewById(R.id.btn_computer);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);

                intent.putExtra(EXTRA_MESSAGE, "computer");
                startActivity(intent);
            }
        });
        ImageButton button5=(ImageButton) findViewById(R.id.btn_audioVideo);
        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);

                intent.putExtra(EXTRA_MESSAGE, "audio&video");
                startActivity(intent);
            }
        });
        ImageButton button6=(ImageButton) findViewById(R.id.btn_other);
        button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);

                intent.putExtra(EXTRA_MESSAGE, "other");
                startActivity(intent);
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
