package com.example.administrator.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.administrator.servicetest.receiver.DynamicReceiver;
import com.example.administrator.servicetest.service.LocalService;
import com.example.administrator.servicetest.service.RemoteService;

public class SubActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        findViewById(R.id.start_service).setOnClickListener(this);
        findViewById(R.id.stop_service).setOnClickListener(this);
        findViewById(R.id.bind_service).setOnClickListener(this);
        findViewById(R.id.unbind_service).setOnClickListener(this);
        findViewById(R.id.bind_service_no_auto_create).setOnClickListener(this);


        findViewById(R.id.bind_local_service_binder).setOnClickListener(this);
        findViewById(R.id.bind_remote_service_binder).setOnClickListener(this);


        findViewById(R.id.send_static_local_bc).setOnClickListener(this);
        findViewById(R.id.send_static_remote_bc).setOnClickListener(this);
        findViewById(R.id.send_dynamic_no_handler_bc).setOnClickListener(this);
        findViewById(R.id.send_dynamic_hanlder_bc).setOnClickListener(this);
        handlerThread.start();

        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_service:
                startService();
                break;
            case R.id.stop_service:
                stopService();
                break;
            case R.id.bind_service:
                bindService();
                break;
            case R.id.unbind_service:
                unbindService();
                break;
            case R.id.bind_service_no_auto_create:
                bindNoCreateService();
                break;
            case R.id.bind_local_service_binder:
                bindService();
                break;
            case R.id.bind_remote_service_binder:
                bindRemoteService();
                break;
            case R.id.send_static_local_bc:
                sendStaticLocalBC();
                break;
            case R.id.send_static_remote_bc:
                sendStaticRemoteBC();
                break;
            case R.id.send_dynamic_no_handler_bc:
                sendDynamicNoHandlerBC();
                break;
            case R.id.send_dynamic_hanlder_bc:
                sendDynamicHandlerBC();
                break;
        }
    }

    private void startService(){
        Intent intent = new Intent("com.fy.yb.localservice");
        startService(intent);
    }

    private void stopService(){
        Intent intent = new Intent("com.fy.yb.localservice");
        stopService(intent);
    }

    private void bindService(){
        Intent intent = new Intent("com.fy.yb.localservice");
        bindService(intent, sc, BIND_AUTO_CREATE);
    }

    private void bindRemoteService(){
        Intent intent = new Intent("com.fy.yb.remoteservice");
        bindService(intent, sc, BIND_AUTO_CREATE);
    }

    /**
     * 绑定后不解绑会异常 android.app.ServiceConnectionLeaked
     */
    private void unbindService(){
        unbindService(sc);
    }

    private void bindNoCreateService(){
        Intent intent = new Intent("com.fy.yb.localservice");
        bindService(intent, sc, 0);
    }

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.log("ServiceConnection", "onServiceConnected");
            //绑定本进程中的Service，返回的Binder对象就是Service中创建的同一个对象
            if(name.getClassName().contains(LocalService.TAG)){
                Log.i("ServiceConnection", "LocalBinder adress: " + service.hashCode() +
                        " LocalService adress: " + ((LocalService.LocalBinder)service).getService().hashCode());
                LocalService localService = (LocalService) ((LocalService.LocalBinder)service).getService();
                localService.isBind = true;
            }

            if(name.getClassName().contains(RemoteService.TAG)){
                Log.i("ServiceConnection", "RemoteBinder adress: " + service.hashCode());
                try {
                    ICalculateInterface.Stub.asInterface(service).count();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.log("ServiceConnection", "onServiceDisconnected");
        }
    };

    private DynamicReceiver noHandlerReceiver = new DynamicReceiver();
    private DynamicReceiver handlerReceiver = new DynamicReceiver();
    private DynamicReceiver netStateReceiver = new DynamicReceiver();
    private HandlerThread handlerThread = new HandlerThread("handlerThread_1");

    private void registerReceiver(){
        IntentFilter intentFilter = new IntentFilter("com.fy.yb.dynamicNoHandlerReceiver");
        registerReceiver(noHandlerReceiver, intentFilter);

        intentFilter = new IntentFilter("com.fy.yb.dynamicHandlerReceiver");
        registerReceiver(handlerReceiver, intentFilter, null, new Handler(handlerThread.getLooper()));

        intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");//网络状态
        registerReceiver(netStateReceiver, intentFilter, "android.permission.ACCESS_NETWORK_STATE", null);
    }

    private void unregisterReceiver(){
        unregisterReceiver(noHandlerReceiver);
        unregisterReceiver(handlerReceiver);
        unregisterReceiver(netStateReceiver);
    }

    private void sendStaticLocalBC(){
        Intent intent = new Intent("com.fy.yb.staticLocalReceiver");
        sendBroadcast(intent);
    }

    private void sendStaticRemoteBC(){
        Intent intent = new Intent("com.fy.yb.staticRemoteReceiver");
        sendBroadcast(intent);
    }

    private void sendDynamicNoHandlerBC(){
        Intent intent = new Intent("com.fy.yb.dynamicNoHandlerReceiver");
        sendBroadcast(intent);
    }

    private void sendDynamicHandlerBC(){
        Intent intent = new Intent("com.fy.yb.dynamicHandlerReceiver");
        sendBroadcast(intent);
    }

}
