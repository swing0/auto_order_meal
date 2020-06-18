package com.example.autobook.Activity;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


import androidx.drawerlayout.widget.DrawerLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.autobook.Adapter.DishAdapter;
import com.example.autobook.Adapter.ReferAdapter;
import com.example.autobook.Bean.Dish;
import com.example.autobook.Bean.Refer_Dish;
import com.example.autobook.Bean.Restaurant;
import com.example.autobook.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ResturantActivity extends Activity implements View.OnClickListener {

    //定义控件
    private NumberPicker MAX;
    private DrawerLayout drawerLayout;
    private Button refer, get_dish;
    private ImageView call_phone;
    private TextView resturant_name, resturant_phone, Allprice;
    private ListView expandableListView;
    private GridView Dishs;
    private CheckBox main_check_all;


    private int Restaurant_id;
    Restaurant restaurant = new Restaurant();
    //餐厅适配器数据源
    public List<Dish> DishList = new ArrayList<>();
    //服务器端口地址
    final static String url1 = "http://192.168.0.104:8080/dish/restaurant_id/";
    String url2 = null;
    //okhttp请求器
    private OkHttpClient client = new OkHttpClient();
    //餐厅推荐菜品适配器数据源
    private List<Refer_Dish> ReferDishList = new ArrayList<>();
    private ReferAdapter referAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);
        GetSelectInfo();
        initView();
        InitDishData();
        Dishs.setAdapter(new DishAdapter(getApplicationContext(), DishList));
    }

    /**
     * 初始化控件
     */
    public void initView() {
        main_check_all = (CheckBox) findViewById(R.id.main_check_all);
        Allprice = (TextView) findViewById(R.id.Allprice);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        refer = (Button) findViewById(R.id.refer);
        refer.setOnClickListener(this);
        get_dish = (Button) findViewById(R.id.get_dish);
        get_dish.setOnClickListener(this);
        call_phone = (ImageView) findViewById(R.id.call_phone);
        call_phone.setOnClickListener(this);
        resturant_name = (TextView) findViewById(R.id.resturant_name);
        resturant_name.setText(restaurant.getName());
        resturant_phone = (TextView) findViewById(R.id.resturant_phone);
        resturant_phone.setText(restaurant.getPhone());
        Dishs = (GridView) findViewById(R.id.dish_gridview);
        expandableListView = (ListView) findViewById(R.id.expand_able_view);
        MAX = (NumberPicker) findViewById(R.id.MAX);
        MAX.setMaxValue(50);
        MAX.setMinValue(0);
        MAX.setValue(12);
        main_check_all.setOnClickListener(this);

    }

    /**
     * 接受ListView传来的数据
     */
    public void GetSelectInfo() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Restaurant_id = bundle.getInt("Rest_id");
        url2="http://192.168.0.104:8080/dish/"+Restaurant_id+"/";
        restaurant = (Restaurant) bundle.getSerializable("Restaurant");
    }

    public void changeAllPrice() {
        int nowsum = 0;
        int Allsum = 0;
        for (int mark = 0; mark < ReferDishList.size(); mark++) {
            if (ReferDishList.get(mark).isCheck()) {
                nowsum += ReferDishList.get(mark).getPrice();
            }
            Allsum += ReferDishList.get(mark).getPrice();
        }
        if (nowsum == Allsum) {
            main_check_all.setChecked(true);
        } else {
            main_check_all.setChecked(false);
        }
        Allprice.setText(String.valueOf(nowsum) + "");
    }

    /**
     * 请求服务器菜品数据
     */
    public void InitDishData() {
        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url1 + Restaurant_id).build();
                //发送请求
                try {
                    final Response response = client.newCall(request).execute();
                    initlist(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void initlist(String data) {
        JSONArray array = (JSONArray) JSONArray.parse(data);
        for (int mark = 0; mark < array.size(); mark++) {
            JSONObject res = array.getJSONObject(mark);
            Dish dish = new Dish(res.getInteger("id"), res.getString("name"), res.getInteger("price"),
                    res.getInteger("restaurant_id"), res.getInteger("sales_volume"), res.getInteger("scoring_times"),
                    res.getInteger("total_score"), res.getString("classification"), res.getString("cuisine"), ReturnBitmap(res.getString("image")));
            DishList.add(dish);
        }
    }

    public void initReferlist(String data) {
        Log.d("data",data);
        JSONArray array = (JSONArray) JSONArray.parse(data);
        for (int mark = 0; mark < array.size(); mark++) {
            JSONObject res = array.getJSONObject(mark);
            Refer_Dish k = new Refer_Dish();
            k.setCheck(false);
            k.setId(res.getIntValue("id"));
            k.setName(res.getString("name"));
            k.setPrice(res.getIntValue("price"));
            k.setImage(ReturnBitmap(res.getString("image")));
            ReferDishList.add(k);
        }
    }

    /*
    展示当前菜品的选中状态
     */

    public void showToast(){
        int sum=0;
        for(Refer_Dish l:ReferDishList){
            if(l.isCheck()){
                sum+=1;
            }
        }
        Toast toast=Toast.makeText(this,"您当前选中"+String.valueOf(sum)+"道菜！",Toast.LENGTH_SHORT);
        showMyToast(toast,1000);
    }
    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }

    public Bitmap ReturnBitmap(final String Img_url) {
        URL imageurl = null;
        try {
            imageurl = new URL(Img_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) imageurl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setDoInput(true);
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = null;
        try {
            is = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmp = BitmapFactory.decodeStream(is);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmp;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + resturant_phone.getText()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.refer:
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
            case R.id.get_dish:
                ReferDishList.clear();
                main_check_all.setChecked(false);
                Allprice.setText(String.valueOf(0));
                InitReferDishData();
                referAdapter = new ReferAdapter(getApplicationContext(), ReferDishList);
                referAdapter.notifyDataSetChanged();
                expandableListView.setAdapter(referAdapter);
                referAdapter.setCheck(new ReferAdapter.oncheck() {
                    @Override
                    public void Checkedchanged(int position, boolean ischecked) {
                        ReferDishList.get(position).setCheck(ischecked);
                        referAdapter.notifyDataSetChanged();
                        changeAllPrice();
                        showToast();
                    }
                });
                referAdapter.notifyDataSetChanged();
                break;
            case R.id.main_check_all:
                if(ReferDishList.size()!=0){
                    for (int i = 0; i < ReferDishList.size(); i++) {
                        ReferDishList.get(i).setCheck(main_check_all.isChecked());
                    }
                    referAdapter.notifyDataSetChanged();
                    changeAllPrice();
                    showToast();
                }else {
                    Toast a=Toast.makeText(this,"暂无推荐！",Toast.LENGTH_SHORT);
                    showMyToast(a,1000);
                    main_check_all.setChecked(false);
                }
                break;
        }
    }

    /**
     * 请求推荐菜品
     */
    public void InitReferDishData() {
        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url2 + String.valueOf(MAX.getValue())).build();
                //发送请求
                try {
                    final Response response = client.newCall(request).execute();
                    //Log.d("菜品信息",response.body().string());
                    initReferlist(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
