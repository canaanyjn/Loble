package com.canaan.lockbible.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canaan.lockbible.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by canaan on 2015/4/5 0005.
 */
public class NavigationDrawerFragment extends BaseFragment
        implements View.OnClickListener{
    @InjectView(R.id.drawer_bookings_main) TextView mainTextView;
    @InjectView(R.id.drawer_bookings_preference) TextView preferenceTextView;
    @InjectView(R.id.drawer_bookings_about) TextView aboutTextView;
    //private TextView mainTextView,preferenceTextView,aboutTextView;

    public interface OnTabCilckListener{
        public void onClick(int index);
    }

    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;
    private OnTabCilckListener mOnTabClickListener;

    private List<TextView> tabs = new ArrayList<>();
    private int mCurrentIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout drawerContent = (LinearLayout)inflater.inflate(R.layout.fragment_navigation_drawer,container,false);
        ButterKnife.inject(this, drawerContent);
        //findViews(drawerContent);
        init();
        return drawerContent;
    }

    private void findViews(View v){
        mainTextView = (TextView)v.findViewById(R.id.drawer_bookings_main);
        preferenceTextView = (TextView)v.findViewById(R.id.drawer_bookings_preference);
        aboutTextView = (TextView)v.findViewById(R.id.drawer_bookings_about);
    }
    public void setUp(int Id,DrawerLayout drawerLayout){
        mFragmentContainerView = getActivity().findViewById(Id);
        mDrawerLayout = drawerLayout;

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    public void setOnTabClickListener(OnTabCilckListener onTabClickListener){
        this.mOnTabClickListener = onTabClickListener;
    }

    public void setmCurrentIndex(int index){
        this.mCurrentIndex = index;
    }

    public  int getCurrentIndex(){return mCurrentIndex;}

    public  void openDrawerFragment(){
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    private void init(){
        tabs.add(mainTextView);
        tabs.add(preferenceTextView);
        tabs.add(aboutTextView);
        tabs.get(0).setSelected(true);

        for (int i = 0;i<tabs.size();i++){
            tabs.get(i).setOnClickListener(this);
        }
    }

    public synchronized void setTabSelected(int index){
        if (index == mCurrentIndex||!isIndexValid(index))
            return;
        tabs.get(mCurrentIndex).setSelected(false);
        tabs.get(index).setSelected(true);
        mCurrentIndex = index;
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public boolean isIndexValid(int index){
        if (index<3&&index>=0)
            return true;
         return false;
    }

    @Override
    public void onClick(View v) {
        if (mOnTabClickListener == null) return;
        switch (v.getId()){
            case R.id.drawer_bookings_main:
                mOnTabClickListener.onClick(0);
                break;
            case R.id.drawer_bookings_preference:
                mOnTabClickListener.onClick(1);
                break;
            case R.id.drawer_bookings_about:
                mOnTabClickListener.onClick(2);
                break;
        }
    }
}
