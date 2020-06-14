package com.example.autobook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Request;

import java.io.IOException;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText username;
    private EditText et_password;
    private Button m_login;
    private TextView go_register;
    private TextView forget_password;
    final static String url="http://192.168.0.104:8080/user/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        Init();
    }

    public void Init(){
        username=(EditText)findViewById(R.id.username);
        Intent m=getIntent();
        String data=m.getStringExtra("account");
        username.setText(data);
        et_password=(EditText)findViewById(R.id.password);
        m_login=(Button)findViewById(R.id.login);
        go_register=(TextView)findViewById(R.id.go_register);
        forget_password=(TextView)findViewById(R.id.forget_password);
        m_login.setOnClickListener(this);
        go_register.setOnClickListener(this);
        forget_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                checkLoginBefore();
                break;
            case R.id.go_register:
                Intent register=new Intent(this,RegisterActivity.class);
                startActivity(register);
                //Log.d("注册","点击去注册");
                break;
            case R.id.forget_password:
                Log.d("密码","点击忘记密码");
                break;
        }
    }
    private void checkLoginBefore(){
        String name=username.getText().toString();
        String password=et_password.getText().toString();
        if(name.length()<=0&&password.length()<=0){
            Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
        }else if(name.length()<=0){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
        }else if(password.length()<=0){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
        }else if(name!=null&&password!=null){
            SharedPreferences session=getSharedPreferences("userConfig",MODE_PRIVATE);
            String saveName=session.getString("username","");
            String savePassword=session.getString("password","");

            if(saveName.equals(name)&&savePassword.equals(password)){
                Log.d("本地有缓存","本地有缓存");
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Log.d("本地无缓存","本地无缓存，服务器验证");
                //请求服务器验证
                 JSONObject json=new JSONObject();
                 json.put("account",name);
                 json.put("password",password);
                try {
                    OkhttpManager.doPostHttpRequest(url, json.toString(), new OkhttpManager.DataCallBack() {
                         @Override
                         public void requestFailture(Request request, IOException e) {

                         }
                         @Override
                         public void requestSuccess(String result) {
                             JSONObject msg=JSONObject.parseObject(result);
                             if(!msg.containsKey("msg")){
                                 if(SaveUserToSession(msg)){
                                     LetsGo();
                                 }else {
                                     //LetsGo();
                                     Toast.makeText(LoginActivity.this, "缓存失败", Toast.LENGTH_SHORT).show();
                                 }
                             }else {
                                 Toast.makeText(LoginActivity.this, msg.getString("msg"), Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 向本地存储用户数据
     * @return
     */
    public boolean SaveUserToSession(JSONObject json){
        SharedPreferences.Editor editor=getSharedPreferences("userConfig", PreferenceActivity.MODE_WORLD_READABLE).edit();
        //像SharedPreference中写入数据需要使用Editor
        //类似键值对
        editor.putString("username", json.getString("account"));
        editor.putString("address",json.getString("address"));
        editor.putString("id",json.getString("id"));
        editor.putString("nickname",json.getString("nickname"));
        editor.putString("password",json.getString("password"));
        editor.putString("phone",json.getString("phone"));
        editor.commit();
        //提交
        return editor.commit();
    }
    public void LetsGo(){
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }


}