package com.example.codemagictest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductsActivity extends AppCompatActivity {

    private ArrayList<Product> productList = new ArrayList<>();
    ProductListAdapter adapter;
    @BindView(R.id.productsRecyclerView)
    RecyclerView productRecyclerView;

    @BindView(R.id.search_btn)
    ImageButton searchBtn;
    @BindView(R.id.searchBar)
    TextInputEditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_activity);

        ButterKnife.bind(this);



        productRecycler();

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String searchText = extras.getString("productName");
            (ProductsActivity.this).adapter.filter(searchText);
        }

    }

    private void productRecycler() {
        //productRecyclerView.setHasFixedSize(true);
        //productRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //RecyclerView productRecyclerView = findViewById(R.id.productRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductsActivity.this);
        productRecyclerView.setLayoutManager(layoutManager);

        productList.add(new Product(1, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodone", 100, "this is prod one description", (float) 4.5));
        productList.add(new Product(2, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodtwo", (float) 55.4, "this is prod two description", (float) 2.1));
        productList.add(new Product(3, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodthree", 99, "this is prod three description", (float) 3));
        productList.add(new Product(4, new String[]{"https://i.imgur.com/NRLsmco.png"}, "prodfour", 100, "this is prod four description", (float) 5));
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
}
