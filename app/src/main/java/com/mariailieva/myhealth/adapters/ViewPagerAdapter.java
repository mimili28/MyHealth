package com.mariailieva.myhealth.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mariailieva.myhealth.fragments.ExploreFragment;
import com.mariailieva.myhealth.fragments.MyHealthFragment;
import com.mariailieva.myhealth.fragments.ProfileFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ProfileFragment();
            case 1:
                return new MyHealthFragment();
            case 2:
                return new ExploreFragment();
            default:
                return new ExploreFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
