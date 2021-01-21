package com.example.codemagictest;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductInfoActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.product_info);
        ButterKnife.bind(this);

        productInitializer();


    }
    private void productInitializer() {
        Product prod = (Product) getIntent().getSerializableExtra("productSelected");
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
}
