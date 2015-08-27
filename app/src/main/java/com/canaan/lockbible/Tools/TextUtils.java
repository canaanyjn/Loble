package com.canaan.lockbible.Tools;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by canaan on 2015/4/3 0003.
 */
public class TextUtils {

    public static final void setTypeFace(TextView tv,AssetManager assertName,String typeFaceName){
        Typeface tf = Typeface.createFromAsset(assertName,""+typeFaceName);
        tv.setTypeface(tf);
    }
}
