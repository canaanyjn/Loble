package com.canaan.lockbible.ui.Activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import com.canaan.lockbible.Constants.Constants;
import com.canaan.lockbible.R;
import com.canaan.lockbible.Tools.Log;
import com.canaan.lockbible.Tools.SharedPreferenUtils;
import com.canaan.lockbible.ui.Fragment.LockPinFragment;
import com.canaan.lockbible.ui.Fragment.LockVerseShowFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by canaan on 2015/3/30 0030.
 */
public class LockActivity extends BaseActivity implements LockPinFragment.ViewPagerScroll{
    private static final String TAG = LockActivity.class.getSimpleName();

    @InjectView(R.id.activit_lock_view_pager)ViewPager mViewPager;

    PagerAdapter mAdapter;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLock();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lock);
        ButterKnife.inject(this);
        Log.i(TAG, "On Create");

        initViewPager();
        mBitmap = getBitmapFromRes(R.drawable.pic_back);
        //startService(new Intent(this, LockScreenService.class));
    }

    private void setLock(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        if (SharedPreferenUtils.getBoolean(this,Constants.TAG_IS_TOOLBAR_SHOW)){
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void initViewPager() {
        mAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);

    }

    public void setViewPagerEnable(boolean isAble) {
        mViewPager.setEnabled(isAble);
    }

    private Bitmap getBitmapFromRes(int id) {
        Bitmap bmp = ((BitmapDrawable) getResources()
                .getDrawable(id)).getBitmap();
        return bmp;
    }

    @Override
    public void setAbility(boolean ability) {
//        if (mViewPager == null) {
//            View v = View.inflate()
//            mViewPager = (ViewPager) findViewById(R.id.activit_lock_view_pager);
//        }
//        mViewPager.setEnabled(ability);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new LockPinFragment();
            } else {
                return new LockVerseShowFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public class BlurPageTransformer implements ViewPager.PageTransformer {
        private Bitmap mBitmap;

        public BlurPageTransformer(Bitmap bitmap) {
            mBitmap = bitmap;
        }

        @Override
        public void transformPage(View page, float position) {
            blur(page,position);
        }

        private void blur(View view,float position) {
            RenderScript rs = RenderScript.create(LockActivity.this);
            Allocation overlayAlloc = Allocation.createFromBitmap(rs, mBitmap);
            ScriptIntrinsicBlur blur =
                    ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
            blur.setInput(overlayAlloc);

            Log.d(TAG, "position--->" + position);
            if (position == 0)
                position = 0.1f;
            blur.setRadius(Math.abs(position*10%25));
            blur.forEach(overlayAlloc);
            overlayAlloc.copyTo(mBitmap);
            view.setBackground(new BitmapDrawable(getResources(), mBitmap));
            rs.destroy();
        }
    }



}
