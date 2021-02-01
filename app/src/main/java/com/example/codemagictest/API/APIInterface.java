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

    @GET("/APIs/EcomAppAPI/cart/selectAddedToCartByUserId.php")
    Call<JsonObject> getAddedToCart(@Query("userID") int userID);

    @GET("/APIs/EcomAppAPI/user/selectUserByLoginAndPassword.php")
    Call<JsonObject> getUser(@Query("login") String login, @Query("password") String password);

    @GET("/APIs/EcomAppAPI/cart/insertAddToCart.php")
    Call<JsonObject> putProdInCart(@Query("idUser") int userId, @Query("idProduct") int prodId, @Query("qte") int qte);

    @GET("/APIs/EcomAppAPI/cart/deleteFromCart.php")
    Call<JsonObject> removeProdFromCart(@Query("idUser") int userId, @Query("idProduct") int prodId);

}
