package com.example.autobook.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baoyz.widget.PullRefreshLayout;
import com.example.autobook.Adapter.PostAdapter;
import com.example.autobook.Bean.OrderDetails;
import com.example.autobook.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class Order_Post_Fragment extends Fragment implements View.OnClickListener {
    private final static String url="http://192.168.0.104:8080/order/state";
    private final static String url_pay="http://192.168.0.104:8080/order/updateManyState";
    private ExpandableListView postlistview;
    private List<OrderDetails> datas=new ArrayList<>();
    private PostAdapter postAdapter;
    private Button hadGet;
    private PullRefreshLayout layout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_order__post_, container, false);
        datas.clear();
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
        //initlistview();
    }

    public void initview(){
        layout=(PullRefreshLayout)getActivity().findViewById(R.id.refresh);
        hadGet=(Button)getActivity().findViewById(R.id.hadGet);
        postlistview=(ExpandableListView)getActivity().findViewById(R.id.postlist);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initlistview();
                layout.setRefreshing(false);
            }
        });
        hadGet.setOnClickListener(this);
        initlistview();
        postAdapter.setOnclick_checkbox(new PostAdapter.onCheckChangeListener() {
            @Override
            public void onGroupClick(int groupID) {
                datas.get(groupID).setPaid(!datas.get(groupID).isPaid());
            }
        });
    }
    public void initlistview(){
        initdata("3");
        postAdapter=new PostAdapter(datas,getContext());
        postlistview.setAdapter(postAdapter);
    }

    public void initdata(String state){
        datas.clear();
        //XUtil3网络请求
        JSONObject jsonObject=new JSONObject();
        SharedPreferences sp = getActivity().getSharedPreferences("userConfig", PreferenceActivity.MODE_PRIVATE);
        jsonObject.put("id",sp.getString("id",""));
        jsonObject.put("state",state);
        RequestParams params=new RequestParams(url);
        params.setAsJsonContent(true);
        params.setBodyContent(jsonObject.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Message message = new Message();
                message.obj = result;
                hang.sendMessage(message);
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
    public Handler hang=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result=(String)msg.obj;
            JSONObject jsonObject=JSONObject.parseObject(result);
            if(jsonObject.getString("msg").equals("请求成功")){
                JSONArray array=JSONArray.parseArray(jsonObject.getString("data"));
                for(int i=0;i<array.size();i++){
                    OrderDetails orderDetails=JSONObject.parseObject(array.get(i).toString(),OrderDetails.class);
                    orderDetails.setPaid(false);
                    for(int k=0;k<orderDetails.getSimpleDishes().size();k++){
                        orderDetails.getSimpleDishes().get(k).setPaid(false);
                    }
                    datas.add(orderDetails);
                }
                Log.d("数据",String.valueOf(datas.size()));
            }else{
                postlistview.setBackgroundResource(R.drawable.no_post_order);
                //Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hadGet:
                GetDish();
                break;
        }
    }
    public void GetDish(){
        JSONArray array=new JSONArray();
        for(OrderDetails a:datas){
            if(a.isPaid()){
                JSONObject l=new JSONObject();
                l.put("id",String.valueOf(a.getOrder_id()));
                array.add(l);
            }
        }
        JSONObject object=new JSONObject();
        object.put("state","4");
        object.put("data",array.toArray());
        Log.d("json",object.toJSONString());
        RequestParams params=new RequestParams(url_pay);
        params.setAsJsonContent(true);
        params.setBodyContent(object.toJSONString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Message message=new Message();
                message.obj=result;
                handler1.sendMessage(message);
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
    public Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String data=(String)msg.obj;
            JSONObject json=JSONObject.parseObject(data);
            if(json.getString("data").equals("订单状态修改成功")&&json.getString("msg").equals("请求成功")){
                Toast.makeText(getActivity(),"已确认收到",Toast.LENGTH_SHORT).show();
                initlistview();
            }
        }
    };
}
