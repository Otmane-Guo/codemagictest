package com.example.codemagictest;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.ACTIVITY_SERVICE;

public class MenuFragment extends Fragment {

    @BindView(R.id.navigation_menu)
    BottomNavigationView bottomNavigationView;

    public static MenuFragment newInstance(String param1, String param2){
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.navigation_menu, container, false);
        ButterKnife.bind(this, v);
        bottomNavigationView.setSelectedItemId(R.id.home);
        ActivityManager am = (ActivityManager)v.getContext().getSystemService(ACTIVITY_SERVICE);
        List< ActivityManager.RunningTaskInfo > taskInfo = am.getRunningTasks(1);
        String currentActivity = taskInfo.get(0).topActivity.getClassName();
        Log.d( "CURRENT Activity ",  currentActivity);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Toast.makeText(v.getContext(), "TO Home Activity ! ", Toast.LENGTH_SHORT).show();
                        if(!currentActivity.equals("com.example.codemagictest.HomeActivity"))
                            startActivity(new Intent(v.getContext(), HomeActivity.class));
                        return true;
                    case R.id.hotDeals:
                        Toast.makeText(v.getContext(), "TO HotDeals Activity ! ", Toast.LENGTH_SHORT).show();
                        //if(!currentActivity.equals("com.example.codemagictest.HotDealsActivity"))
                            //startActivity(new Intent(v.getContext(), HotDealsActivity.class));
                        return true;
                    case R.id.cart:
                        Toast.makeText(v.getContext(), "TO Cart Activity ! ", Toast.LENGTH_SHORT).show();
                        //if(!currentActivity.equals("com.example.codemagictest.CartActivity"))
                            //startActivity(new Intent(v.getContext(), CartActivity.class));
                        return true;
                    case R.id.hotProducts:
                        Toast.makeText(v.getContext(), "TO HotProducts Activity ! ", Toast.LENGTH_SHORT).show();
                        //if(!currentActivity.equals("com.example.codemagictest.HotProductsActivity"))
                            //startActivity(new Intent(v.getContext(), HotProductsActivity.class));
                        return true;
                    case R.id.user:
                        Toast.makeText(v.getContext(), "TO User Activity ! ", Toast.LENGTH_SHORT).show();
                        //if(!currentActivity.equals("com.example.codemagictest.UserActivity"))
                            //startActivity(new Intent(v.getContext(), UserActivity.class));
                        return true;
                }
                return false;
            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
