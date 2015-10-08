package com.canaan.lockbible.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canaan.lockbible.R;

/**
 * Created by canaan on 2015/4/7 0007.
 */
public class AboutFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about_layout,null,false);
        init();

        return v;
    }

    private void init(){
        getActivity().setTitle("关于我们");
    }
}
