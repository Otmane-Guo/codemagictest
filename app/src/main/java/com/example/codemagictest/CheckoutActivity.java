package com.example.codemagictest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.codemagictest.API.APIInterface;
import com.example.codemagictest.API.ClientAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    private String MyPREFERENCES="myPrefs";
    SharedPreferences sharedpreferences;
    ArrayList<CartItem> cartItemsList = new ArrayList<>();
    @BindView(R.id.orderItems)
    RecyclerView orderItems;

    @BindView(R.id.totalPrice)
    TextView totalPrice;

    @BindView(R.id.confirmationButton)
    Button confirmationButton;

    Integer userId;
    float totalPriceSum=0;
//    @BindView(R.id.itemTitle)
//    TextView itemTitle;
//    @BindView(R.id.itemSubtitle)
//    TextView itemSubtitle;
//    @BindView(R.id.prodImage)
//    ImageView prodImage;

    Spinner sprCoun;
    Spinner sprCoun2;
    //Bundle extras;
    //int prodID;
    int idUser;
    //int qte;


    CartItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);

        //extras= getIntent().getExtras();

        //this.prodID = extras.getInt("productId");
        //this.idUser = extras.getInt("userId");
        //this.qte = extras.getInt("userId");
        sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedpreferences.edit();
        //editor.putInt("idUser", idUser);
        //editor.commit();

        userId = sharedpreferences.getInt("idUser",-1);

        Log.w("sharedPrefsTest", "userId = "+userId);

        listProductsAddToCart();
        Log.w("price2", "prix total " + totalPriceSum);

        sprCoun = (Spinner)findViewById(R.id.shippingMethod);
        sprCoun2 = (Spinner)findViewById(R.id.shippingAddress);
    }

    private void orderItemRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CheckoutActivity.this);
        orderItems.setLayoutManager(layoutManager);

        adapter = new CartItemsAdapter(this, cartItemsList);
        orderItems.setAdapter(adapter);
    }

    public void listProductsAddToCart() {

        sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        userId = sharedpreferences.getInt("idUser",-1);


        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call = service.getAddedToCart(userId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonArrayOfOrders = response.body().get("cartItems").getAsJsonArray();

                CartItem cartItem;
                for (int i = 0; i < jsonArrayOfOrders.size(); i++) {
                    JsonObject jsonOrder = jsonArrayOfOrders.get(i).getAsJsonObject();
                    //int id = Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", ""));
                    //String[] productImagesUrls = listProductImages(id);
                    Integer qte = Integer.valueOf(String.valueOf(jsonOrder.get("qte")).replace("\"", ""));
                    Float unitPrice = Float.valueOf(String.valueOf(jsonOrder.get("price")).replace("\"", ""));

                    cartItem = new CartItem(Integer.valueOf(String.valueOf(jsonOrder.get("prodID")).replace("\"", "")),
                            String.valueOf(jsonOrder.get("name")).replace("\"", ""),
                            String.valueOf(jsonOrder.get("url")).replace("\"", ""),
                            qte,
                            unitPrice
                    );
                    cartItemsList.add(cartItem);


                    totalPriceSum+= (unitPrice * qte);
                    Log.w("price", "prix total " + totalPriceSum);
                }
                totalPrice.setText(totalPriceSum + " MAD");
                confirmationButton.setText("Confirmer ("+(totalPriceSum + 49 ) + " MAD)");

                //Log.d("sipphotos", String.valueOf(orderItemsList.get(1).getName()));
                orderItemRecycler();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("siperror", "error in callback");
                Log.d("siperror", t.getMessage());
            }
        });

    }
}