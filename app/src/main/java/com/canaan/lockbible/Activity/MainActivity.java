package com.canaan.lockbible.Activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.avos.avoscloud.feedback.FeedbackAgent;
import com.canaan.lockbible.Fragment.BaseFragment;
import com.canaan.lockbible.Fragment.NavigationDrawerFragment;
import com.canaan.lockbible.R;
import com.canaan.lockbible.Service.LockScreenService;
import com.umeng.update.UmengUpdateAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;


public class MainActivity extends BaseActivity
        implements View.OnClickListener,NavigationDrawerFragment.OnTabCilckListener{
    @Optional @InjectView(R.id.at_main_toolbar) Toolbar mToolBar;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private BaseFragment mMainFragment[] = new BaseFragment[4];

    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setSupportActionBar(mToolBar);

        //开启反馈通知
        FeedbackAgent agent = new FeedbackAgent(this);
        agent.sync();

        //umeng自动更新
        UmengUpdateAgent.setUpdateCheckConfig(false);
        UmengUpdateAgent.update(this);

        mToolBar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mToolBar.setNavigationOnClickListener(this);
        initFragment();
        //open the lockScreen
        Intent i = new Intent(this,LockScreenService.class);
        startService(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        getSupportActionBar().setTitle(title);
    }

    private void initFragment(){
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer_fragment,
                (DrawerLayout)findViewById(R.id.main_drawer_layout));
        mNavigationDrawerFragment.setOnTabClickListener(this);
        showFragment(0);
    }

    @Override
    public void onClick(View v) {
        mNavigationDrawerFragment.openDrawerFragment();
    }

    private void showFragment(int index){

        if (mMainFragment[index] == null)
            mMainFragment[index] = BaseFragment.newInstance(index);

        mNavigationDrawerFragment.setTabSelected(index);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_layout,mMainFragment[index])
                .commit();

    }

    @Override
    public void onClick(int index) {
        showFragment(index);
    }

}
