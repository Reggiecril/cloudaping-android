package com.cloudaping.cloudaping_android;

import android.content.Context;
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
 * Created by reggie on 31/03/18.
 */

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context context;
    private List<PaymentData> payment_data;
    public static final String EXTRA_MESSAGE = "com.cloudaping.cloudaping_android.extra.MESSAGE";

    public PaymentAdapter(Context context, List<PaymentData> payment_data) {
        this.context = context;
        this.payment_data = payment_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bankcard,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        final String id=Integer.toString(payment_data.get(position).getId());
        holder.cardNumber.setText(payment_data.get(position).getCardNumber());
        holder.expireDate.setText(payment_data.get(position).getExpirationMonth()+"/"+payment_data.get(position).getExpirationYear());

    }

    @Override
    public int getItemCount() {
        return payment_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView cardNumber;
        public TextView expireDate;
        public LinearLayout bank_card;

        public ViewHolder(View itemView) {
            super(itemView);
            cardNumber = (TextView) itemView.findViewById(R.id.bankcard_cardNumber);
            expireDate = (TextView) itemView.findViewById(R.id.bankcard_expire);
            bank_card = (LinearLayout) itemView.findViewById(R.id.bankcard);
        }
    }
}