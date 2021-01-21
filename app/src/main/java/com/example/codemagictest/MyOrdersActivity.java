package com.example.codemagictest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.codemagictest.API.APIInterface;
import com.example.codemagictest.API.ClientAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersActivity extends AppCompatActivity {

    RecyclerView ordersRecyclerView;
    List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        listAllOrders(this);
    }


    public void listAllOrders(Context context) {
        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call = service.getAllOrders(1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonArrayOfOrders = response.body().get("orders").getAsJsonArray();
                orders = new ArrayList<>();
                Order order;
                for (int i = 0; i < jsonArrayOfOrders.size(); i++) {
                    JsonObject jsonOrder = jsonArrayOfOrders.get(i).getAsJsonObject();
                    order = new Order(String.valueOf(jsonOrder.get("name")).replace("\"", ""),
                            String.valueOf(jsonOrder.get("image")).replace("\"", ""),
                            String.valueOf(jsonOrder.get("orderStatus")).replace("\"", ""));
                    orders.add(order);
                }
                Log.d("sipphotos", String.valueOf(orders.get(1).getName()));
                inflateRecyclerView(orders, context);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("siperror", "error in callback");
                Log.d("siperror", t.getMessage());
            }
        });

    }

    public void inflateRecyclerView(List<Order> orders, Context context) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ordersRecyclerView = findViewById(R.id.orders_recyclerview);
                OrdersAdapter adapter = new OrdersAdapter(getApplicationContext(), orders);
                ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                ordersRecyclerView.setAdapter(adapter);
            }
        });

    }
}