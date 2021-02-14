package com.example.codemagictest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.codemagictest.Model.Category;
import com.example.codemagictest.ProductsActivity;
import com.example.codemagictest.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Category> dataList;
    private Context mContext;

    public CategoryAdapter(Context context, ArrayList<Category> dataList){
        this.dataList = dataList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_listitem, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        //holder.name.setText(dataList.get(position).getName());
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(dataList.get(position).getImage())
                .into(holder.image);

        holder.name.setText(dataList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on product: " + holder.name.getText());
                Toast.makeText(mContext, holder.name.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ProductsActivity.class);
                Category category = dataList.get(position);
                intent.putExtra("categorySelected", category.getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView name;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
        }
    }
}
