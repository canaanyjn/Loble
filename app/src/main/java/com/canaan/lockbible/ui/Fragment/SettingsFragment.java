package com.canaan.lockbible.ui.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.feedback.FeedbackAgent;
import com.balysv.materialripple.MaterialRippleLayout;
import com.canaan.lockbible.Constants.Constants;
import com.canaan.lockbible.R;
import com.canaan.lockbible.Service.LockScreenService;
import com.canaan.lockbible.Tools.SharedPreferenUtils;
import com.canaan.lockbible.ui.Activity.SetPinActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by canaan on 2015/4/6 0006.
 */
public class SettingsFragment extends BaseFragment
                    implements CompoundButton.OnCheckedChangeListener{
    @InjectView(R.id.fragment_prefer_lock_switch) SwitchCompat mLockSwitch;
    @InjectView(R.id.fragment_prefer_push_switch) SwitchCompat mPushSwitch;
    @InjectView(R.id.fragment_prefer_toolbar_show_switch) SwitchCompat mToolBarSwitch;
    @InjectView(R.id.fragment_prefer_pin_show_switch) SwitchCompat mPinShowSwitch;
    @InjectView(R.id.fragment_prefer_rating) TextView mRatingLayout;
    @InjectView(R.id.fragment_prefer_update) TextView mUpdateLayout;
    @InjectView(R.id.fragment_prefer_feedback) TextView mFeedbackLayout;
    @InjectView(R.id.fragment_prefer_pin_setting_layout)
    RelativeLayout mPinSettingLayout;
    @InjectView(R.id.fragment_prefer_pin_show_layout)
    RelativeLayout mPinShowLayout;

    private MaterialDialog materialDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_preferences,container,false);

        ButterKnife.inject(this, v);
        init();
        setSwitchType();
        setSwitchListener();
        setClicks();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void init(){
        getActivity().setTitle("设置");

        materialDialog = new MaterialDialog(getActivity())
                .setTitle("检测更新")
                .setMessage("已是最新版本")
                .setPositiveButton("OK",new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDialog.dismiss();
                    }
                });

        addRippleView();
    }

    private void setSwitchListener(){
        mLockSwitch.setOnCheckedChangeListener(this);
        mPushSwitch.setOnCheckedChangeListener(this);
        mToolBarSwitch.setOnCheckedChangeListener(this);
        mPinShowSwitch.setOnCheckedChangeListener(this);
    }

    private void setSwitchType(){
        mLockSwitch.setChecked(SharedPreferenUtils.getBoolean(getActivity(), Constants.TAG_IS_LOCK_SCREEN_OPEN));
        mPushSwitch.setChecked(SharedPreferenUtils.getBoolean(getActivity(), Constants.TAG_IS_PUSH_OPEN));
        mToolBarSwitch.setChecked(SharedPreferenUtils.getBoolean(getActivity(), Constants.TAG_IS_TOOLBAR_SHOW));
        mPinShowSwitch.setChecked(SharedPreferenUtils.getBoolean(getActivity(), Constants.TAG_IS_PIN_VIEW_OPEN));
        if (SharedPreferenUtils.getString(getActivity(),Constants.TAG_PATTERN_STRING).equals("")) {
            mPinShowSwitch.setChecked(false);
        }
    }

    private void setClicks(){
        mFeedbackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackAgent agent = new FeedbackAgent(getActivity());
                agent.startDefaultThreadActivity();
            }
        });
        mRatingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        mUpdateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.show();
            }
        });
        mPinSettingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SetPinActivity.class));
            }
        });
    }

    private void setPinViewVisibility(boolean isShow) {
        if (isShow) {
            startPinViewAnimation(isShow);
        } else {
            startPinViewAnimation(isShow);
        }
    }

    private void setRippleView(View view) {
        MaterialRippleLayout.on(view)
                .rippleColor(Color.GRAY)
                .rippleAlpha(0.2f)
                .create();
    }

    private void addRippleView() {
        setRippleView(mFeedbackLayout);
        setRippleView(mRatingLayout);
        setRippleView(mUpdateLayout);
    }

    private void startPinViewAnimation(boolean isShow) {
        AlphaAnimation showAnimation = new AlphaAnimation(0.0f,1.0f);
        showAnimation.setDuration(300);
        AlphaAnimation closeAnimation = new AlphaAnimation(1.0f,0.0f);
        closeAnimation.setDuration(300);

        showAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mPinSettingLayout.setVisibility(View.VISIBLE);
                mPinShowLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        closeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mPinSettingLayout.setVisibility(View.GONE);
                mPinShowLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (isShow) {
            mPinSettingLayout.startAnimation(showAnimation);
            mPinShowLayout.startAnimation(showAnimation);
        } else {
            mPinSettingLayout.startAnimation(closeAnimation);
            mPinShowLayout.startAnimation(closeAnimation);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.fragment_prefer_lock_switch:
                if (!SharedPreferenUtils.getBoolean(getActivity(), Constants.TAG_IS_LOCK_SCREEN_OPEN)){
                    Intent i = new Intent(getActivity(), LockScreenService.class);
                    getActivity().startService(i);
                    LockScreenService.setServiceAlarm(getActivity(),true);

                    SharedPreferenUtils.saveBoolean(getActivity(), Constants.TAG_IS_LOCK_SCREEN_OPEN,true);
                    setPinViewVisibility(true);

                }else{
                    LockScreenService.stopService(getActivity());

                    SharedPreferenUtils.saveBoolean(getActivity(),Constants.TAG_IS_LOCK_SCREEN_OPEN,false);
                    setPinViewVisibility(false);
                }
                break;
            case R.id.fragment_prefer_push_switch:
                if(!SharedPreferenUtils.getBoolean(getActivity(),Constants.TAG_IS_PUSH_OPEN)){
                    SharedPreferenUtils.saveBoolean(getActivity(),Constants.TAG_IS_PUSH_OPEN,true);
                }else{
                    SharedPreferenUtils.saveBoolean(getActivity(),Constants.TAG_IS_PUSH_OPEN,false);
                }
                break;
            case R.id.fragment_prefer_toolbar_show_switch:
                if(!SharedPreferenUtils.getBoolean(getActivity(),Constants.TAG_IS_TOOLBAR_SHOW)){
                    SharedPreferenUtils.saveBoolean(getActivity(),Constants.TAG_IS_TOOLBAR_SHOW,true);
                }else{
                    SharedPreferenUtils.saveBoolean(getActivity(),Constants.TAG_IS_TOOLBAR_SHOW,false);
                }
                break;
            case R.id.fragment_prefer_pin_show_switch:
                 boolean a = SharedPreferenUtils.getBoolean(getActivity(), Constants.TAG_IS_PIN_VIEW_OPEN);
                if(!SharedPreferenUtils.getBoolean(getActivity(),Constants.TAG_IS_PIN_VIEW_OPEN)){
                    if (SharedPreferenUtils.getString(getActivity(),Constants.TAG_PATTERN_STRING).equals("")){
                        Toast.makeText(getActivity(),R.string.set_pin_first,Toast.LENGTH_SHORT).show();
                        mPinShowSwitch.toggle();
                        SharedPreferenUtils.saveBoolean(getActivity(),Constants.TAG_IS_PIN_VIEW_OPEN,false);
                    } else {
                        SharedPreferenUtils.saveBoolean(getActivity(),Constants.TAG_IS_PIN_VIEW_OPEN,true);
                    }
                }else{
                    SharedPreferenUtils.saveBoolean(getActivity(),Constants.TAG_IS_PIN_VIEW_OPEN,false);
                }
                break;
        }
    }
}
