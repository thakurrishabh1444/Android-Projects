package com.example.tiffinwala.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.tiffinwala.R;
import com.example.tiffinwala.entity.Mealmenu;
import com.example.tiffinwala.fragments.subscriptions;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_SLIDER = 0;
    private static final int VIEW_TYPE_MENU_ITEM = 1;

    private List<SlideModel> slideModels;
    private List<Mealmenu> menuList;

    public HomeAdapter(List<SlideModel> slideModels, List<Mealmenu> menuList) {
        this.slideModels = slideModels;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_SLIDER) {
            View sliderView = inflater.inflate(R.layout.image_slider_layout, parent, false);
            return new SliderViewHolder(sliderView);
        } else {
            View menuItemView = inflater.inflate(R.layout.meals_menu_layout, parent, false);
            return new MenuItemViewHolder(menuItemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SLIDER) {
            SliderViewHolder sliderViewHolder = (SliderViewHolder) holder;
            sliderViewHolder.bindSliderData();
        } else {
            MenuItemViewHolder menuItemViewHolder = (MenuItemViewHolder) holder;
            int menuPosition = position - 1; // Account for the slider
            menuItemViewHolder.bindMenuItemData(menuList.get(menuPosition));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.recyclerView, new subscriptions<>()); // Replace with your SubscriptionFragment class
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        // Total count of items including both slider and menu items
        return 1 + menuList.size(); // 1 for the slider
    }

    @Override
    public int getItemViewType(int position) {
        // Return view type based on position
        if (position == 0) {
            return VIEW_TYPE_SLIDER;
        } else {
            return VIEW_TYPE_MENU_ITEM;
        }
    }

    // ViewHolder for the ImageSlider
    class SliderViewHolder extends RecyclerView.ViewHolder {
         ImageSlider imageSlider;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSlider = itemView.findViewById(R.id.imageSlider);
        }

        public void bindSliderData() {
            imageSlider.setImageList(slideModels);
        }
    }

    // ViewHolder for menu items
    class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView tiffinImage;
        private TextView tiffinTitle;
        private TextView tiffinPrice;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tiffinImage = itemView.findViewById(R.id.tiffinImage);
            tiffinTitle = itemView.findViewById(R.id.tiffinTitle);
            tiffinPrice = itemView.findViewById(R.id.tiffinPrice);
        }

        public void bindMenuItemData(Mealmenu mealmenu) {
            tiffinImage.setImageResource(mealmenu.getImageResId());
            tiffinTitle.setText(mealmenu.getTitle());
            tiffinPrice.setText(mealmenu.getPrice());


        }
    }
}

