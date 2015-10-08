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
import android.view.KeyEvent;
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
import com.canaan.lockbible.ui.widgt.MyViewPager;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by canaan on 2015/3/30 0030.
 */
public class LockActivity extends BaseActivity {
    private static final String TAG = LockActivity.class.getSimpleName();

    @InjectView(R.id.activit_lock_view_pager)MyViewPager mViewPager;

    PagerAdapter mAdapter;
    private Bitmap mBitmap;
    private int mPageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLock();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lock);
        ButterKnife.inject(this);
        Log.i(TAG, "On Create");

        mBitmap = getBitmapFromRes(R.drawable.pic_back);
        mPageCount = getPageCount();
        initViewPager();
        mAdapter.notifyDataSetChanged();
        //startService(new Intent(this, LockScreenService.class));
    }

    private int getPageCount() {
        boolean a = SharedPreferenUtils.getBoolean(this, Constants.TAG_IS_PIN_VIEW_OPEN);
        if (SharedPreferenUtils.getString(this,Constants.TAG_PATTERN_STRING).equals("")) {
            SharedPreferenUtils.saveBoolean(this, Constants.TAG_IS_PIN_VIEW_OPEN, false);
        }

        if (SharedPreferenUtils.getBoolean(this,Constants.TAG_IS_PIN_VIEW_OPEN))
            return 2;
        return 1;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
        Log.d(TAG, "position--->" + mViewPager.getCurrentItem());
        if (mPageCount == 2) {
            mViewPager.setCurrentItem(1);
        }

    }

    private Bitmap getBitmapFromRes(int id) {
        Bitmap bmp = ((BitmapDrawable) getResources()
                .getDrawable(id)).getBitmap();
        return bmp;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (mPageCount == 2) {
                if (position == 0) {
                    return new LockPinFragment();
                } else {
                    return new LockVerseShowFragment();
                }
            } else if (mPageCount == 1){
                return new LockVerseShowFragment();
            } else {
                return new LockVerseShowFragment();
            }

        }

        @Override
        public int getCount() {
            return mPageCount;
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
