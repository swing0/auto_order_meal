package com.example.autobook;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import org.xutils.x;

public class MyApplication extends Application {
    private String IP="192.168.0.104";

    public String getIP() {
        return IP;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);
    }
}
