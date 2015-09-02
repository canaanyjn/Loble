package com.canaan.lockbible.ui.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.balysv.materialripple.MaterialRippleLayout;
import com.canaan.lockbible.R;
import com.github.glomadrian.codeinputlib.CodeInput;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by canaan on 2015/8/30 0030.
 */
public class LockPinFragment extends Fragment {


    @InjectView(R.id.pin_button_1)
    Button mPinButton1;
    @InjectView(R.id.pin_button_2)
    Button mPinButton2;
    @InjectView(R.id.pin_button_3)
    Button mPinButton3;
    @InjectView(R.id.pin_button_4)
    Button mPinButton4;
    @InjectView(R.id.pin_button_5)
    Button mPinButton5;
    @InjectView(R.id.pin_button_6)
    Button mPinButton6;
    @InjectView(R.id.pin_button_7)
    Button mPinButton7;
    @InjectView(R.id.pin_button_8)
    Button mPinButton8;
    @InjectView(R.id.pin_button_9)
    Button mPinButton9;

    private List<Button> pinButtons = new ArrayList<>();
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lock_pin_layout, container, false);
        ButterKnife.inject(this, rootView);

        initButtonList();
        setButtonRipple();

        return rootView;
    }

    private void initButtonList() {
        pinButtons.add(mPinButton1);
        pinButtons.add(mPinButton2);
        pinButtons.add(mPinButton3);
        pinButtons.add(mPinButton4);
        pinButtons.add(mPinButton5);
        pinButtons.add(mPinButton6);
        pinButtons.add(mPinButton7);
        pinButtons.add(mPinButton8);
        pinButtons.add(mPinButton9);
    }

    private void setButtonRipple() {
        for (int i = 0 ;i < pinButtons.size();i++) {
            Button button = pinButtons.get(i);
            MaterialRippleLayout.on(button)
                    .rippleColor(Color.GRAY)
                    .rippleAlpha(0.3f)
                    .rippleDuration(350)
                    .rippleHover(true)
                    .create();
            button.setClickable(true);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
