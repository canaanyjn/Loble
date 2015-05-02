package com.canaan.lockbible.BroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.canaan.lockbible.Service.LockScreenService;

/**
 * Created by canaan on 2015/3/30 0030.
 */
public class ScreenChangeBR extends BroadcastReceiver {
    private static final String TAG = ScreenChangeBR.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.SCREEN_ON")
                || intent.getAction().equals("android.intent.action.SCREEN_OFF"))
        {
            Log.i(TAG, "Screen On/Off");
        }

    }
}
