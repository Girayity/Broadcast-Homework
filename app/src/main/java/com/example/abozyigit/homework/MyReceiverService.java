package com.example.abozyigit.homework;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class MyReceiverService extends Service {
    private static BroadcastReceiver myReceiver;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate()
    {
        registerMyReciver();
    }

    @Override
    public void onDestroy()
    {
        unregisterReceiver(myReceiver);
        myReceiver = null;
    }

    private void registerMyReciver()
    {
        myReceiver = new BroadcastReceiver()
        {
            //uygulama  yok olsa bile log atıyor, logcat'de görebilirsiniz
            @Override
            public void onReceive(Context context, Intent intent)
            {
                MyDatabase myDatabase= new MyDatabase(context);
                //sistem aksiyonları, ödevinizle alakalı olan
                if (intent.getAction()==Intent.ACTION_SCREEN_OFF)
                {
                    Log.d("test", "ACTION_SCREEN_OFF");
                    myDatabase.insert(true);
                }
                //sistem aksiyonları, ödevinizle alaklı olan
                else if (intent.getAction()==Intent.ACTION_SCREEN_ON)
                {
                    Log.d("test", "ACTION_SCREEN_ON");
                    myDatabase.insert(false);
                }
                //bu aksiyon bizim broadcastsender uygulamasından gelcek
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(myReceiver, filter);
    }
}
