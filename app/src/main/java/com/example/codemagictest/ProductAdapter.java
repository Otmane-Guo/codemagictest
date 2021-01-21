package com.example.codemagictest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<com.example.codemagictest.ProductAdapter.ProductViewHolder> {
    private static final String TAG = "ProductViewAdapter";
    ArrayList<Product> productList;
    private int position;
    private Context mContext;

    public ProductAdapter(Context context, ArrayList<Product> productList)
    {
        this.productList = productList;
        mContext = context;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_listitem, parent, false);
        return new ProductAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Log.d("ProductAdapter", "onBindViewHolder: called.");
        this.position = position;
        Product product = productList.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(product.getImages()[0])
                .into(holder.productImage);

        //holder.productImage.setImageResource(product.getId());
        holder.name.setText(product.getName());
        holder.desc.setText(product.getDescription());
        holder.price.setText(product.getPrice()+"");
        holder.rating.setRating(product.getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on product: " + holder.name.getText());
                Toast.makeText(mContext, holder.name.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ProductInfoActivity.class);
                Product product = productList.get(position);
                intent.putExtra("productSelected", product);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView name, desc, price;
        RatingBar rating;
        View itemView;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            productImage = itemView.findViewById(R.id.image_poduct);
            name = itemView.findViewById(R.id.name_product);
            desc = itemView.findViewById(R.id.desc_product);
            price = itemView.findViewById(R.id.price_product);
            rating = itemView.findViewById(R.id.rating_product);


        }
    }


}
