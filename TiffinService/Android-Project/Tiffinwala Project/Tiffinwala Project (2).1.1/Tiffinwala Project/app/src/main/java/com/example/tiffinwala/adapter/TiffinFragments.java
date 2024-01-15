package com.example.tiffinwala.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tiffinwala.fragments.DetailsFragment;
import com.example.tiffinwala.fragments.homefragment;
import com.example.tiffinwala.fragments.profile;
import com.example.tiffinwala.fragments.subscriptions;

public class TiffinFragments extends FragmentStateAdapter {

    private boolean hasSubscribed;
    public TiffinFragments(@NonNull FragmentActivity fragmentActivity, boolean hasSubscribed) {
        super(fragmentActivity);
        this.hasSubscribed = hasSubscribed;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return hasSubscribed ? new DetailsFragment() : new subscriptions<>();
            case 1:
                return new homefragment();
            case 2:
                return new profile();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
