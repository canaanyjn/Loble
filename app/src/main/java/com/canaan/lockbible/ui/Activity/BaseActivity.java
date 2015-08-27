package com.canaan.lockbible.ui.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.canaan.lockbible.DB.DBManager;

/**
 * Created by canaan on 2015/3/30 0030.
 */
public class BaseActivity extends ActionBarActivity {
    protected DBManager mDbmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbmanager = new DBManager(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDbmanager.closeDB();
    }
}
