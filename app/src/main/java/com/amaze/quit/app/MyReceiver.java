package com.amaze.quit.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Sander on 23-6-2014.
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service1 = new Intent(context, MyAlarmService.class);
        context.startService(service1);
    }
}
