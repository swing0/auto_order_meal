package com.example.autobook.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.autobook.Activity.OrderActivity;
import com.example.autobook.Activity.RatingActivity;
import com.example.autobook.R;

public class SelfFragment extends Fragment implements View.OnClickListener {
    private TextView account;
    private ImageView exitApp;
    private RadioButton btn_1,btn_3;



    public SelfFragment() { }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Initview();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_self, container, false);
    }

    public void Initview(){
        exitApp=(ImageView)getActivity().findViewById(R.id.exitApp);
        exitApp.setOnClickListener(this);
        account=(TextView)getActivity().findViewById(R.id.account);
        SharedPreferences sp = getActivity().getSharedPreferences("userConfig", PreferenceActivity.MODE_PRIVATE);
        account.setText("Account："+sp.getString("username",""));
        btn_1=(RadioButton)getActivity().findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_3=(RadioButton)getActivity().findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_1:
                Intent intent1=new Intent(getActivity(), RatingActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_3:
                Intent intent=new Intent(getActivity(), OrderActivity.class);
                startActivity(intent);
                break;
            case R.id.exitApp:
                getActivity().finish();
                System.exit(0);
                break;
        }
    }
}