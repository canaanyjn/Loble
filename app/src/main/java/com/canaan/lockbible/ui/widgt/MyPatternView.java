package com.canaan.lockbible.ui.widgt;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.eftimoff.patternview.PatternView;

/**
 * Created by canaan on 2015/9/7 0007.
 */
public class MyPatternView extends PatternView {
    public MyPatternView(Context context) {
        super(context);
    }

    public MyPatternView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPatternView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }
}
