package com.example.autobook.Activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.ExpandableListView;

import com.alibaba.fastjson.JSONArray;
import com.example.autobook.Adapter.OrderAdapter;
import com.example.autobook.Adapter.PostAdapter;
import com.example.autobook.Bean.OrderDetails;
import com.example.autobook.MyApplication;
import com.example.autobook.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class RatingActivity extends Activity {
    static String url;

    private ExpandableListView expandableListView;
    private OrderAdapter postAdapter;
    private List<OrderDetails> detailsList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        MyApplication myApplication=new MyApplication();
        url="http://"+myApplication.getIP()+":8080/order/state";
        expandableListView=(ExpandableListView)findViewById(R.id.pingjia_listview);
        initdata("4");
    }

    public void initdata(String state){
        SharedPreferences sp=getSharedPreferences("userConfig", PreferenceActivity.MODE_PRIVATE);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",sp.getString("id",""));
            jsonObject.put("state",state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams params=new RequestParams(url);
        params.setAsJsonContent(true);
        params.setBodyContent(jsonObject.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Message message = new Message();
                message.obj = result;
                pj_handle.sendMessage(message);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    public Handler pj_handle=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result=(String)msg.obj;
            com.alibaba.fastjson.JSONObject jsonObject= com.alibaba.fastjson.JSONObject.parseObject(result);
            if(jsonObject.getString("msg").equals("请求成功")){
                JSONArray array=JSONArray.parseArray(jsonObject.getString("data"));
                for(int i=0;i<array.size();i++){
                    OrderDetails orderDetails= com.alibaba.fastjson.JSONObject.parseObject(array.get(i).toString(),OrderDetails.class);
                    orderDetails.setPaid(false);
                    for(int k=0;k<orderDetails.getSimpleDishes().size();k++){
                        orderDetails.getSimpleDishes().get(k).setPaid(false);
                    }
                    detailsList.add(orderDetails);
                }
                Log.d("数据",String.valueOf(detailsList.size()));
                postAdapter=new OrderAdapter(detailsList,getApplicationContext());
                expandableListView.setAdapter(postAdapter);
                postAdapter.setOnclick_checkbox(new OrderAdapter.onCheckChangeListener() {
                    @Override
                    public void onGroupClick(int groupID) {
                        OrderDetails orderDetails=detailsList.get(groupID);
                        Intent intent=new Intent(RatingActivity.this,OrderRateActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("DATA",orderDetails);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }else{
                expandableListView.setBackgroundResource(R.drawable.image_no1);
                //Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
            }
        }
    };
}
