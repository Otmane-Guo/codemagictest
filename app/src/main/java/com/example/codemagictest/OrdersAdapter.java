package com.example.codemagictest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private Context context;
    private List<Order> orders;

    public OrdersAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //elements of item as views
        public TextView orderName;
        public ImageView orderImage;
        public CardView cardView;
        public TextView orderStat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderImage = itemView.findViewById(R.id.order_image);
            orderName = itemView.findViewById(R.id.order_title);
            orderStat = itemView.findViewById(R.id.order_status);
            cardView = itemView.findViewById(R.id.order_cardview);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Order order = orders.get(position);
        //replacing the view elements with real data
        holder.orderName.setText(order.getName());
        holder.orderStat.setText(order.getStat());
        Log.d("sip", "up " + String.valueOf(position));

        Picasso.get().load(order.getImage()).into(holder.orderImage);
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }
}
