package com.example.codemagictest.API;



import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("/orders/selectOrdersByUserId.php")
    Call<JsonObject> getAllOrders(@Query("userID") int userID);

    @GET("/orders/selectCountOrdersByUserId.php")
    Call<JsonObject> countOrders(@Query("userID") int userID);

    @GET("/product/selectProducts.php")
    Call<JsonObject> getProducts();

    @GET("/product/selectHotProducts.php")
    Call<JsonObject> getHotProducts();

    @GET("/product/selectProductImageById.php")
    Call<JsonObject> getProductImages(@Query("productID") int productID);

    @GET("/product/selectProductsByCategoryName.php")
    Call<JsonObject> getProductsByCategory(@Query("categoryName") String categoryName);

    @GET("/category/selectCategories.php")
    Call<JsonObject> getCategories();

    @GET("/cart/selectAddedToCartByUserId.php")
    Call<JsonObject> getAddedToCart(@Query("userID") int userID);

    @GET("/user/selectUserByLoginAndPassword.php")
    Call<JsonObject> getUser(@Query("login") String login, @Query("password") String password);

    @GET("/cart/insertAddToCart.php")
    Call<JsonObject> putProdInCart(@Query("idUser") int userId, @Query("idProduct") int prodId, @Query("qte") int qte);

    @GET("/cart/deleteFromCart.php")
    Call<JsonObject> removeProdFromCart(@Query("idUser") int userId, @Query("idProduct") int prodId);

    @GET("/orders/insertOrderByUserId.php")
    Call<JsonObject> saveOrder(@Query("userID") int userId, @Query("shippingPrice") float shippingPrice);

}
