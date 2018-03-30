package com.cloudaping.cloudaping_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class AccountActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private Session session;
    private TextView name,email,mobile,joinDate;
    private Button btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_36dp));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //init session
        session=new Session(this);
        String message=session.getCustomerID();
        //get UI
        name=(TextView)findViewById(R.id.txt_user_name);
        email=(TextView)findViewById(R.id.txt_user_email);
        mobile=(TextView)findViewById(R.id.txt_user_mobile);
        joinDate=(TextView)findViewById(R.id.txt_user_joinDate);
        btn_logout=(Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.remove("customerID");
                Intent intent=new Intent(AccountActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //get data from sever
        mQueue = Volley.newRequestQueue(this);
        jsonParse(message);
    }

    private void jsonParse(String id) {

        String url = "http://cloudaping.com/android/androidProfile.php?id="+id;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("customer");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
//                              data = new MyData(object.getInt("product_id"), object.getString("product_name"),object.getString("product_mainImage"), object.getString("product_type"),object.getString("product_originPrice"), object.getString("product_quantity"), object.getString("product_category"), object.getString("product_trader_id"),object.getString("product_updateDate"),object.getString("product_sell"), object.getString("product_nowPrice"));
                                name.setText(object.getString("customer_firstname")+" "+object.getString("customer_lastname"));
                                email.setText(object.getString("customer_email"));
                                mobile.setText(object.getString("customer_phoneNumber"));
                                joinDate.setText(object.getString("customer_joinDate"));
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
