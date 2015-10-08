package com.canaan.lockbible.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.canaan.lockbible.Constants.Constants;
import com.canaan.lockbible.R;
import com.canaan.lockbible.Tools.Log;
import com.canaan.lockbible.Tools.SharedPreferenUtils;
import com.canaan.lockbible.Tools.TextUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by canaan on 2015/3/30 0030.
 */
public class LockActivity extends BaseActivity {
    @InjectView(R.id.at_lock_address_tv) TextView addressTextView;
    @InjectView(R.id.at_lock_content_tv) TextView contentTextView;
    @InjectView(R.id.at_lock_rootview) RelativeLayout rootLayout;

    private static final String TAG = LockActivity.class.getSimpleName();
    public static Handler handler;
    private float downY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLock();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lock);
        ButterKnife.inject(this);

        Log.i(TAG, "On Create");

        //startService(new Intent(this, LockScreenService.class));
        setTypeFace();
        setClicks();
        setVerse();
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

    private void setTypeFace(){
        TextUtils.setTypeFace(contentTextView, getAssets(), "fz_zxh.TTF");
        TextUtils.setTypeFace(addressTextView,getAssets(),"fz_zxh.TTF");
    }

    private void setClicks(){
        rootLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //downX = event.getX();
                        downY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveY;
                        moveY = event.getY();
                        if (moveY - downY >200)
                            exit(Constants.DOWN);
                        if (downY - moveY > 200)
                            exit(Constants.UP);
                        break;
                }
                return true;
            }
        });
    }

    private void exit(int direction){
        finish();
        if (direction == Constants.DOWN)
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_slide_out_bottom);
        if (direction == Constants.UP)
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_slide_out_top);
    }

    private void setVerse(){
        Log.i(TAG,"VerseAddress-->"+SharedPreferenUtils.getString(this, Constants.TAG_LAST_VERSE_ADDRESS));
        String addressVerse = SharedPreferenUtils.getString(this, Constants.TAG_LAST_VERSE_ADDRESS);
        String contentVerse = SharedPreferenUtils.getString(this, Constants.TAG_LAST_VERSE_CONTENT);
        if (addressVerse == "")
            addressVerse = "创1:1";
        if (contentVerse == "")
            contentVerse = "起初";
        addressTextView.setText(addressVerse);
        contentTextView.setText("  "+contentVerse);
    }
}
