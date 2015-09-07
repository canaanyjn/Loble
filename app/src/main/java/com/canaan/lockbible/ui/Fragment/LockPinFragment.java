package com.canaan.lockbible.ui.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.canaan.lockbible.Constants.Constants;
import com.canaan.lockbible.R;
import com.canaan.lockbible.Tools.Log;
import com.canaan.lockbible.Tools.SharedPreferenUtils;
import com.canaan.lockbible.ui.Activity.LockActivity;
import com.eftimoff.patternview.PatternView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by canaan on 2015/8/30 0030.
 */
public class LockPinFragment extends Fragment {
    public interface ViewPagerScroll {
        void setAbility(boolean ability);
    }


    @InjectView(R.id.pin_layout_patternView) PatternView mPatternView;

    private View rootView;
    public static final String TAG = LockPinFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lock_pin_layout, container, false);
        ButterKnife.inject(this, rootView);

        initPattenView();
        return rootView;
    }

    private void initPattenView() {
        mPatternView.setOnPatternDetectedListener(new PatternView.OnPatternDetectedListener() {
            @Override
            public void onPatternDetected() {
                String patternString = mPatternView.getPatternString();
                if (patternString.equals(SharedPreferenUtils.getString(getActivity(),
                        Constants.TAG_PATTERN_STRING))) {
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(),R.string.pin_is_wrong,Toast.LENGTH_SHORT).show();
                }
                mPatternView.clearPattern();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
