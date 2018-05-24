package com.cloudaping.cloudaping_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by reggie on 04/04/18.
 */

public class ConfirmationAdapter extends RecyclerView.Adapter<ConfirmationAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context context;
    private List<MyData> my_data;
    public static final String order = "com.cloudaping.cloudaping_android.extra.MESSAGE";

    public ConfirmationAdapter(Context context, List<MyData> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmation_card,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        final String id=Integer.toString(my_data.get(position).getId());
        holder.productName.setText(my_data.get(position).getDescription());
        holder.productType.setText(my_data.get(position).getProductType());
        holder.productNowPrice.setText("£"+my_data.get(position).getPrice());
        holder.productOriginPrice.setText("£"+my_data.get(position).getOriginPrice());
        holder.productQuantity.setText("X"+my_data.get(position).getQuantity());
        Glide.with(context).load(my_data.get(position).getImageName()).into(holder.productImage);
        holder.total.setText(my_data.get(position).getQuantity()+"items"+"  "+"Total:"+my_data.get(position).getTotal());
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ItemActivity.class);

                intent.putExtra(order, id);
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_data.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,my_data.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView storeName,productName,productType
                ,productNowPrice,productOriginPrice,productQuantity,total;
        public ImageView productImage;
        public LinearLayout line_card;
        public Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            storeName = (TextView) itemView.findViewById(R.id.txt_store_name);
            productImage = (ImageView) itemView.findViewById(R.id.img_product);
            productName = (TextView) itemView.findViewById(R.id.product_name);
            productType = (TextView) itemView.findViewById(R.id.product_type);
            productNowPrice = (TextView) itemView.findViewById(R.id.product_nowPrice);
            productOriginPrice = (TextView) itemView.findViewById(R.id.product_originPrice);
            productOriginPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
            productQuantity = (TextView) itemView.findViewById(R.id.product_quantity);
            total=(TextView) itemView.findViewById(R.id.txt_total);
            delete=(Button)itemView.findViewById(R.id.btn_delete);
        }
    }
}