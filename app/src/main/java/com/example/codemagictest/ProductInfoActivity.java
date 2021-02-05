package com.example.codemagictest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.codemagictest.API.APIInterface;
import com.example.codemagictest.API.ClientAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInfoActivity extends AppCompatActivity {
    private String MyPREFERENCES="myPrefs";
    @BindView(R.id.prod_image)
    ImageView prodImage;
    @BindView(R.id.name)
    TextView prodname;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.prod_desc)
    TextView prodesc;
    @BindView(R.id.previousActivity)
    ImageView previousActivity;

    @BindView(R.id.addToCartbtn)
    Button addToCartButton;

    Product prod;
    SharedPreferences prefs;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.product_info);
        ButterKnife.bind(this);

        prefs = getSharedPreferences("login",Context.MODE_PRIVATE);
        userId = prefs.getInt("idUser",-1);

        productInitializer();


    }
    private void productInitializer() {
        prod = (Product) getIntent().getSerializableExtra("productSelected");
        Glide.with(this)
                .asBitmap()
                .load(prod.getImages()[0])
                .into(prodImage);
        prodname.setText(prod.getName());
        ratingBar.setRating(prod.getRating());
        rating.setText(prod.getRating() + "");
        price.setText(prod.getPrice()+" DH");
        prodesc.setText(prod.getDescription());

    }

    @OnClick(R.id.previousActivity)
    public void previousActivityBtn(){
        ProductInfoActivity.this.finish();
    }

    @OnClick(R.id.addToCartbtn)
    public void AddToCartButtonClicked(){

        addToCartButton.setEnabled(false);
        Log.d("userId from sharedpref", userId+"");
        if(userId != -1){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ProductInfoActivity.this, CheckoutActivity.class);
                    //intent.putExtra("productId", prod.getId());
                    //intent.putExtra("userId", userId);
                    //intent.putExtra("qte", 2);


                    //addProductToCart();
                    addProdToCartSync();

                    //TODO insert in the cart table
                    startActivity(intent);
                }
            };
            new Thread(runnable).start();

        }
        else {
            Toast.makeText(getApplicationContext(), "You have to log in first !!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProductInfoActivity.this, LoginActivity.class);
            startActivity(intent);
        }


    }

    public void addProductToCart(){
        //sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        //userId = sharedpreferences.getInt("idUser",-1);


        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call = service.putProdInCart(userId, prod.getId(), 1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonArrayOfOrders = response.body().get("orderItems").getAsJsonArray();

                /*Order orderItem;
                for (int i = 0; i < jsonArrayOfOrders.size(); i++) {
                    JsonObject jsonOrder = jsonArrayOfOrders.get(i).getAsJsonObject();
                    //int id = Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", ""));
                    //String[] productImagesUrls = listProductImages(id);
                    Integer qte = Integer.valueOf(String.valueOf(jsonOrder.get("qte")).replace("\"", ""));
                    Float unitPrice = Float.valueOf(String.valueOf(jsonOrder.get("price")).replace("\"", ""));

                    orderItem = new Order(String.valueOf(jsonOrder.get("name")).replace("\"", ""),
                            String.valueOf(jsonOrder.get("url")).replace("\"", ""),
                            qte,
                            unitPrice
                    );
                    orderItemsList.add(orderItem);


                    totalPriceSum+= (unitPrice * qte);
                    Log.w("price", "prix total " + totalPriceSum);
                }
                totalPrice.setText(totalPriceSum + " MAD");
                confirmationButton.setText("Confirmer ("+(totalPriceSum + 49 ) + " MAD)");

                //Log.d("sipphotos", String.valueOf(orderItemsList.get(1).getName()));
                orderItemRecycler();*/
                Log.d("","");

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("siperror", "error in callback");
                Log.d("siperror", t.getMessage());
            }
        });
    }

    public void addProdToCartSync() {
        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call = service.putProdInCart(userId, prod.getId(), 1);
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
