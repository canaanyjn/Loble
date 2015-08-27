package com.canaan.lockbible.ui.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.canaan.lockbible.R;

/**
 * Created by canaan on 2015/8/26 0026.
 */
public class LoadingFooter extends RecyclerView.ViewHolder {
    private TextView mLoadingTextView;
    private ProgressBar mProgressBar;
    private int state;

    public static final int STATE_LOADING = 0;
    public static final int STATE_END = 1;
    public static final int STATE_EMPTY = 2;

    public LoadingFooter(View itemView) {
        super(itemView);
        mLoadingTextView = (TextView)itemView.findViewById(R.id.loading_text);
        mProgressBar = (ProgressBar)itemView.findViewById(R.id.loading_progressBar);
    }

    public void setLoadingFooterState(int state){
        switch (state) {
            case STATE_LOADING:
                setLoadingState();
                break;
            case STATE_EMPTY:
                setEmptyState();
                break;
            case STATE_END:
                setEndState();
                break;
        }
    }

    private void setLoadingState() {
        state = STATE_LOADING;
        mLoadingTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void setEmptyState() {
        state = STATE_EMPTY;
        mLoadingTextView.setVisibility(View.VISIBLE);
        mLoadingTextView.setText(R.string.list_is_empty);
        mProgressBar.setVisibility(View.GONE);
    }

    private void setEndState() {
        state = STATE_END;
        mLoadingTextView.setVisibility(View.VISIBLE);
        mLoadingTextView.setText(R.string.list_is_end);
        mProgressBar.setVisibility(View.GONE);
    }

    public int getLoadingFooterState() {
        return state;
    }
}
