package com.example.codemagictest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codemagictest.API.APIInterface;
import com.example.codemagictest.API.ClientAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {

    private ArrayList<Product> productList = new ArrayList<>();
    ProductListAdapter adapter;
    @BindView(R.id.productsRecyclerView)
    RecyclerView productRecyclerView;

    @BindView(R.id.search_btn)
    ImageButton searchBtn;
    @BindView(R.id.searchBar)
    TextInputEditText searchBar;

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_activity);

        ButterKnife.bind(this);
        extras= getIntent().getExtras();

        String category=null;
        if(extras!=null){
            category = extras.getString("categorySelected");
        }

        listAllProducts(category);

        //productRecycler();

        //extras = getIntent().getExtras();
        /*if(extras!=null){

            String searchText = extras.getString("productName");
            (ProductsActivity.this).adapter.filter(searchText);
            productRecycler();
        }*/


    }

    private void productRecycler() {
        //productRecyclerView.setHasFixedSize(true);
        //productRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //RecyclerView productRecyclerView = findViewById(R.id.productRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductsActivity.this);
        productRecyclerView.setLayoutManager(layoutManager);

        //productList.add(new Product(1, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodone", 100, "this is prod one description", (float) 4.5));
        //productList.add(new Product(2, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodtwo", (float) 55.4, "this is prod two description", (float) 2.1));
        //productList.add(new Product(3, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodthree", 99, "this is prod three description", (float) 3));
        //productList.add(new Product(4, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodfour", 100, "this is prod four description", (float) 5));
        adapter = new ProductListAdapter(this, productList);
        productRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.search_btn)
    public void searchBtnClicked(){
        String searchText = searchBar.getText().toString();
        boolean blank = searchText.trim().isEmpty();
        if(blank) searchText="";
        Intent intent = new Intent(ProductsActivity.this, ProductsActivity.class);
        intent.putExtra("productName", searchText);
        this.startActivity(intent);

    }

    public void listAllProducts(String category) {
        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call=null;
        if(category==null){
            call = service.getProducts();
        }
        else {
            call = service.getProductsByCategory(category);
        }

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.body().get("products").isJsonNull() != true) {
                    JsonArray jsonArrayOfProducts = response.body().get("products").getAsJsonArray();

                    Product product;
                    for (int i = 0; i < jsonArrayOfProducts.size(); i++) {
                        JsonObject jsonOrder = jsonArrayOfProducts.get(i).getAsJsonObject();
                        //int id = Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", ""));

                        //String[] productImagesUrls = HomeActivity.listProductImages(id);
                        product = new Product(Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", "")),
                                new String[]{String.valueOf(jsonOrder.get("url")).replace("\"", "")},
                                String.valueOf(jsonOrder.get("name")).replace("\"", ""),
                                Float.valueOf(String.valueOf(jsonOrder.get("price")).replace("\"", "")),
                                String.valueOf(jsonOrder.get("description")).replace("\"", ""),
                                Float.valueOf(String.valueOf(jsonOrder.get("rating")).replace("\"", "")));
                        productList.add(product);
                    }
                    //Log.d("sipphotos", String.valueOf(productList.get(1).getName()));
                    productRecycler();
                    if (extras != null && category == null) {

                        String searchText = extras.getString("productName");
                        (ProductsActivity.this).adapter.filter(searchText);
                        productRecycler();
                    }
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
