package com.example.autobook.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.autobook.Activity.ResturantActivity;
import com.example.autobook.Adapter.ResAdapter;
import com.example.autobook.Bean.Restaurant;
import com.example.autobook.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainFragment extends Fragment implements View.OnClickListener {

    private ListView listView;
    private FloatingActionButton select;
    final static String url="http://192.168.0.104:8080/restaurant/info";
    public List<Restaurant> restaurantList=new ArrayList<>();
    //String data=null;
    private OkHttpClient client=new OkHttpClient();
    //private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_main, container, false);
        x.view().inject(this,view);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();//初始化控件
        initOrder();//数据源
        //Log.d("data",data);
        //initlist();//初始化数据list
        listView.setAdapter(new ResAdapter(getActivity(),restaurantList));
    }

    //初始化控件
    public void initView(){
        listView=getActivity().findViewById(R.id.resturant);
        select=getActivity().findViewById(R.id.floatButton);
        select.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context=getActivity().getApplicationContext();
                Intent intent=new Intent(context, ResturantActivity.class);
                Restaurant clickSelect=restaurantList.get(i);
                Log.d("name",clickSelect.getName());
                int id=clickSelect.getId();
                Bundle bundle=new Bundle();
                bundle.putInt("Rest_id",id);
                bundle.putSerializable("Restaurant",clickSelect);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    //数据源
    public void initOrder(){
        new Thread(){
            @Override
            public void run(){
                Request request = new Request.Builder().url(url).build();
                //发送请求
                try {
                    final Response response = client.newCall(request).execute();
                    //打印服务端传回的数据
                    //Log.d("data",response.body().string());
                    Message msg=new Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("DATA",response.body().string());
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
     public Handler handler=new Handler(){
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
             Bundle m=msg.getData();
             Log.d("DATA",m.getString("DATA"));
             //data=m.getString("DATA");
             initlist(m.getString("DATA"));
         }
     };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.floatButton:
                Toast.makeText(getContext(),"YES",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void initlist(String data){
        JSONObject jsonObject=JSONObject.parseObject(data);
        JSONArray array=(JSONArray) JSONArray.parse(jsonObject.getString("data"));
        for(int mark=0;mark<array.size();mark++){
            JSONObject res =array.getJSONObject(mark);
            Restaurant restaurant=new Restaurant(Integer.parseInt(res.getString("id")),res.getString("name"),res.getString("classification"),
                    res.getString("phone"),Float.parseFloat(res.getString("total_score")),Float.parseFloat(res.getString("scoring_times")),res.getString("address"));
            restaurantList.add(restaurant);
        }
        //按照平均评分排序
        Collections.sort(restaurantList, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant restaurant, Restaurant t1) {
                return t1.getAva().compareTo(restaurant.getAva());
            }
        });

    }

}