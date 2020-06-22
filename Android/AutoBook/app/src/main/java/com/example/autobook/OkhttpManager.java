package com.example.autobook;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OkhttpManager {
    private OkHttpClient client;
    private static OkhttpManager okhttpManager;
    private static Handler mhandler;
    public static synchronized  OkhttpManager getInstance(){
        if(okhttpManager==null){
            okhttpManager=new OkhttpManager();
        }
        return okhttpManager;
    }
    public OkhttpManager(){
        client=new OkHttpClient();
        mhandler=new Handler(Looper.getMainLooper());

    }
    //对外可调用方法

    /**
     * 同步请求返回Response
     * @param url
     * @return
     */
    public static Response getSync(String url) throws IOException {
        return getInstance().p_getSync(url);
    }
    private Response p_getSync(String url) throws IOException {
        Request request=new Request.Builder().url(url).build();
        Response response=client.newCall(request).execute();
        return response;
    }

    /**
     * 同步请求返回string
     * @param url
     * @return
     */
    public static String getSyncAsString(String url) throws IOException {
        return getInstance().p_getSyncAsString(url);
    }
    private String p_getSyncAsString(String url) throws IOException {
        return p_getSync(url).body().string();
    }

    /**
     * 异步请求
     * @param url
     * @param callBack
     */
    public static void getAsync(String url,DataCallBack callBack){
        getInstance().p_getAsync(url, callBack);
    }
    //数据回调借口
    public interface DataCallBack{
        void requestFailture(Request request, IOException e);
        void requestSuccess(String result);
    }
    private void p_getAsync(String url, final DataCallBack callBack){
        final Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                deliverDataFailure(request,e,callBack);
            }

            @Override
            public void onResponse(Response response)  {
                try {
                    String result=response.body().string();
                    deliverDataSuccess(result,callBack);
                } catch (IOException e) {
                    deliverDataFailure(request,e,callBack);
                }
            }
        });
    }
    //数据分发
    private static void deliverDataFailure(final Request request, final IOException e, final DataCallBack dataCallBack){
        mhandler.post(new Runnable(){
            @Override
            public void run(){
                if(dataCallBack!=null){
                    dataCallBack.requestFailture(request, e);
                }
            }
        });
    }
    private void deliverDataSuccess(final String result, final DataCallBack dataCallBack){
        mhandler.post(new Runnable(){
            @Override
            public void run(){
                if(dataCallBack!=null){
                    dataCallBack.requestSuccess(result);
                }
            }
        });
    }

    /**
     * 参数Map<String,String> params=new HashMap<String,String>();
     * params.put("username","admin");
     * 外部调用
     * post提交
     */
    public static void postAsync(String url, Map<String,String> params,DataCallBack callBack){
        getInstance().p_postAsync(url, params, callBack);
    }
    private void p_postAsync(String url, Map<String,String> params, final DataCallBack callBack){
        RequestBody requestBody=null;
        if(params==null){
            params=new HashMap<String,String>();
        }
        FormEncodingBuilder builder=new FormEncodingBuilder();
        for(Map.Entry<String,String> entry:params.entrySet()){
            String key=entry.getKey().toString();
            String value=null;
            if(entry.getValue()==null){
                value="";
            }else {
                value=entry.getValue().toString();
            }
            builder.add(key,value);
        }
        requestBody=builder.build();
        final Request request=new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                deliverDataFailure(request,e,callBack);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try{
                    String result=response.body().string();
                    deliverDataSuccess(result,callBack);
                }catch (IOException e){
                    deliverDataFailure(request,e,callBack);
                }
            }
        });
    }

    /**
     * 根据url和携带json数据请求获取数据
     */
    public static void doPostHttpRequest(String url, String json,DataCallBack callBack)
            throws IOException {
        getInstance().p_doPostHttpRequest(url,json,callBack);
    }
    private void p_doPostHttpRequest(String url, String json, final DataCallBack callBack){
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        final Request request = new Request.Builder().url(url).post(body)
                .addHeader("content-type", "application/json").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                deliverDataFailure(request,e,callBack);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                try{
                     String result=response.body().string();
                    deliverDataSuccess(result,callBack);
                }catch (IOException e){
                    deliverDataFailure(request,e,callBack);
                }
            }
        });
    }

}
