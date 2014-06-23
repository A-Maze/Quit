package com.amaze.quit.app;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by jorik on 22-6-2014.
 */
public class NotificationService extends IntentService {
    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();

        // Do work here, based on the contents of dataString

    }

}
