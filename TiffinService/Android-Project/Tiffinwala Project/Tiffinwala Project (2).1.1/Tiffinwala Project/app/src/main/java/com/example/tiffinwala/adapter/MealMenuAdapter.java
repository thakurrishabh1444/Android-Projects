package com.example.tiffinwala.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffinwala.R;
import com.example.tiffinwala.entity.Mealmenu;

import java.util.List;

public class MealMenuAdapter extends RecyclerView.Adapter<MealMenuAdapter.TiffinViewHolder> {
    private static final int TYPE_IMAGE_SLIDER = 0;
    private static final int TYPE_MENU_ITEM = 1;

    private List<Mealmenu> menuList;

    public MealMenuAdapter(List<Mealmenu> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public TiffinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meals_menu_layout, parent, false);
        return new TiffinViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TiffinViewHolder holder, int position) {

        Mealmenu menu = menuList.get(position);
        holder.tiffinTitleTextView.setText(menu.getTitle());
        holder.tiffinPriceTextView.setText(menu.getPrice());
        holder.tiffinImageView.setImageResource(menu.getImageResId());
    }

    @Override
    public int getItemCount() {
        return menuList.size();

    }


    public class TiffinViewHolder extends RecyclerView.ViewHolder {
        ImageView tiffinImageView;
        TextView tiffinTitleTextView;
        TextView tiffinPriceTextView;

        public TiffinViewHolder(View itemView) {
            super(itemView);
            tiffinImageView = itemView.findViewById(R.id.tiffinImage);
            tiffinTitleTextView = itemView.findViewById(R.id.tiffinTitle);
            tiffinPriceTextView = itemView.findViewById(R.id.tiffinPrice);
        }
    }
}