package com.example.codemagictest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        initCategoryRecyclerView();

        //
        productRecycler();


    }

    private void productRecycler() {
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView productRecyclerView = findViewById(R.id.productRecyclerView);
        productList.add(new Product(1, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodone", 100, "this is prod one description", (float) 4.5));
        productList.add(new Product(2, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodtwo", (float) 55.4, "this is prod two description", (float) 2.1));
        productList.add(new Product(3, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodthree", 99, "this is prod three description", (float) 3));
        productList.add(new Product(4, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodfour", 100, "this is prod four description", (float) 5));
        ProductAdapter adapter = new ProductAdapter(this, productList);
        productRecyclerView.setAdapter(adapter);
    }

    private void initCategoryRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        categoryList.add(new Category(1,"cattwo","https://i.imgur.com/ZcLLrkY.jpg"));
        categoryList.add(new Category(2,"catthree","https://i.imgur.com/ZcLLrkY.jpg"));
        categoryList.add(new Category(3,"catfive","https://i.imgur.com/ZcLLrkY.jpg"));
        categoryList.add(new Category(4,"catsix","https://i.imgur.com/ZcLLrkY.jpg"));
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
}
