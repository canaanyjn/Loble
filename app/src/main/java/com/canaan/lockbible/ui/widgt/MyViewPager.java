package com.canaan.lockbible.ui.widgt;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.eftimoff.patternview.PatternView;

/**
 * Created by canaan on 2015/9/7 0007.
 */
public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof PatternView) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
