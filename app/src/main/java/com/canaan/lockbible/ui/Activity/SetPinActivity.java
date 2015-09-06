package com.canaan.lockbible.ui.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;
import android.widget.Toast;

import com.canaan.lockbible.Constants.Constants;
import com.canaan.lockbible.R;
import com.canaan.lockbible.Tools.Log;
import com.canaan.lockbible.Tools.SharedPreferenUtils;
import com.eftimoff.patternview.PatternView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by canaan on 2015/9/6 0006.
 */
public class SetPinActivity extends SecondActivity {
    @InjectView(R.id.pin_layout_text)
    TextView mPinLayoutText;
    @InjectView(R.id.pin_layout_patternView)
    PatternView mPinLayoutPatternView;
    private int currentStep;
    private String patternString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin_layout);
        ButterKnife.inject(this);

        currentStep = 1;
        setClicks();
    }

    private void setClicks(){
        mPinLayoutPatternView.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {
            @Override
            public void onPatternDetected() {
                if (currentStep == 1) {
                    patternString = mPinLayoutPatternView.getPatternString();
                    mPinLayoutPatternView.clearPattern();
                    currentStep ++;
                    mPinLayoutText.setText(getResources().getText(R.string.confirm_pin));
                } else {
                    if (patternString.equals(mPinLayoutPatternView.getPatternString())) {
                        SetPinActivity.this.finish();
                        savePatternString();
                    } else {
                        Toast.makeText(SetPinActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                        currentStep = 1;
                        patternString = "";
                        mPinLayoutPatternView.clearPattern();
                        mPinLayoutText.setText(getResources().getText(R.string.set_pin));
                    }
                }
            }
        });
    }

    private void savePatternString() {
        SharedPreferenUtils.saveString(SetPinActivity.this, Constants.TAG_PATTERN_STRING,
                patternString);
    }


}
