package com.example.autobook.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.autobook.Utils.OkhttpManager;
import com.example.autobook.R;
import com.smarttop.library.bean.AdressBean;
import com.smarttop.library.bean.City;
import com.smarttop.library.bean.County;
import com.smarttop.library.bean.Province;
import com.smarttop.library.bean.Street;
import com.smarttop.library.db.manager.AddressDictManager;
import com.smarttop.library.widget.AddressSelector;
import com.smarttop.library.widget.BottomDialog;
import com.smarttop.library.widget.OnAddressSelectedListener;
import com.squareup.okhttp.Request;

import java.io.IOException;


public class RegisterActivity extends Activity implements View.OnClickListener, OnAddressSelectedListener, AddressSelector.OnDialogCloseListener{

    private EditText tv_selector_area;
    private BottomDialog dialog;
    private String provinceCode;
    private String cityCode;
    private String countyCode;
    private String streetCode;
    private AddressDictManager addressDictManager;
    private EditText et_phone;
    private EditText et_nickname;
    private EditText password_1;
    private EditText password_2;
    private Button go_register;
    final static String url="http://192.168.0.104:8080/user/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Init();
        setData();
    }

    public void setData(){
        AddressSelector selector = new AddressSelector(this);
        //获取地址管理数据库
        addressDictManager = selector.getAddressDictManager();
        selector.setTextSize(14);//设置字体的大小
        selector.setTextSelectedColor(R.color.colorPrimary);//设置字体获得焦点的颜色
        selector.setTextUnSelectedColor(R.color.addressTextline);//设置字体没有获得焦点的颜色
        //获取数据库管理
        AddressDictManager addressDictManager = selector.getAddressDictManager();
        AdressBean.ChangeRecordsBean changeRecordsBean = new AdressBean.ChangeRecordsBean();
        changeRecordsBean.parentId = 0;
        changeRecordsBean.name = "测试省";
        changeRecordsBean.id = 35;
        addressDictManager.inserddress(changeRecordsBean);//对数据库里增加一个数据
        selector.setOnAddressSelectedListener(new OnAddressSelectedListener() {
            @Override
            public void onAddressSelected(Province province, City city, County county, Street street) {

            }
        });
    }
    public void Init(){
        tv_selector_area = (EditText) findViewById(R.id.tv_selector_area);
        et_phone=(EditText) findViewById(R.id.phone);
        et_nickname=(EditText) findViewById(R.id.nickname);
        password_1=(EditText) findViewById(R.id.password_1);
        password_2=(EditText) findViewById(R.id.password_2);
        go_register=(Button)findViewById(R.id.go_register);
        go_register.setOnClickListener(this);
        tv_selector_area.setFocusable(false);
        tv_selector_area.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_selector_area:
                if (dialog != null) {
                    dialog.show();
                } else {
                    dialog = new BottomDialog(this);
                    dialog.setOnAddressSelectedListener(this);
                    dialog.setDialogDismisListener(this);
                    dialog.setTextSize(14);//设置字体的大小
                    dialog.setIndicatorBackgroundColor(android.R.color.holo_orange_light);//设置指示器的颜色
                    dialog.setTextSelectedColor(android.R.color.holo_orange_light);//设置字体获得焦点的颜色
                    dialog.setTextUnSelectedColor(android.R.color.holo_blue_light);//设置字体没有获得焦点的颜色
                    dialog.show();
                }
                break;
            case R.id.go_register:
                if(checkData()){
                    JSONObject json=new JSONObject();
                    json.put("nickname",et_nickname.getText().toString());
                    json.put("password",password_1.getText().toString());
                    json.put("address",tv_selector_area.getText().toString());
                    json.put("account","YK_"+et_phone.getText().toString());
                    json.put("phone",et_phone.getText().toString());
                    try {
                        OkhttpManager.doPostHttpRequest(url, json.toString(), new OkhttpManager.DataCallBack() {
                            @Override
                            public void requestFailture(Request request, IOException e) {
                            }
                            @Override
                            public void requestSuccess(String result) {
                                JSONObject msg=JSONObject.parseObject(result);
                                if(msg.getString("msg").equals("请求成功")){
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.putExtra("account","YK_"+et_phone.getText().toString());
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(RegisterActivity.this, msg.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

    }

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        provinceCode = (province == null ? "" : province.code);
        cityCode = (city == null ? "" : city.code);
        countyCode = (county == null ? "" : county.code);
        streetCode = (street == null ? "" : street.code);
        String s = (province == null ? "" : province.name) + (city == null ? "" : city.name) + (county == null ? "" : county.name) +
                (street == null ? "" : street.name);
        tv_selector_area.setText(s);
        if (dialog != null) {
            dialog.dismiss();
        }
        getSelectedArea();
    }

    @Override
    public void dialogclose() {
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    /**
     * 根据code 来显示选择过的地区
     */
    private void getSelectedArea(){
        String province = addressDictManager.getProvince(provinceCode);
        String city = addressDictManager.getCity(cityCode);
        String county = addressDictManager.getCounty(countyCode);
        String street = addressDictManager.getStreet(streetCode);
        tv_selector_area.setText(province+city+county+street);
    }
    private boolean checkData(){
        if(et_phone.getText()==null||et_phone.getText().toString().equals("")){
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if(password_1.getText().toString().equals(password_2.getText().toString())){
                return true;
            }else {
                Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
}
