package com.canaan.lockbible.Tools;

import java.util.Calendar;

/**
 * Created by canaan on 2015/4/11 0011.
 */
public class DateUtils {

    public static final String getDate(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int date = c.get(Calendar.DATE);
        return year+"-"+month+"-"+date;
    }

}
