package com.example.tiffinwala;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tiffinwala.Utils.RetroFitClient;
import com.example.tiffinwala.adapter.TiffinFragments;
import com.example.tiffinwala.entity.Order;
import com.example.tiffinwala.fragments.DetailsFragment;
import com.example.tiffinwala.fragments.subscriptions;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Toolbar toolBar;
    ViewPager2 viewPager2;
    TabLayout tabLayout;

    TiffinFragments tiffinFragments;

    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("shop", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);
        sharedPreferences = getSharedPreferences("shop", MODE_PRIVATE);

        // Retrieve the userId from shared preferences
        userId = sharedPreferences.getInt("userId", 0);


        toolBar = findViewById(R.id.toolBar);
        viewPager2 = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        tiffinFragments = new TiffinFragments(this,true);
//        viewPager2.setAdapter(tiffinFragments);
//        viewPager2.setCurrentItem(1);

        checkSubscriptionStatus();



//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                updateBackButtonVisibility(position);
//            }
//        });

    }


    private void checkSubscriptionStatus() {
        RetroFitClient.getInstance().getApi().checkAlreadySubscribeOrNot(userId).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
                // Check if the user has a subscription
                boolean hasSubscription = (array.size() > 0);

                if (hasSubscription) {

                    tiffinFragments = new TiffinFragments(MainActivity.this,true);
                    viewPager2.setAdapter(tiffinFragments);
                    viewPager2.setCurrentItem(1);

                    new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            switch (position) {
                                case 0:
                                    tab.setIcon(R.drawable.subscriptions);
                                    break;
                                case 1:
                                    tab.setIcon(R.drawable.home);
                                    break;
                                case 2:
                                    tab.setIcon(R.drawable.profile);
                                    break;

                            }

                        }
                    }).attach();
                    //loadUserDetailsFragment();
                } else {

                    tiffinFragments = new TiffinFragments(MainActivity.this,false);
                    viewPager2.setAdapter(tiffinFragments);
                    viewPager2.setCurrentItem(1);


                    new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            switch (position) {
                                case 0:
                                    tab.setIcon(R.drawable.subscriptions);
                                    break;
                                case 1:
                                    tab.setIcon(R.drawable.home);
                                    break;
                                case 2:
                                    tab.setIcon(R.drawable.profile);
                                    break;
                            }
                        }
                    }).attach();
                    //loadSubscriptionFragment();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Handle the failure case if needed
            }
        });
    }

    private void loadUserDetailsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recyclerView, new DetailsFragment())
                .commit();
    }


    private void loadSubscriptionFragment() {
        // Replace the first fragment container with the Subscription fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recyclerView, new subscriptions<>())
                .commit();
    }

    public void switchToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recyclerView, fragment)
                .commit();
    }

    private void updateBackButtonVisibility(int position) {
        if (position == 1) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        // Close the app when the back button is pressed
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            viewPager2.setCurrentItem(1);
            return true;
        } else if (item.getItemId() == R.id.logout) {
            getSharedPreferences("shop", MODE_PRIVATE).edit().putBoolean("status", false).apply();

            Toast.makeText(this, "LOG OUT CLICKED", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
