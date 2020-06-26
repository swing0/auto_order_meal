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
import com.example.autobook.Adapter.OrderAdapter;
import com.example.autobook.Bean.OrderDetails;
import com.example.autobook.MyApplication;
import com.example.autobook.R;
import com.example.autobook.Utils.OkhttpManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Order_Unpaid_Fragment extends Fragment implements View.OnClickListener{

    private PullRefreshLayout layout;
    private OrderAdapter orderAdapter;
    private ExpandableListView OrderUnpaidList;
    private String customer_id;
    private Button pay;
    static String url;
    static String url_pay;
    private List<OrderDetails> data=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
        initdata("1");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_order__unpaid_, container, false);
        //视图注入框架
        data.clear();
        MyApplication myApplication=new MyApplication();
        url="http://"+myApplication.getIP()+":8080/order/state";
        url_pay="http://"+myApplication.getIP()+":8080/order/updateManyState";
        OrderUnpaidList=(ExpandableListView)view.findViewById(R.id.OrderUnpaidList__);
        layout=(PullRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        pay=(Button)view.findViewById(R.id.to_paid);
        pay.setOnClickListener(this);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences sp = getActivity().getSharedPreferences("userConfig", PreferenceActivity.MODE_PRIVATE);
        customer_id=sp.getString("id","");
    }

    /*
      请求数据
     */
    public void initdata(String state){
        data.clear();
        //XUtil3网络请求
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("id",customer_id);
        jsonObject.put("state",state);
        try {
            OkhttpManager.doPostHttpRequest(url, jsonObject.toString(), new OkhttpManager.DataCallBack() {
                @Override
                public void requestFailture(Request request, IOException e) {

                }
                @Override
                public void requestSuccess(String result) {
                    Message message = new Message();
                    message.obj = result;
                    mHandler.sendMessage(message);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result=(String)msg.obj;
            Log.d("订单数据",result);
            JSONObject jsonObject=JSONObject.parseObject(result);
            if(jsonObject.getString("msg").equals("请求成功")){
                JSONArray array=JSONArray.parseArray(jsonObject.getString("data"));
                for(int i=0;i<array.size();i++){
                    OrderDetails orderDetails=JSONObject.parseObject(array.get(i).toString(),OrderDetails.class);
                    orderDetails.setPaid(false);
                    for(int k=0;k<orderDetails.getSimpleDishes().size();k++){
                        orderDetails.getSimpleDishes().get(k).setPaid(false);
                    }
                    data.add(orderDetails);
                }
                Log.d("表格数据",String.valueOf(data.size()));
                orderAdapter=new OrderAdapter(data,getContext());
                OrderUnpaidList.setAdapter(orderAdapter);
                Log.d("MARK","137行被执行");
                orderAdapter.setOnclick_checkbox(new OrderAdapter.onCheckChangeListener() {
                    @Override
                    public void onGroupClick(int groupID) {
                        data.get(groupID).setPaid(!data.get(groupID).isPaid());
                    }
                });
            }else{
                OrderUnpaidList.setBackgroundResource(R.drawable.image_no);
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.to_paid:
                paid();
                break;
        }
    }
    public void paid(){
        JSONArray array=new JSONArray();
        for(OrderDetails a:data){
            if(a.isPaid()){
                JSONObject l=new JSONObject();
                l.put("id",String.valueOf(a.getOrder_id()));
                array.add(l);
            }
        }
        JSONObject object=new JSONObject();
        object.put("state","2");
        object.put("data",array.toArray());
        RequestParams params=new RequestParams(url_pay);
        params.setAsJsonContent(true);
        params.setBodyContent(object.toJSONString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Message message=new Message();
                message.obj=result;
                handler.sendMessage(message);
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
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String data=(String)msg.obj;
            JSONObject json=JSONObject.parseObject(data);
            if(json.getString("data").equals("订单状态修改成功")&&json.getString("msg").equals("请求成功")){
                Toast.makeText(getContext(),"付款成功！",Toast.LENGTH_SHORT).show();
                initdata("1");
            }
        }
    };
}
