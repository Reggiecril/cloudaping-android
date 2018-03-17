package com.cloudaping.cloudaping_android;

/**
 * Created by reggie on 13/03/18.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by filipp on 9/16/2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context context;
    private List<MyData> my_data;

    public CustomAdapter(Context context, List<MyData> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {

        holder.name.setText(my_data.get(position).getDescription());
        holder.price.setText(my_data.get(position).getPrice());
        Glide.with(context).load(my_data.get(position).getImage_link()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + my_data.get(position));

                Toast.makeText(context, my_data.get(position).getDescription(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.line_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + my_data.get(position));

                Toast.makeText(context, my_data.get(position).getDescription(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView name;
        public ImageView imageView;
        public TextView price;
        public LinearLayout line_card;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.card_product_name);
            imageView = (ImageView) itemView.findViewById(R.id.card_product_image);
            price = (TextView) itemView.findViewById(R.id.card_product_price);
            line_card = (LinearLayout) itemView.findViewById(R.id.line_card);
        }
    }
}