package com.canaan.lockbible;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.canaan.lockbible.Constants.Constants;
import com.canaan.lockbible.Model.VerseList;
import com.canaan.lockbible.Tools.network.RequestManager;

import org.litepal.LitePalApplication;

/**
 * Created by canaan on 2015/4/11 0011.
 */
public class MyApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initVolley();

        //initLeanCloud
        AVOSCloud.initialize(getApplicationContext(), Constants.LEAN_CLOUD_APPID,Constants.LEAN_CLOUD_APPKEY);
        AVObject.registerSubclass(VerseList.class);
    }

    private void initVolley(){
        RequestManager.init(getApplicationContext());
    }

}
