package com.mariailieva.myhealth.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mariailieva.myhealth.R;
import com.mariailieva.myhealth.adapters.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager2 = findViewById(R.id.viewpager);
        viewPager2.setAdapter(new ViewPagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tablayout);
        TabLayoutMediator tabLayoutMediator =new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: {
                        tab.setText("Profile");
                        tab.setIcon(R.drawable.ic_profile);
                        break;
                    }
                    case 1: {
                        tab.setText("My health");
                        tab.setIcon(R.drawable.ic_heart);
                        break;
                    }
                    case 2: {
                        tab.setText("Explore");
                        tab.setIcon(R.drawable.ic_explore);
                        break;
                    }
                }
            }
        }
        );
        tabLayoutMediator.attach();
    }
}
