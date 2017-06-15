package com.example.administrator.servicetest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.servicetest.LogUtil;

/**
 * Created by Administrator on 2017/6/13.
 */

public class LocalService extends Service implements ILocalInterface{
    public static final String TAG = LocalService.class.getSimpleName();

    public boolean isBind = false;
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.log(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.log(TAG, "onStartCommand");
        Log.e(TAG, TAG + ": isBind " + isBind);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.log(TAG, "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.log(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        LogUtil.log(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.log(TAG, "onBind");

        LocalBinder localBinder = new LocalBinder();
        Log.e(TAG, "LocalBinder adress: " + localBinder.hashCode() +
                " LocalService adress: " + this.hashCode());

        return localBinder;
    }

    @Override
    public int count() {
        LogUtil.log(TAG, "count");
        return 0;
    }

    public class LocalBinder extends Binder{
        public Object getService(){
            return LocalService.this;
        }
    }
}
