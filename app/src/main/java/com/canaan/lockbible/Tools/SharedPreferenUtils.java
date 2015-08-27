package com.canaan.lockbible.Tools;

import android.app.Activity;
import android.content.Context;

/**
 * Created by canaan on 2015/4/9 0009.
 */
public class SharedPreferenUtils {

    public static final void saveBoolean(Context context,String tag,boolean value){
        context.getSharedPreferences("preferen", Activity.MODE_PRIVATE).
                edit().putBoolean(tag,value).commit();
    }

    public static final void saveString(Context context,String tag,String value){
        context.getSharedPreferences("preferen", Activity.MODE_PRIVATE).
                edit().putString(tag,value).commit();
    }

    public static final void saveInt(Context context,String tag,int value){
        context.getSharedPreferences("preferen", Activity.MODE_PRIVATE).
                edit().putInt(tag,value).commit();
    }

    public static final boolean getBoolean(Context context,String tag){
        return context.getSharedPreferences("preferen", Activity.MODE_PRIVATE).
                getBoolean(tag,true);
    }

    public static final String getString(Context context,String tag){
        return context.getSharedPreferences("preferen", Activity.MODE_PRIVATE).
                getString(tag,"");
    }

    public static final int getInt(Context context,String tag){
        return context.getSharedPreferences("preferen", Activity.MODE_PRIVATE).
                getInt(tag,0);
    }

}
