package com.example.administrator.servicetest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.servicetest.ICalculateInterface;
import com.example.administrator.servicetest.LogUtil;

/**
 * Created by Administrator on 2017/6/13.
 */

public class RemoteService extends Service {
    public static final String TAG = "RemoteService";

    public int count;

    public class RemoteBinder extends ICalculateInterface.Stub {

        @Override
        public int count() throws RemoteException {
            Log.e(TAG, "RemoteBinder" + ": count " +
                    " Process: " + Process.myPid() +
                    " Thread: " + Thread.currentThread().getId());
            int localCount = 0;
            while (true){
                if(localCount > 60) break;
                count++;
                localCount++;
                Log.e(TAG, "RemoteBinder" + ": count " +
                        " Process: " + Process.myPid() +
                        " Thread: " + Thread.currentThread().getId() +
                        " count: " + count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return count;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.log(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.log(TAG, "onStartCommand");
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
        Log.e(TAG, TAG + ": onBind" +
                " Process: " + Process.myPid() +
                " Thread: " + Thread.currentThread().getId());
        return new RemoteBinder();
    }
}
