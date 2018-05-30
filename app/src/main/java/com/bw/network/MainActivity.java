package com.bw.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.bw.network.receiver.NetworkReceiver;
import com.bw.network.widgets.NetWorkBar;

public class MainActivity extends AppCompatActivity {

    public NetWorkBar netWorkBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //networkReceiver = new NetworkReceiver();
        netWorkBar = findViewById(R.id.mNetWorkBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkReceiver, makeIntentFilter());
    }

   NetworkReceiver networkReceiver = new NetworkReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            //监听wifi的连接状态即是否连接的一个有效的无线路由
            if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())){
                Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (parcelableExtra != null){
                    // 获取联网状态的NetWorkInfo对象
                    NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                    //获取的State对象则代表着连接成功与否等状态
                    NetworkInfo.State state = networkInfo.getState();
                    //判断网络是否已经连接
                    boolean isConnected = state == NetworkInfo.State.CONNECTED;
                    Log.i("TAG", "isConnected:" + isConnected);
                    if (isConnected) {
                        netWorkBar.showNetWorkBar(false);
                    } else {
                        netWorkBar.showNetWorkBar(true);
                    }
                }
            }

            // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                //获取联网状态的NetworkInfo对象
                NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (info != null) {
                    //如果当前的网络连接成功并且网络连接可用
                    if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                        if (info.getType() == ConnectivityManager.TYPE_WIFI
                                || info.getType() == ConnectivityManager.TYPE_MOBILE) {
                            netWorkBar.showNetWorkBar(false);
                        }
                    } else {
                        netWorkBar.showNetWorkBar(true);
                    }
                }
            }
        }
    };



    /**
     * 发送服务广播
     * @return filter
     */
    private IntentFilter makeIntentFilter() {
        //在代码中实现动态注册的方式
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        return filter;
    }

}
