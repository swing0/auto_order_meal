package com.example.autobook.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.autobook.Fragment.Order_Post_Fragment;
import com.example.autobook.Fragment.Order_Unpaid_Fragment;
import com.example.autobook.R;

import java.util.ArrayList;
import java.util.List;


public class OrderActivity extends FragmentActivity implements View.OnClickListener {
    private TextView back;
    private int position=0;
    private List<Fragment> fragment;
    private FrameLayout frameLayout;
    private RadioButton rdbtn_1,rdbtn_2;
    private RadioGroup rgMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initview();
        initfragment();
        initListener();
    }
    public void initview(){
        frameLayout=(FrameLayout)findViewById(R.id.fragment);
        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(this);
        rgMain=(RadioGroup)findViewById(R.id.rgMain);
        rdbtn_1=(RadioButton)findViewById(R.id.rdbtn_1);
        rdbtn_2=(RadioButton)findViewById(R.id.rdbtn_2);
    }
    private void initfragment(){
        fragment=new ArrayList<>();
        fragment.add(new Order_Unpaid_Fragment());
        fragment.add(new Order_Post_Fragment());
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rdbtn_1:
                        position=0;
                        change(position);
                        break;
                    case R.id.rdbtn_2:
                        position=1;
                        change(position);
                        break;
                    default:
                        position=0;
                        change(position);
                        break;
                }
            }
        });

        rgMain.check(R.id.rdbtn_1);
    }
    /*
    切换页面
     */
    public void change(int local){
        if(local==0){
            Order_Unpaid_Fragment fragment=new Order_Unpaid_Fragment();
            Bundle bundle = new Bundle();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment,fragment);
            fragmentTransaction.commit();
        }else{
            Order_Post_Fragment fragment=new Order_Post_Fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment,fragment);
            fragmentTransaction.commit();
        }
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
