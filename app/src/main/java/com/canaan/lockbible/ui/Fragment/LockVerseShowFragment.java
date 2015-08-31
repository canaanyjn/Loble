package com.canaan.lockbible.ui.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by canaan on 2015/8/30 0030.
 */
public class LockVerseShowFragment extends Fragment {
    @InjectView(R.id.at_lock_address_tv)
    TextView addressTextView;
    @InjectView(R.id.at_lock_content_tv) TextView contentTextView;
//    @InjectView(R.id.at_lock_rootview)
//    RelativeLayout rootLayout;

    private Context mContext;

    private static final String TAG = LockVerseShowFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lock_verse_layout,container,false);
        ButterKnife.inject(this,v);

        mContext = getActivity();

        setTypeFace();
        setClicks();
        setVerse();

        return v;
    }

    private void setTypeFace(){
        TextUtils.setTypeFace(contentTextView, getActivity().getAssets(), "fz_zxh.TTF");
        TextUtils.setTypeFace(addressTextView, getActivity().getAssets(), "fz_zxh.TTF");
    }

    private void setClicks(){
//        rootLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        //downX = event.getX();
//                        downY = event.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        float moveY;
//                        moveY = event.getY();
//                        if (moveY - downY > 200)
//                            exit(Constants.DOWN);
//                        if (downY - moveY > 200)
//                            exit(Constants.UP);
//                        break;
//                }
//                return true;
//            }
//        });
    }

    private void exit(int direction){
        getActivity().finish();
        if (direction == Constants.DOWN)
            getActivity().overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_slide_out_bottom);
        if (direction == Constants.UP)
            getActivity().overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_slide_out_top);
    }

    private void setVerse(){
        Log.i(TAG, "VerseAddress-->" + SharedPreferenUtils.getString(mContext, Constants.TAG_LAST_VERSE_ADDRESS));
        String addressVerse = SharedPreferenUtils.getString(mContext, Constants.TAG_LAST_VERSE_ADDRESS);
        String contentVerse = SharedPreferenUtils.getString(mContext, Constants.TAG_LAST_VERSE_CONTENT);
        if (addressVerse == "")
            addressVerse = "创1:1";
        if (contentVerse == "")
            contentVerse = "起初";
        addressTextView.setText(addressVerse);
        contentTextView.setText("  " + contentVerse);
    }
}
