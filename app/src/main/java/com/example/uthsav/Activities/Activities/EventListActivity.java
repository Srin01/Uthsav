package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.uthsav.Activities.Adapter.PageAdapter;
import com.example.uthsav.R;
import com.google.android.material.tabs.TabLayout;

public class EventListActivity extends AppCompatActivity
{
    Toolbar toolbar2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

//        toolbar2 = findViewById(R.id.toolbar3);
//        setSupportActionBar(toolbar2);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Unregistered Events"));
        tabLayout.addTab(tabLayout.newTab().setText("Registered Events"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });
    }

    public void onClickToolBar(View view)
    {
        startActivity(new Intent(this, HomeActivity.class));
    }
}