package com.example.codemagictest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codemagictest.API.APIInterface;
import com.example.codemagictest.API.ClientAPI;
import com.example.codemagictest.Adapter.CategoryAdapter;
import com.example.codemagictest.Adapter.ProductAdapter;
import com.example.codemagictest.Model.Category;
import com.example.codemagictest.Model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private ArrayList<Category> categoryList = new ArrayList<>();
    private ArrayList<Product> productList = new ArrayList<>();
    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;
    @BindView(R.id.view_all_product_btn)
    TextView viewProducts;
    @BindView(R.id.search_btn)
    ImageButton searchBtn;
    @BindView(R.id.searchBar)
    TextInputEditText searchBar;
    @BindView(R.id.navigation_menu)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);
        //bottomNavigationView.setSelectedItemId(R.id.home);
        listCategories();
        listHotProducts();
        //
        //productRecycler();


    }

    private void productRecycler() {
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView productRecyclerView = findViewById(R.id.productRecyclerView);

        //productList.add(new Product(1, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodone", 100, "this is prod one description", (float) 4.5));
        //productList.add(new Product(2, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodtwo", (float) 55.4, "this is prod two description", (float) 2.1));
        //productList.add(new Product(3, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodthree", 99, "this is prod three description", (float) 3));
        //productList.add(new Product(4, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodfour", 100, "this is prod four description", (float) 5));
        ProductAdapter adapter = new ProductAdapter(this, productList);
        productRecyclerView.setAdapter(adapter);
    }

    private void initCategoryRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        //categoryList.add(new Category(1,"cattwo","https://i.imgur.com/ZcLLrkY.jpg"));
        //categoryList.add(new Category(2,"catthree","https://i.imgur.com/ZcLLrkY.jpg"));
        //categoryList.add(new Category(3,"catfive","https://i.imgur.com/ZcLLrkY.jpg"));
        //categoryList.add(new Category(4,"catsix","https://i.imgur.com/ZcLLrkY.jpg"));
        CategoryAdapter adapter = new CategoryAdapter(this, categoryList);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.view_all_product_btn)
    public void viewAllProducts(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        Intent intent = new Intent(HomeActivity.this, ProductsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.search_btn)
    public void searchBtnClicked(){
        String searchText = searchBar.getText().toString();
        boolean blank = searchText.trim().isEmpty();
        if(blank) searchText="";
        Intent intent = new Intent(HomeActivity.this, ProductsActivity.class);
        intent.putExtra("productName", searchText);
        HomeActivity.this.startActivity(intent);

    }

    public static String[]  listProductImages(int productID) {
        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call = service.getProductImages(productID);
        String[] productImagesUrls = {};
        try {
            Response<JsonObject> response = call.execute();
            JsonObject productImages = response.body();
            Log.d("productImages","hello");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                JsonArray jsonArrayOfOrders = response.body().get("productImage").getAsJsonArray();
//
//
//                for (int i = 0; i < jsonArrayOfOrders.size(); i++) {
//                    JsonObject jsonOrder = jsonArrayOfOrders.get(i).getAsJsonObject();
//                    //int id = Integer.valueOf(String.valueOf(jsonOrder.get("id")));
//                    Log.d("unic", String.valueOf(jsonOrder.get("url")).replace("\"", ""));
//                    productImagesUrls[i] = String.valueOf(jsonOrder.get("url")).replace("\"", "");
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Log.d("siperror", "error in callback");
//                Log.d("siperror", t.getMessage());
//            }
//        });
        return productImagesUrls;

    }

    public void listHotProducts() {
        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call = service.getHotProducts();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonArrayOfOrders = response.body().get("products").getAsJsonArray();

                Product product;
                for (int i = 0; i < jsonArrayOfOrders.size(); i++) {
                    JsonObject jsonOrder = jsonArrayOfOrders.get(i).getAsJsonObject();
                    //int id = Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", ""));
                    //String[] productImagesUrls = listProductImages(id);
                    product = new Product(Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", "")),
                            new String[]{String.valueOf(jsonOrder.get("url")).replace("\"", "")},
                            String.valueOf(jsonOrder.get("name")).replace("\"", ""),
                            Float.valueOf(String.valueOf(jsonOrder.get("price")).replace("\"", "")),
                            String.valueOf(jsonOrder.get("description")).replace("\"", ""),
                            Float.valueOf(String.valueOf(jsonOrder.get("rating")).replace("\"", "")));
                    productList.add(product);
                }
                Log.d("sipphotos", String.valueOf(productList.get(1).getName()));
                productRecycler();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("siperror", "error in callback");
                Log.d("siperror", t.getMessage());
            }
        });

    }

    public void listCategories(){
        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call = service.getCategories();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonArrayOfCategory = response.body().get("categories").getAsJsonArray();

                Category category;
                for (int i = 0; i < jsonArrayOfCategory.size(); i++) {
                    JsonObject jsonOrder = jsonArrayOfCategory.get(i).getAsJsonObject();
                    //int id = Integer.valueOf(String.valueOf(jsonOrder.get("id")));
                    category = new Category(Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", "")),
                            String.valueOf(jsonOrder.get("name")).replace("\"", ""),
                            String.valueOf(jsonOrder.get("image")).replace("\"", "")
                            );
                    categoryList.add(category);
                }
                //Log.d("sipphotos", String.valueOf(productList.get(1).getName()));
                initCategoryRecyclerView();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("siperror", "error in callback");
                Log.d("siperror", t.getMessage());
            }
        });

    }
}
