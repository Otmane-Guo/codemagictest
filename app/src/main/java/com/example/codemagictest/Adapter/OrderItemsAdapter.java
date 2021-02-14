package com.example.codemagictest.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.codemagictest.Model.Order;
import com.example.codemagictest.R;

import java.util.ArrayList;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemViewHolder>{
    private Context context;
    ArrayList<Order> orderItemsArray;

    public OrderItemsAdapter(Context context, ArrayList<Order> orderItemsArray) {
        this.context = context;
        this.orderItemsArray = orderItemsArray;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_item_cell, parent, false);
        return new OrderItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
//        Log.d("ProductAdapter", "onBindViewHolder: called.");
        Order orderItem = orderItemsArray.get(position);
        Glide.with(context)
                .asBitmap()
                .load(orderItem.getImage())
                .into(holder.prodImage);

        //holder.productImage.setImageResource(product.getId());
        holder.title.setText(orderItem.getName());
        holder.subTitle.setText(orderItem.getQte()+" x "+orderItem.getPrice()+" = "+orderItem.getQte()*orderItem.getPrice()+ " MAD");

//        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("removeBtn clicked", "onClick: remove: "+holder.);
//                Toast.makeText(mContext, holder.name.getText(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, ProductInfoActivity.class);
//                Product product = productList.get(position);
//                intent.putExtra("productSelected", product);
//                mContext.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return orderItemsArray.size();
    }


    class OrderItemViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        TextView title;
        TextView subTitle;
        ImageView prodImage;
        Button removeBtn;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.title=itemView.findViewById(R.id.itemTitle);
            this.subTitle=itemView.findViewById(R.id.itemSubtitle);
            this.prodImage=itemView.findViewById(R.id.prodImage);
            this.removeBtn = itemView.findViewById(R.id.removeProductFromCartBtn);
        }
    }
}


