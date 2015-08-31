package com.canaan.lockbible.ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canaan.lockbible.R;

/**
 * Created by canaan on 2015/8/30 0030.
 */
public class LockPinFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lock_pin_layout,container,false);

        return v;
    }
}
