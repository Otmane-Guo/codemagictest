package com.example.codemagictest;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.codemagictest.API.APIInterface;
import com.example.codemagictest.API.ClientAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.CartItemViewHolder>{
    private Context context;
    ArrayList<CartItem> cartItemsArray;

    public CartItemsAdapter(Context context, ArrayList<CartItem> cartItemsArray) {
        this.context = context;
        this.cartItemsArray = cartItemsArray;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_item_cell, parent, false);
        return new CartItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
//        Log.d("ProductAdapter", "onBindViewHolder: called.");
        CartItem cartItem = cartItemsArray.get(position);
        Glide.with(context)
                .asBitmap()
                .load(cartItem.getImage())
                .into(holder.prodImage);

        //holder.productImage.setImageResource(product.getId());
        holder.title.setText(cartItem.getName());
        holder.subTitle.setText(cartItem.getQte()+" x "+cartItem.getPrice()+" = "+cartItem.getQte()*cartItem.getPrice()+ " MAD");

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("removeBtn clicked", "onClick: remove: ");
                CartItem cartItem = cartItemsArray.get(position);
                Toast.makeText(context, holder.title.getText() + " removed from cart", Toast.LENGTH_SHORT).show();


                //TODO Execute remove querry
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        removeProductFromSync(cartItem.getProdId());
                    }
                };
                new Thread(runnable).start();

                Intent intent = new Intent(context, CheckoutActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);


            }


        });


    }

    @Override
    public int getItemCount() {
        return cartItemsArray.size();
    }


    class CartItemViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        TextView title;
        TextView subTitle;
        ImageView prodImage;
        Button removeBtn;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.title=itemView.findViewById(R.id.itemTitle);
            this.subTitle=itemView.findViewById(R.id.itemSubtitle);
            this.prodImage=itemView.findViewById(R.id.prodImage);
            this.removeBtn = itemView.findViewById(R.id.removeProductFromCartBtn);
        }
    }


    public void removeProductFromSync(int productId) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedpreferences.edit();
        //editor.putInt("idUser", idUser);
        //editor.commit();

        int userId = sharedpreferences.getInt("idUser",-1);
        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call = service.removeProdFromCart(userId, productId);
        //final User[] user = {null};
        User currentUser;


        try
        {
            Response<JsonObject> response = call.execute();
            //JsonObject jsonArrayOfOrders = response.body();

            //if(response.body().get("user") instanceof JsonArray)
            if(response.body().get("id").isJsonNull() != true) {
                JsonArray jsonArrayOfOrders = response.body().get("id").getAsJsonArray();

                for (int i = 0; i < jsonArrayOfOrders.size(); i++) {
                    JsonObject jsonOrder = jsonArrayOfOrders.get(i).getAsJsonObject();
                    Log.d("response not null", "JsonArray full");
                    //int id = Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", ""));
                    //String[] productImagesUrls = listProductImages(id);
                    /*if(jsonOrder!=null){
                        user = new User(Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", "")),
                                String.valueOf(jsonOrder.get("firstName")).replace("\"", ""),
                                String.valueOf(jsonOrder.get("lastName")).replace("\"", ""),
                                String.valueOf(jsonOrder.get("address")).replace("\"", ""),
                                String.valueOf(jsonOrder.get("tel")).replace("\"", ""),
                                String.valueOf(jsonOrder.get("password")).replace("\"", ""),
                                String.valueOf(jsonOrder.get("login")).replace("\"", "")
                        );
                        prefs.edit().putInt("idUser", user.getId()).commit();
                        Log.d("response not null", "JsonArray user");
                        //setUser(user);
                    }*/

                }
            }
            else {
                //Toast.makeText(LoginActivity.this, "Email or password invalid", Toast.LENGTH_SHORT).show();
                Log.d("response null", "not JsonArray");
            }
            //API response
            //System.out.println(jsonArrayOfOrders);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
}


