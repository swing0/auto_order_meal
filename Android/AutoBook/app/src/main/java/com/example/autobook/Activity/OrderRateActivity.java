package com.example.autobook.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.autobook.Adapter.ReferAdapter;
import com.example.autobook.Bean.OrderDetails;
import com.example.autobook.Bean.Refer_Dish;
import com.example.autobook.Bean.SimpleDish;
import com.example.autobook.MyApplication;
import com.example.autobook.R;
import com.example.autobook.Utils.MyDialog;
import com.example.autobook.Utils.OkhttpManager;
import com.squareup.okhttp.Request;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderRateActivity extends Activity implements View.OnClickListener {
    private TextView resturant,cancel,commit_dish;
    private ListView listView;
    private Button commit;
    private ReferAdapter referAdapter;
    private OrderDetails orderDetails;
    private RatingBar rating_resturant,rating_dish;
    private MyDialog mMyDialog;
    private String select_dish_id=null;
    private String select_resturant_id=null;
    private List<Refer_Dish> data=new ArrayList<>();
    static String url1;
    static String url2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_rate);
        MyApplication my=new MyApplication();
        url1="http://"+my.getIP()+":8080/dish/score";
        url2="http://"+my.getIP()+":8080/restaurant/score";
        resturant=(TextView)findViewById(R.id.resturant);
        commit=(Button)findViewById(R.id.commit);
        commit.setOnClickListener(this);
        rating_resturant=(RatingBar)findViewById(R.id.rating_resturant);
        listView=(ListView)findViewById(R.id.dish);
        setOrderDetails();
        resturant.setText("感谢您在"+orderDetails.getRestaurant_name()+"用餐");
        referAdapter=new ReferAdapter(getApplicationContext(),data);
        listView.setAdapter(referAdapter);
        View dialog_view = getLayoutInflater().inflate(R.layout.dialog, null);
        mMyDialog = new MyDialog(OrderRateActivity.this, 0, 0, dialog_view, R.style.Theme_AppCompat_Dialog);
        mMyDialog.setCancelable(true);
        cancel=dialog_view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        commit_dish=dialog_view.findViewById(R.id.commit_dish);
        commit_dish.setOnClickListener(this);
        rating_dish=dialog_view.findViewById(R.id.rating_dish);
        referAdapter.setCheck(new ReferAdapter.oncheck() {
            @Override
            public void Checkedchanged(int position, boolean ischecked) {
                select_dish_id=String.valueOf(data.get(position).getId());
                Log.d("菜品id",select_dish_id);
                mMyDialog.show();
            }
        });
    }
    public void setOrderDetails(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        orderDetails=(OrderDetails) bundle.getSerializable("DATA");
        select_resturant_id=String.valueOf(orderDetails.getRestaurant_id());
        Log.d("饭店id",select_resturant_id);
        for(SimpleDish p:orderDetails.getSimpleDishes()){
            Refer_Dish q=new Refer_Dish(p.getId().intValue(),p.getName(),p.getPrice().intValue(),p.getImage(),false);
            data.add(q);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                mMyDialog.dismiss();
                break;
            case R.id.commit_dish:
                float score=rating_dish.getRating()*2;
                Commit(url1,select_dish_id,String.valueOf((int)score));
                mMyDialog.dismiss();
                break;
            case R.id.commit:
                float score1=rating_resturant.getRating()*2;
                Commit(url2,select_resturant_id,String.valueOf((int)score1));
                break;
        }
    }
    public void Commit(String url, String id,String score){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("total_score",score);
        Log.d("json数据",jsonObject.toString());
        try {
            OkhttpManager.doPostHttpRequest(url, jsonObject.toString(), new OkhttpManager.DataCallBack() {
                @Override
                public void requestFailture(Request request, IOException e) {

                }
                @Override
                public void requestSuccess(String result) {
                    Message message = new Message();
                    message.obj = result;
                    handler.sendMessage(message);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result=(String) msg.obj;
            Log.d("结果",result);
            JSONObject jsonObject=JSONObject.parseObject(result);
            if(jsonObject.getString("msg").equals("请求成功")){
                Toast.makeText(OrderRateActivity.this,"感谢您的评价！",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(OrderRateActivity.this,"评价失败！",Toast.LENGTH_SHORT).show();
            }
        }
    };
}
