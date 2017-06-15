package com.example.administrator.servicetest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.servicetest.LogUtil;

/**
 * Created by Administrator on 2017/6/14.
 */

public class StaticLocalReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.log("StaticLocalReceiver", "onReceive", context);
    }
}
