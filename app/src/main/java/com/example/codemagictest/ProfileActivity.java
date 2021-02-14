package com.example.codemagictest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.codemagictest.API.APIInterface;
import com.example.codemagictest.API.ClientAPI;
import com.example.codemagictest.Model.Product;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.userName)
    TextInputEditText userNameInputEditText;
    @BindView(R.id.login)
    TextInputEditText loginInputEditText;
    @BindView(R.id.adresse)
    TextInputEditText addresseInputEditText;
    @BindView(R.id.phone)
    TextInputEditText phoneInputEditText;
    @BindView(R.id.fullName)
    TextView fullNameTextView;

    @BindView(R.id.order_label)
    TextView orderCount;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        prefs = getSharedPreferences("login", Context.MODE_PRIVATE);
        String fullName = prefs.getString("fullName", "default");
        String login = prefs.getString("login", "default");
        String address = prefs.getString("userAddress", "default");
        countAllOrders();

        String phone = prefs.getString("phone", "default");

        userNameInputEditText.setText(fullName);
        fullNameTextView.setText(fullName);
        loginInputEditText.setText(login);
        addresseInputEditText.setText(address);
        phoneInputEditText.setText(phone);

    }

    public void countAllOrders() {
        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call= service.countOrders(Session.getUserId());

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.body().get("ordersCount").isJsonNull() != true) {
                    JsonArray jsonArrayOfProducts = response.body().get("ordersCount").getAsJsonArray();

                    Product product;
                    for (int i = 0; i < jsonArrayOfProducts.size(); i++) {
                        JsonObject jsonOrder = jsonArrayOfProducts.get(i).getAsJsonObject();
                        //int id = Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", ""));

                        //String[] productImagesUrls = HomeActivity.listProductImages(id);

                        orderCount.setText(String.valueOf(jsonOrder.get("numberoforders")).replace("\"", ""));
                        Log.w("tatata",String.valueOf(jsonOrder.get("numberoforders")).replace("\"", ""));
                    }
                    //Log.d("sipphotos", String.valueOf(productList.get(1).getName()));

                }else{
                    Log.d("response null", "not JsonArray");
                }
                //inflateRecyclerView(orders, context);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("siperror", "error in callback");
                Log.d("siperror", t.getMessage());
            }
        });

    }


}