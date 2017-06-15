package com.example.administrator.servicetest;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.util.Log;

/**
 * Created by Administrator on 2017/6/14.
 */

public class LogUtil {
    public static void log(String tag){
        log(tag, tag);
    }

    public static void log(String tag, String method){
        Log.e(tag, tag + ": "  + method + " " +
                " Process: " + Process.myPid() +
                " Thread: " + Thread.currentThread().getId());
    }

    public static void log(String tag, String method, Context context){
        Log.e(tag, tag + ": "  + method + " " +
                " Process: " + Process.myPid() +
                " ProcessName: " + getProcessName(Process.myPid(), context) +
                " Thread: " + Thread.currentThread().getId());
    }

    public static String getProcessName(int pid, Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningAppProcessInfo appProcess : am.getRunningAppProcesses()){
            if(pid == appProcess.pid){
                return appProcess.processName;
            }
        }
        return "";
    }
}
