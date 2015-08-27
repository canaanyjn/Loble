package com.canaan.lockbible.Tools;

public class Log {
	public static boolean isDebug = true;
	
	public static void i(String tag,String string){
		if(isDebug){
			android.util.Log.i(tag, string);
		}
	}
	
	public static void d(String tag,String string){
		if(isDebug){
			android.util.Log.d(tag, string);
		}
	}
	
	public static void e(String tag,String string){
		if(isDebug){
			android.util.Log.e(tag, string);
		}
	}
}
