package com.example.autobook;

import android.app.Application;

import org.xutils.x;

public class MyApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);
    }
}
