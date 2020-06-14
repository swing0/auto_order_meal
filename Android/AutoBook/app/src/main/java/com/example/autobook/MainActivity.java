package com.example.autobook;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.autobook.Fragment.MainFragment;
import com.example.autobook.Fragment.SelfFragment;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> list;
    private LinearLayout id_tab_main;
    private LinearLayout id_tab_self;
    private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        InitEvent();
    }
    public void InitEvent(){
        id_tab_main.setOnClickListener(this);
        id_tab_self.setOnClickListener(this);

    }

    public void Init(){
        //初始化控件
        id_tab_main=(LinearLayout)findViewById(R.id.id_tab_main);
        id_tab_self=(LinearLayout)findViewById(R.id.id_tab_self);
        viewPager=(ViewPager) findViewById(R.id.view_pager);
        text=(TextView)findViewById(R.id.user);
        SharedPreferences sp = getSharedPreferences("userConfig", PreferenceActivity.MODE_PRIVATE);
        text.setText(sp.getString("nickname",""));

        list=new ArrayList<Fragment>();
        MainFragment mainFragment=new MainFragment();
        SelfFragment selfFragment=new SelfFragment();
        list.add(mainFragment);
        list.add(selfFragment);

        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };
        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //id_tab_main.setBackgroundColor(Color.parseColor("#ffffff") );
                viewPager.setCurrentItem(viewPager.getCurrentItem());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_tab_main:
                viewPager.setCurrentItem(0);
                break;
            case R.id.id_tab_self:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
