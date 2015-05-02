package com.canaan.lockbible.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.volley.toolbox.JsonObjectRequest;
import com.canaan.lockbible.Constants.Constants;
import com.canaan.lockbible.DB.DBManager;
import com.canaan.lockbible.Tools.network.RequestManager;

/**
 * Created by canaan on 2015/4/7 0007.
 */
public class BaseFragment extends Fragment {
    protected Object tag = new Object();
    protected DBManager mDbManager;

    public static BaseFragment newInstance(int index) {
        switch (index) {
            case Constants.FRAGMENT_VERSE_LIST:
                return new VerseListFragment();
            case Constants.FRAGMENT_PREFERENCE:
                return new SettingsFragment();
            case Constants.FRAGMENT_ABOUT:
                return new AboutFragment();
            default:
                return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbManager = new DBManager(getActivity().getApplicationContext());
    }

    public void addRequestToQueue(JsonObjectRequest jsonObjectRequest) {
        jsonObjectRequest.setTag(tag);
        RequestManager.getRequestQueue().add(jsonObjectRequest);
    }

    @Override
    public void onStop() {
        RequestManager.getRequestQueue().cancelAll(tag);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDbManager.closeDB();
    }
}


