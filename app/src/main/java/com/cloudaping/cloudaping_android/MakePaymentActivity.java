package com.cloudaping.cloudaping_android;
import java.text.SimpleDateFormat;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MakePaymentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ConfirmationAdapter adapter;
    private List<MyData> my_list;
    private RequestQueue mQueue;
    private Session session;
    private TextView address_name,address_address,address_id,payment_id,payment;
    private Button order;
    private SharedPreference sharedPreference=new SharedPreference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
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
        //name
        order=(Button)findViewById(R.id.confirmation_btn);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnPlaceOrder();
                sharedPreference.emptyShoppingCart(MakePaymentActivity.this);
                Intent intent=new Intent(MakePaymentActivity.this,OrderActivity.class);
                startActivity(intent);
            }
        });
        address_name=(TextView)findViewById(R.id.confirmation_address_name);
        address_id=(TextView)findViewById(R.id.confirmation_address_id);
        payment_id=(TextView)findViewById(R.id.confirmation_payment_id);
        address_address=(TextView)findViewById(R.id.confirmation_address_address);
        payment=(TextView)findViewById(R.id.confirmation_payment);
        //get data from sever
        mQueue = Volley.newRequestQueue(this);
        session=new Session(this);
        jsonParse(session.getCustomerID());

        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        my_list= (ArrayList<MyData>)getIntent().getSerializableExtra("order");
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ConfirmationAdapter(this,my_list);
        recyclerView.setAdapter(adapter);


    }
    public void OnPlaceOrder() {
        session=new Session(this);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate =new Date(System.currentTimeMillis());
        Random rand = new Random();
        int n = rand.nextInt(1000);
        String str=formatter.format(curDate)+""+n;

        String delivery=address_id.getText().toString();
        String payment=payment_id.getText().toString();
        String customer=session.getCustomerID();
        String order_id=str;

        int totalMoney=0;
        for(MyData data:my_list){
            int price=Integer.valueOf(data.getPrice());
            totalMoney+=price;
            String quantity=data.getQuantity();
            String trader=data.getTraderID();
            String total=Integer.toString(Integer.valueOf(quantity)*price);
            String product=Integer.toString(data.getId());
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute("item", order_id, quantity,trader,total,product);
            ToastUtil.showL(this,order_id+" "+quantity+" "+trader+" "+total+" "+product);
        }
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute("orders", order_id, customer,delivery,payment,Integer.toString(totalMoney));
    }
    public class BackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;

        BackgroundWorker(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];

            if (type.equals("orders")) {
                String login_url = "http://cloudaping.com/android/androidConfirmationOrder.php";
                try {
                    String order_id = params[1];
                    String customer_id = params[2];
                    String delivery = params[3];
                    String payment = params[4];
                    String totalMoney = params[5];
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("order_id", "UTF-8") + "=" + URLEncoder.encode(order_id, "UTF-8") + "&"
                            + URLEncoder.encode("customer_id", "UTF-8") + "=" + URLEncoder.encode(customer_id, "UTF-8")+ "&"
                            + URLEncoder.encode("delivery", "UTF-8") + "=" + URLEncoder.encode(delivery, "UTF-8")+ "&"
                            + URLEncoder.encode("payment", "UTF-8") + "=" + URLEncoder.encode(payment, "UTF-8")+ "&"
                            + URLEncoder.encode("totalMoney", "UTF-8") + "=" + URLEncoder.encode(totalMoney, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(type.equals("item")){
                String item_url = "http://cloudaping.com/android/androidConfirmationOrderItem.php";
                try {
                    String order_id = params[1];
                    String quantity = params[2];
                    String trader = params[3];
                    String total = params[4];
                    String product_id = params[5];
                    URL url = new URL(item_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("order_id", "UTF-8") + "=" + URLEncoder.encode(order_id, "UTF-8") + "&"
                            + URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8")+ "&"
                            + URLEncoder.encode("trader", "UTF-8") + "=" + URLEncoder.encode(trader, "UTF-8")+"&"
                            + URLEncoder.encode("total", "UTF-8") + "=" + URLEncoder.encode(total, "UTF-8")+"&"
                            + URLEncoder.encode("product_id", "UTF-8") + "=" + URLEncoder.encode(product_id, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }
    private void jsonParse(String id) {

        String url = "http://cloudaping.com/android/androidConfirmation.php?id="+id;
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("confirmation");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                address_name.setText(object.getString("address_name"));
                                address_address.setText(object.getString("address_address"));
                                payment.setText(object.getString("payment"));
                                address_id.setText(object.getString("address_id"));
                                payment_id.setText(object.getString("payment_id"));
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
