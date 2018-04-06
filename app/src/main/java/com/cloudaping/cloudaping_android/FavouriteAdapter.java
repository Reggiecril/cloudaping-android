package com.cloudaping.cloudaping_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by reggie on 04/04/18.
 */


public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context context;
    private List<ShoppingCartBean> favourite_data;
    public static final String favourite = "com.cloudaping.cloudaping_android.extra.MESSAGE";

    public FavouriteAdapter(Context context, List<ShoppingCartBean> favourite_data) {
        this.context = context;
        this.favourite_data = favourite_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_card,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        final String id=Integer.toString(favourite_data.get(position).getId());
        holder.productName.setText(favourite_data.get(position).getShoppingName());
        holder.productName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ItemActivity.class);

                intent.putExtra(favourite, id);
                context.startActivity(intent);
            }
        });
        int price=(int)favourite_data.get(position).getPrice();
        holder.productPrice.setText("£"+Integer.toString(price));
        Glide.with(context).load("http://cloudaping.com/assets/images/"+favourite_data.get(position).getImageUrl()).into(holder.productImage);
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ItemActivity.class);

                intent.putExtra(favourite, id);
                context.startActivity(intent);
            }
        });
        holder.productDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alert = new AlertDialog.Builder(context).create();
                alert.setTitle("Action Request:");
                alert.setMessage("Are you sure you would like to remove those items?");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "Canel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "Remove",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                favourite_data.remove(position);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();

                            }
                        });
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favourite_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView productName;
        public TextView productPrice;
        public ImageView productImage;
        public ImageView productDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.tv_commodity_name);
            productPrice = (TextView) itemView.findViewById(R.id.tv_commodity_price);
            productImage = (ImageView) itemView.findViewById(R.id.iv_show_pic);
            productDelete = (ImageView) itemView.findViewById(R.id.tv_commodity_delete);
        }
    }
}