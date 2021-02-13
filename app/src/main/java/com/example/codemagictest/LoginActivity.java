package com.example.codemagictest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.codemagictest.API.APIInterface;
import com.example.codemagictest.API.ClientAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.loginButton)
    Button button;
    @BindView(R.id.login)
    TextInputEditText login;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.checkbox)
    CheckBox rememberMe;

    SharedPreferences prefs;
    static User user;
    //public static int insert=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



        prefs = getSharedPreferences("login",Context.MODE_PRIVATE);
        String login = prefs.getString("login", "default");
        String password = prefs.getString("password", "default");
        if(login!="default" && password!="default")
        {
            this.login.setText(login);
            this.password.setText(password);
            this.rememberMe.setChecked(true);
            Toast.makeText(LoginActivity.this, "Already connected", Toast.LENGTH_SHORT).show();



            Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(myIntent);
        }

    }

    @OnClick(R.id.loginButton)
    public void onLoginClicked(){
        String loginText = this.login.getText().toString();
        String passwordText = this.password.getText().toString();
        System.out.println(loginText + ":" + passwordText);

        //User user = getUserfromDB(loginText, passwordText);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                getUserfromDBSync(loginText, passwordText);
//                int userId = prefs.getInt("idUser",-1);
                int userId = Session.getUserId();
                Log.d("---userID/login", user ==null?"user is NULL": user.getId()+""+ user.getLogin()+ " seesion :" + userId + "real session "+Session.getUserId());
                if(userId != -1){
                    //SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                    //Toast.makeText(LoginActivity.this, "You are connected successfully", Toast.LENGTH_SHORT).show();
                    Log.d("login", "You are connected successfully");
                    //Intent myIntent = new Intent(MainActivity.this, CategoryActivity.class);
                    //startActivity(myIntent);

                    //prefs.edit().putInt("idUser", user.getId()).commit();
                    if(rememberMe.isChecked()){

                           prefs.edit().putString("login", loginText).commit();
                        prefs.edit().putString("password", passwordText).commit();

                    }
                    Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myIntent);

                    System.out.println("connected-----");
                }
                else{

                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Email or password invalid", Toast.LENGTH_SHORT).show();
                            }
                        });

                    //Toast.makeText(LoginActivity.this, "Email or password invalid", Toast.LENGTH_SHORT).show();
                    Log.d("login ", "Email or password invalid");
                }

            }
        };
        new Thread(runnable).start();

        Log.d("---userID/login2", " seesion :" + Session.getUserId());


    }


    public User getUserfromDB(String login, String password ) {
        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call = service.getUser(login, password);
        //final User[] user = {null};
        User currentUser;

        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //JsonArray jsonArrayOfOrders = response.body().get("user").getAsJsonArray();


                //if(response.body().get("user") instanceof JsonArray)
                if(response.body().get("user").isJsonNull() != true)
                {
                    JsonArray jsonArrayOfOrders = response.body().get("user").getAsJsonArray();
                    user=null;
                    for (int i = 0; i < jsonArrayOfOrders.size(); i++) {
                        JsonObject jsonOrder = jsonArrayOfOrders.get(i).getAsJsonObject();
                        //int id = Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", ""));
                        //String[] productImagesUrls = listProductImages(id);
                        if(jsonOrder!=null){
                            user = new User(Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", "")),
                                    String.valueOf(jsonOrder.get("firstName")).replace("\"", ""),
                                    String.valueOf(jsonOrder.get("lastName")).replace("\"", ""),
                                    String.valueOf(jsonOrder.get("address")).replace("\"", ""),
                                    String.valueOf(jsonOrder.get("tel")).replace("\"", ""),
                                    String.valueOf(jsonOrder.get("password")).replace("\"", ""),
                                    String.valueOf(jsonOrder.get("login")).replace("\"", "")
                            );
                            if(rememberMe.isChecked()){

                                prefs.edit().putInt("idUser", user.getId()).commit();
                                Session.setUserId(user.getId());
                            }else{
                                Session.setUserId(user.getId());
                            }


                            //setUser(user);
                        }

                    }
                }
                else {
                    Log.d("response null", "not JsonArray");
                }

                //Log.d("userID/login", user[0] ==null?"user is NULL": user[0].getId()+""+ user[0].getLogin());
                //productRecycler();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("siperror", "error in callback");
                Log.d("siperror", t.getMessage());
            }
        });
        //Log.d("login userID shared", user ==null?"user is NULL": user.getId()+""+ user.getLogin());
        Log.d("login userID shared", Session.getUserId()+"");
        return user;
    }

    public void getUserfromDBSync(String login, String password ) {
        APIInterface service = ClientAPI.getClient().create(APIInterface.class);
        Call<JsonObject> call = service.getUser(login, password);
        //final User[] user = {null};
        User currentUser;


        try
        {
            Response<JsonObject> response = call.execute();
            //JsonObject jsonArrayOfOrders = response.body();

            //if(response.body().get("user") instanceof JsonArray)
            if(response.body().get("user").isJsonNull() != true)
            {
                JsonArray jsonArrayOfOrders = response.body().get("user").getAsJsonArray();
                user=null;
                for (int i = 0; i < jsonArrayOfOrders.size(); i++) {
                    JsonObject jsonOrder = jsonArrayOfOrders.get(i).getAsJsonObject();
                    //int id = Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", ""));
                    //String[] productImagesUrls = listProductImages(id);
                    if(jsonOrder!=null){
                        user = new User(Integer.parseInt(String.valueOf(jsonOrder.get("id")).replace("\"", "")),
                                String.valueOf(jsonOrder.get("firstName")).replace("\"", ""),
                                String.valueOf(jsonOrder.get("lastName")).replace("\"", ""),
                                String.valueOf(jsonOrder.get("address")).replace("\"", ""),
                                String.valueOf(jsonOrder.get("tel")).replace("\"", ""),
                                String.valueOf(jsonOrder.get("password")).replace("\"", ""),
                                String.valueOf(jsonOrder.get("login")).replace("\"", "")
                        );
                        if(rememberMe.isChecked()){

                            prefs.edit().putInt("idUser", user.getId()).commit();
                            Session.setUserId(user.getId());
                        }else{
                            Session.setUserId(user.getId());
                        }
                        prefs.edit().putString("userAddress", user.getAddress()).commit();
                        prefs.edit().putString("login", user.getLogin()).commit();
                        prefs.edit().putString("fullName", user.getFirstName() + " " + user.getLastName()).commit();
                        prefs.edit().putString("phone", user.getTel()).commit();

//                        prefs.edit().putInt("idUser", user.getId()).commit();
//                        Log.d("response not null", "JsonArray user"+user.getLogin());
                        //setUser(user);
                    }

                }
            }
            else {
                Toast.makeText(LoginActivity.this, "Email or password invalid", Toast.LENGTH_SHORT).show();
                Log.d("response null", "not JsonArray");
            }
            //API response
            //System.out.println(jsonArrayOfOrders);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
  
}