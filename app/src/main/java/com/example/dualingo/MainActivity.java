package com.example.dualingo;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager vp=(ViewPager) findViewById(R.id.viewPager);
        CategoryPageAdapter cpa=new CategoryPageAdapter(getSupportFragmentManager());
        vp.setAdapter(cpa);
        TabLayout tb=findViewById(R.id.tabLayout);
        tb.setupWithViewPager(vp);
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tb));
    }
}
