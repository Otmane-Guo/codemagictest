package com.example.codemagictest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.codemagictest.API.APIInterface;
import com.example.codemagictest.API.ClientAPI;
import com.example.codemagictest.Adapter.CartItemsAdapter;
import com.example.codemagictest.Model.CartItem;
import com.example.codemagictest.Model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @BindView(R.id.shippingCosts)
    TextView shippingCostTV;

    Integer userId;
    float totalPriceSum=0;
    float  shippingCost = 49;
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

//        userId = sharedpreferences.getInt("idUser",-1);
        userId = Session.getUserId();

        Log.w("sharedPrefsTest", "userId = "+userId);

        listProductsAddToCart();
        Log.w("price2", "prix total " + totalPriceSum);

        sprCoun = (Spinner)findViewById(R.id.shippingMethod);
        sprCoun2 = (Spinner)findViewById(R.id.shippingAddress);
        confirmationButton.setEnabled(false);

        ArrayList<String> arrayList2 = new ArrayList<String>();
        arrayList2.add(sharedpreferences.getString("userAddress", "null"));
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,arrayList2);
        sprCoun2.setAdapter(adp2);

        ArrayList<String> arrayList1 = new ArrayList<String>();

        arrayList1.add("Amana");
        arrayList1.add("DHL");
        arrayList1.add("Fedex");
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,arrayList1);
        sprCoun.setAdapter(adp);

        sprCoun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long arg3)
            {
                if(position==0){
                    shippingCost = 49;
                }
                else if(position == 1){
                    shippingCost = 100;
                }
                else if(position == 2){
                    shippingCost = 140;
                }
                confirmationButton.setText("Confirmer ("+(totalPriceSum + shippingCost ) + " MAD)");
                shippingCostTV.setText(shippingCost+" MAD");

            }

            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub
            }
        });

        /*sprCoun.setOnItemClickListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    shippingCost = 49;
                }
                else if(position == 2){
                    shippingCost = 100;
                }
                else if(position == 3){
                    shippingCost = 140;
                }

            }
        });*/

    }

    private void orderItemRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CheckoutActivity.this);
        orderItems.setLayoutManager(layoutManager);

        adapter = new CartItemsAdapter(this, cartItemsList);
        orderItems.setAdapter(adapter);
    }

    @OnClick(R.id.confirmationButton)
    public void confirmationBtnClicked(){
        //cartItemsList
        confirmationButton.setEnabled(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                registerOrderSync();
                Intent intent = new Intent(CheckoutActivity.this, MyOrdersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        };
        new Thread(runnable).start();


    }

    public void registerOrderSync(){
//        SharedPreferences sharedpreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedpreferences.edit();
        //editor.putInt("idUser", idUser);
        //editor.commit();

//        int userId = sharedpreferences.getInt("idUser",-1);
        int userId = Session.getUserId();

        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call = service.saveOrder(userId,shippingCost);
        //final User[] user = {null};
        User currentUser;


        try
        {
            Response<JsonObject> response = call.execute();
            //JsonObject jsonArrayOfOrders = response.body();

            //if(response.body().get("user") instanceof JsonArray)
            if(response.body().get("error").isJsonNull() != true) {
                JsonArray jsonArrayOfOrders = response.body().get("error").getAsJsonArray();

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

    public void listProductsAddToCart() {

        sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
//        userId = sharedpreferences.getInt("idUser",-1);
        userId = Session.getUserId();


        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call = service.getAddedToCart(userId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("cartItems").isJsonNull() != true) {
                    JsonArray jsonArrayOfOrders = response.body().get("cartItems").getAsJsonArray();

                    CartItem cartItem;
                    for (int i = 0; i < jsonArrayOfOrders.size(); i++) {
                        JsonObject jsonOrder = jsonArrayOfOrders.get(i).getAsJsonObject();
                        //int id = Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", ""));
                        //String[] productImagesUrls = listProductImages(id);
                        Integer qte = Integer.valueOf(String.valueOf(jsonOrder.get("qte")).replace("\"", ""));
                        Float unitPrice = Float.valueOf(String.valueOf(jsonOrder.get("price")).replace("\"", ""));

                        cartItem = new CartItem(Integer.valueOf(String.valueOf(jsonOrder.get("prod_id")).replace("\"", "")),
                                String.valueOf(jsonOrder.get("name")).replace("\"", ""),
                                String.valueOf(jsonOrder.get("url")).replace("\"", ""),
                                qte,
                                unitPrice
                        );
                        cartItemsList.add(cartItem);
                        confirmationButton.setEnabled(true);



                        totalPriceSum+= (unitPrice * qte);
                        Log.w("price", "prix total " + totalPriceSum);
                    }
                    totalPrice.setText(totalPriceSum + " MAD");
                    confirmationButton.setText("Confirmer ("+(totalPriceSum + shippingCost ) + " MAD)");

                    //Log.d("sipphotos", String.valueOf(orderItemsList.get(1).getName()));
                    orderItemRecycler();
                }
                else{
                    Log.d("response null", "not JsonArray");
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("siperror", "error in callback");
                Log.d("siperror", t.getMessage());
            }
        });

    }
}