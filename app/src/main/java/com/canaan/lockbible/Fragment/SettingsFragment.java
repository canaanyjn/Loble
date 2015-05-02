package com.canaan.lockbible.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.avos.avoscloud.feedback.FeedbackAgent;
import com.canaan.lockbible.Constants.Constants;
import com.canaan.lockbible.R;
import com.canaan.lockbible.Service.LockScreenService;
import com.canaan.lockbible.Tools.SharedPreferenUtils;

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
    @InjectView(R.id.fragment_prefer_rating) TextView mRatingLayout;
    @InjectView(R.id.fragment_prefer_update) TextView mUpdateLayout;
    @InjectView(R.id.fragment_prefer_feedback) TextView mFeedbackLayout;

    private MaterialDialog materialDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_preferences,container,false);

        ButterKnife.inject(this,v);
        init();
        setSwitchListener();
        setSwitchType();
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
    }

    private void setSwitchListener(){
        mLockSwitch.setOnCheckedChangeListener(this);
        mPushSwitch.setOnCheckedChangeListener(this);
        mToolBarSwitch.setOnCheckedChangeListener(this);
    }

    private void setSwitchType(){
        mLockSwitch.setChecked(SharedPreferenUtils.getBoolean(getActivity(), Constants.TAG_IS_LOCK_SCREEN_OPEN));
        mPushSwitch.setChecked(SharedPreferenUtils.getBoolean(getActivity(),Constants.TAG_IS_PUSH_OPEN));
        mToolBarSwitch.setChecked(SharedPreferenUtils.getBoolean(getActivity(),Constants.TAG_IS_TOOLBAR_SHOW));
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
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
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
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.fragment_prefer_lock_switch:
                if (!SharedPreferenUtils.getBoolean(getActivity(), Constants.TAG_IS_LOCK_SCREEN_OPEN)){
                    Intent i = new Intent(getActivity(), LockScreenService.class);
                    getActivity().startService(i);
                    LockScreenService.setServiceAlarm(getActivity(),true);
                    SharedPreferenUtils.saveBoolean(getActivity(),Constants.TAG_IS_LOCK_SCREEN_OPEN,true);
                }else{
                    LockScreenService.stopService(getActivity());
                    SharedPreferenUtils.saveBoolean(getActivity(),Constants.TAG_IS_LOCK_SCREEN_OPEN,false);
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
        }
    }
}
