package com.canaan.lockbible.Service;


import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.canaan.lockbible.ui.Activity.LockActivity;
import com.canaan.lockbible.Constants.Constants;
import com.canaan.lockbible.DB.DBManager;
import com.canaan.lockbible.Model.Verse;
import com.canaan.lockbible.R;
import com.canaan.lockbible.Tools.DateUtils;
import com.canaan.lockbible.Tools.Log;
import com.canaan.lockbible.Tools.SharedPreferenUtils;


import java.util.Calendar;
import java.util.List;

/**
 * Created by canaan on 2015/3/30 0030.
 */
public class LockScreenService extends Service {

    private static KeyguardManager km;
    private static KeyguardManager.KeyguardLock kl;
    private Intent toLockIntent;
    private static Handler handler = new Handler();

    public static Calendar calendar = Calendar.getInstance();
    private static final String TAG = LockScreenService.class.getSimpleName();



//    protected void onHandleIntent(Intent intent) {
//        findToVerses("2015-4-18");
//    }


    public void onCreate(){
        super.onCreate();

        toLockIntent = new Intent(LockScreenService.this,LockActivity.class);
        //如果已经有这个activity，则将已有的提到栈顶，否则新建一个activity
        toLockIntent.addFlags(toLockIntent.FLAG_ACTIVITY_NEW_TASK);

        km = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("");
        kl.disableKeyguard();

        this.setServiceAlarm(this,true);
        registerComponent();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    private BroadcastReceiver br = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i(TAG, "BRonReceive");
            if (intent.getAction().equals("android.intent.action.SCREEN_ON")
                    || intent.getAction().equals("android.intent.action.SCREEN_OFF"))
            {
                if (SharedPreferenUtils.getBoolean(LockScreenService.this,Constants.TAG_IS_LOCK_SCREEN_OPEN))
                    LockScreenService.this.startActivity(toLockIntent);
            }
        }

    };

    private boolean isUpdated() {
        String updateDate = SharedPreferenUtils.getString(LockScreenService.this, Constants.TAG_UPDATED_DATE);
        if (updateDate.equals(DateUtils.getDate()))
            return true;
        return false;
    }

    private boolean isPushVerseOpen() {
        return SharedPreferenUtils.getBoolean(LockScreenService.this,Constants.TAG_IS_PUSH_OPEN);
    }

    public class GetVerseThread implements Runnable{

        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    AVQuery<AVObject> query = new AVQuery<AVObject>("Verse");
                    query.whereEqualTo("date", DateUtils.getDate());
                    query.findInBackground(new FindCallback<AVObject>() {
                        public void done(List<AVObject> verses, AVException e) {
                            String verseAddress = "";
                            String verseContent = "";
                            DBManager dbManager = new DBManager(getApplicationContext());
                            if (e == null) {
                                verseAddress = verses.get(0).getString("verseAddress");
                                verseContent = verses.get(0).getString("verseContent");

                                SharedPreferenUtils.saveString(LockScreenService.this,
                                        Constants.TAG_LAST_VERSE_ADDRESS, verseAddress);
                                SharedPreferenUtils.saveString(LockScreenService.this,
                                        Constants.TAG_LAST_VERSE_CONTENT, verseContent);
                                SharedPreferenUtils.saveString(LockScreenService.this,
                                        Constants.TAG_UPDATED_DATE,DateUtils.getDate());
//                                if (!dbManager.queryVerseByDate(DateUtils.getDate())) {
//                                    Verse verse = new Verse();
//                                    verse.setDate(DateUtils.getDate());
//                                    verse.setVerseAddress(verseAddress);
//                                    verse.setVerseContent(verseContent);
//                                    dbManager.addVerse(verse);
//                                }
                            } else {
                                if (verses == null) {
                                    Toast.makeText(LockScreenService.this, "无今日经文", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LockScreenService.this, "请检查网络连接或重试",
                                            Toast.LENGTH_SHORT).show();
                                }
                                Log.e(TAG, "exception-->" + e.toString());

                                SharedPreferenUtils.saveString(LockScreenService.this,
                                        Constants.TAG_LAST_VERSE_ADDRESS,
                                        dbManager.queryTodayVerseAddress());
                                SharedPreferenUtils.saveString(LockScreenService.this,
                                        Constants.TAG_LAST_VERSE_CONTENT,
                                        dbManager.queryTodayVerseContent());
                            }
                        }
                    });
                }
            });
        }
    }


    public void onStart(Intent intent,int startId){
        Log.i(TAG, "On Start");
        super.onStart(intent, startId);
        if (!isUpdated() && isPushVerseOpen()) {
            new Thread(new GetVerseThread()).start();
        }
        notifySpinnerBar();
    }

    public void onDestroy(){
        this.unregisterReceiver(br);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void notifySpinnerBar() {
        Notification notify = new Notification(R.drawable.ic_launcher,null,0);
        notify.flags = Notification.FLAG_ONGOING_EVENT;

        Intent notifyIntent = new Intent(this,LockActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, 0);
        notify.setLatestEventInfo(this, null, null, pendingIntent);

        this.startForeground(0, notify);
    }

    //注册broadcast
    public void registerComponent()
    {
        IntentFilter mScreenOnOrOffFilter = new IntentFilter();
        mScreenOnOrOffFilter.addAction("android.intent.action.SCREEN_OFF");
        LockScreenService.this.registerReceiver(br, mScreenOnOrOffFilter);
    }

    //停止服务
    public static void stopService(Context context){
        Intent iService = new Intent(context,LockScreenService.class);
        iService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.stopService(iService);
        kl = km.newKeyguardLock("");
        kl.reenableKeyguard();
        LockScreenService.setServiceAlarm(context, false);
    }

    public static void setServiceAlarm(Context context,boolean isOn){
        Intent i = new Intent(context,LockScreenService.class);
        PendingIntent pi = PendingIntent.getService(context,0,i,0);
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        setAlarmCalendar(0,45,0);
        if (isOn){
            am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                    Constants.INTERVAL,pi);
            Log.e(TAG,"on");
        }else {
            am.cancel(pi);
            pi.cancel();
        }
    }

    public static void setAlarmCalendar(int HOUR_OF_DAY,int MINUTE,int SECOND){
        calendar.set(Calendar.HOUR_OF_DAY,HOUR_OF_DAY);
        calendar.set(Calendar.MINUTE, MINUTE);
        calendar.set(Calendar.MILLISECOND,SECOND);
    }

}
