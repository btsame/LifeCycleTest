package com.example.administrator.servicetest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.administrator.servicetest.LogUtil;

/**
 * Created by Administrator on 2017/6/14.
 */

public class DynamicReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.log("DynamicReceiver", "onReceive", context);
        Log.e("DynamicReceiver", "action: " + intent.getAction());

        int count = 0;
        while (true){
            if(count > 60) break;
            count++;
            Log.e("DynamicReceiver", "count: " + count);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
