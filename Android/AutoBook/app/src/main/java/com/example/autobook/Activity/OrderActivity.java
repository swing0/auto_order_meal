package com.example.autobook.Activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.autobook.Fragment.Order_Unpaid_Fragment;
import com.example.autobook.Fragment.Order_Post_Fragment;
import com.example.autobook.R;
import java.util.ArrayList;
import java.util.List;


public class OrderActivity extends FragmentActivity implements View.OnClickListener {
    private TextView back;
    private List<Fragment> myFragment;
    private ViewPager viewPager;
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        //initview();
        initPager();
    }

    private void initPager() {
        //初始化控件
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        back = (TextView) findViewById(R.id.back);
        //设置点击
        back.setOnClickListener(this);
        myFragment=new ArrayList<>();
        myFragment.add(new Order_Unpaid_Fragment());
        myFragment.add(new Order_Post_Fragment());
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return myFragment.get(position);
            }

            @Override
            public int getCount() {
                return myFragment.size();
            }
        };
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
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

    private void initview(){
        //白底黑字导航 appstyle颜色改为白色
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
