package com.example.autobook.Activity;



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
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


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.autobook.Adapter.DishAdapter;
import com.example.autobook.Adapter.ReferAdapter;
import com.example.autobook.Bean.Dish;
import com.example.autobook.Bean.Refer_Dish;
import com.example.autobook.Bean.Restaurant;
import com.example.autobook.MainActivity;
import com.example.autobook.R;
import com.example.autobook.Utils.OkhttpManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private Button refer, get_dish,btn_buy;
    private ImageView call_phone;
    private TextView resturant_name, resturant_phone, Allprice;
    private ListView expandableListView;
    private GridView Dishs;
    private CheckBox main_check_all;
    //当前顾客已经加购的菜品
    public List<Refer_Dish> NowsDishes=new ArrayList<>();
    //图片缓存地址
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/cache/imge";
    private int Restaurant_id;
    Restaurant restaurant = new Restaurant();
    //餐厅适配器数据源
    public List<Dish> DishList = new ArrayList<>();
    //服务器端口地址
    final static String url1 = "http://192.168.0.104:8080/dish/restaurant_id/";
    final static String url_order = "http://192.168.0.104:8080/order/register";
    String url2 = null;
    //okhttp请求器
    private OkHttpClient client = new OkHttpClient();
    //餐厅推荐菜品适配器数据源
    private List<Refer_Dish> ReferDishList = new ArrayList<>();
    private ReferAdapter referAdapter;
    private DishAdapter dishAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);
        GetSelectInfo();
        initView();
        InitDishData();
        dishAdapter=new DishAdapter(getApplicationContext(), DishList);
        Dishs.setAdapter(dishAdapter);
        dishAdapter.setOnCarClick(new DishAdapter.OnCarClick() {
            @Override
            public void ClickAdd(int position) {
                if(!NowsDishes.contains(DishList.get(position))){
                    Refer_Dish p=new Refer_Dish(DishList.get(position).getId(),DishList.get(position).getName(),
                            DishList.get(position).getPrice(),DishList.get(position).getImage(),true);
                    NowsDishes.add(p);
                }
            }
        });
    }


    public void saveMyBitmap(String bitName, Bitmap mBitmap) throws IOException {
        File f = new File(Environment.getExternalStorageDirectory()+ "/Pictures/" + bitName + ".png");
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化控件
     */
    public void initView() {
        btn_buy = (Button) findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener(this);
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
                    Message result=new Message();
                    result.obj=response.body().string();
                    this_handler.sendMessage(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public Handler this_handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initlist((String) msg.obj);
        }
    };

    public void initlist(String data) {
        JSONObject jsonObject=JSONObject.parseObject(data);
        JSONArray array = (JSONArray) JSONArray.parse(jsonObject.getString("data"));
        for (int mark = 0; mark < array.size(); mark++) {
            JSONObject res = array.getJSONObject(mark);
            Dish dish = new Dish(res.getInteger("id"), res.getString("name"), res.getInteger("price"),
                    res.getInteger("restaurant_id"), res.getInteger("sales_volume"), res.getInteger("scoring_times"),
                    res.getInteger("total_score"), res.getString("classification"), res.getString("cuisine"), res.getString("image"));
//            try {
//                saveMyBitmap(String.valueOf(Restaurant_id)+"_"+String.valueOf(dish.getId()),dish.getImage());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            DishList.add(dish);
        }
    }

    public void initReferlist(String data) {
            //Log.d("data",data);
        JSONArray array = (JSONArray) JSONArray.parse(data);
        for (int mark = 0; mark < array.size(); mark++) {
            JSONObject res = array.getJSONObject(mark);
            Refer_Dish k = new Refer_Dish();
            k.setCheck(false);
            k.setId(res.getIntValue("id"));
            k.setName(res.getString("name"));
            k.setPrice(res.getIntValue("price"));
            k.setImage(res.getString("image"));
            //k.setImage(getBitmapFromLocal(String.valueOf(Restaurant_id)+"_"+String.valueOf(k.getId())));
            ReferDishList.add(k);
        }
    }

    /**
     * 从本地缓存获取图片
     */
    public Bitmap getBitmapFromLocal(String fileName){
        try{
            File file=new File(Environment.getExternalStorageDirectory()+ "/Pictures/",fileName+".PNG");
            if(file.exists()){
                Bitmap bitmap=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+ "/Pictures/"+fileName+".PNG");
                //Toast.makeText(ResturantActivity.this,"从本地缓存获取图片"+fileName,Toast.LENGTH_SHORT);
                return bitmap;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
                //referAdapter.notifyDataSetChanged();
                expandableListView.setAdapter(referAdapter);
                referAdapter.notifyDataSetChanged();
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
            case R.id.btn_buy:
                if(ReferDishList.size()!=0){
                    for (int i = 0; i < ReferDishList.size(); i++) {
                        if(!NowsDishes.contains(ReferDishList.get(i))){
                            if(ReferDishList.get(i).isCheck()){
                                NowsDishes.add(ReferDishList.get(i));
                            }
                        }
                    }
                }
                Toast.makeText(this,String.valueOf(NowsDishes.size()),Toast.LENGTH_SHORT).show();
                //Intent nowDishlist=new Intent(ResturantActivity.this,);
                AddToBack();
                break;
        }
    }
    public void AddToBack(){
        SharedPreferences sp = getSharedPreferences("userConfig", PreferenceActivity.MODE_PRIVATE);
        String customer_id=sp.getString("id","");
        int price=0;
        String dish_id_list="";
        if(NowsDishes.size()>0){
            for(int v=0;v<NowsDishes.size();v++){
                price+=NowsDishes.get(v).getPrice();
                if(v<NowsDishes.size()-1){
                    dish_id_list+=(String.valueOf(NowsDishes.get(v).getId()))+",";
                }else {
                    dish_id_list+=String.valueOf(NowsDishes.get(v).getId());
                }
            }
        }
        //Log.d("字符",dish_id_list);
        JSONObject json=new JSONObject();
        json.put("price",String.valueOf(price));
        json.put("state","1");
        json.put("dish_id_list",dish_id_list);
        json.put("restaurant_id",String.valueOf(Restaurant_id));
        json.put("customer_id",customer_id);
        Log.d("JSON",json.toString());
        try {
            OkhttpManager.doPostHttpRequest(url_order, json.toString(), new OkhttpManager.DataCallBack() {
                @Override
                public void requestFailture(Request request, IOException e) {
                }
                @Override
                public void requestSuccess(String result) {
                    JSONObject msg=JSONObject.parseObject(result);
                    if(msg.getString("msg").equals("请求成功")&&msg.getString("data").equals("订单添加成功")){
                        Toast.makeText(ResturantActivity.this, msg.getString("data"), Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(ResturantActivity.this, LoginActivity.class);
//                        startActivity(intent);
                    }else {
                        Toast.makeText(ResturantActivity.this, msg.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
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
                    String result=response.body().string();
                    JSONObject object=JSONObject.parseObject(result);
                    if(object.getString("msg").equals("请求成功")){
                        Message msg1=new Message();
                        Bundle bundle1=new Bundle();
                        bundle1.putString("DATA",object.getString("data"));
                        msg1.setData(bundle1);
                        ll.sendMessage(msg1);
                        //initReferlist(object.getString("data"));
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ResturantActivity.this,"未能找到推荐菜单",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public Handler ll=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle p=msg.getData();
            initReferlist(p.getString("DATA"));
        }
    };

}
