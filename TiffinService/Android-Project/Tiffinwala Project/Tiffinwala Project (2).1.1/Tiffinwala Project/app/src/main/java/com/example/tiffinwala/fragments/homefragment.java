package com.example.tiffinwala.fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.tiffinwala.R;
import com.example.tiffinwala.adapter.HomeAdapter;
import com.example.tiffinwala.entity.Mealmenu;

import java.util.ArrayList;
import java.util.List;

public class homefragment extends Fragment implements MenuItem.OnMenuItemClickListener {

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private List<Mealmenu> menuList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homefragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        menuList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getSlideModels(), menuList);

        recyclerView.setAdapter(homeAdapter);


        ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
        if (imageSlider != null) {
            ArrayList<SlideModel> slideModels = getSlideModels();
            imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        } else {
            Log.e(TAG, "ImageSlider is null " );
        }

        menuList.add(new Mealmenu("Veg Dabba", "₹60", R.drawable.veg));
        menuList.add(new Mealmenu("Non-veg Dabba", "₹100", R.drawable.nonveg));
        menuList.add(new Mealmenu("Hybrid", "₹80", R.drawable.hybrid));




        homeAdapter.notifyDataSetChanged();

        ImageView facebookImageView = view.findViewById(R.id.facebookImageView);
        ImageView twitterImageView = view.findViewById(R.id.twitterImageView);
        ImageView instagramImageView = view.findViewById(R.id.instagramImageView);

        // Set click listeners for social media icons
        facebookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.facebook.com/");
            }
        });

        twitterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://twitter.com/");
            }
        });

        instagramImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://www.instagram.com/");
            }
        });
    }

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }



    private ArrayList<SlideModel> getSlideModels() {
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.one, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.two, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.three, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.four, ScaleTypes.FIT));
        Log.e(TAG, "getSlideModels: ");
        return slideModels;
    }

    @Override
    public boolean onMenuItemClick(@NonNull MenuItem item) {
        Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
        return false;
    }
}
