package com.example.codemagictest.API;



import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("/APIs/EcomAppAPI/orders/selectOrdersByUserId.php")
    Call<JsonObject> getAllOrders(@Query("userID") int userID);

    @GET("/APIs/EcomAppAPI/product/selectProducts.php")
    Call<JsonObject> getProducts();

    @GET("/APIs/EcomAppAPI/product/selectHotProducts.php")
    Call<JsonObject> getHotProducts();

    @GET("/APIs/EcomAppAPI/product/selectProductImageById.php")
    Call<JsonObject> getProductImages(@Query("productID") int productID);

    @GET("/APIs/EcomAppAPI/product/selectProductsByCategoryName.php")
    Call<JsonObject> getProductsByCategory(@Query("categoryName") String categoryName);

    @GET("/APIs/EcomAppAPI/category/selectCategories.php")
    Call<JsonObject> getCategories();
}
