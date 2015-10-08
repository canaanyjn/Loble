package com.canaan.lockbible.ui.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canaan.lockbible.Constants.Constants;
import com.canaan.lockbible.Model.Verse;
import com.canaan.lockbible.R;

import java.util.List;

/**
 * Created by canaan on 2015/4/6 0006.
 */
public class VerseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Verse> mVerses;
    private LoadingFooter mLoadingFooter;
    private View loadingLayout;

    private static final String TAG = VerseListAdapter.class.getSimpleName();
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;

    public VerseListAdapter(Context context,List<Verse> verses){
        mContext = context;
        mVerses = verses;
    }

    public void setVerses(List<Verse> verses){
        this.mVerses = verses;
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView titleTextView,contentTextView,timeTextView;

        public Holder(View itemView) {
            super(itemView);
            titleTextView = (TextView)itemView.findViewById(R.id.verse_list_card_view_title);
            contentTextView = (TextView)itemView.findViewById(R.id.verse_list_card_view_content);
            timeTextView = (TextView)itemView.findViewById(R.id.verse_list_card_view_time);
        }
    }

    private boolean isFooterItem(int position){
        return position == getItemCount() -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //Log.d(TAG, "viewType-->" + viewType + " isLoadingitem" + isFooterItem(viewType) + " itemCount--->" + getItemCount());
        if (viewType == TYPE_NORMAL) {
            RecyclerView.ViewHolder viewHolder;
            View v = LayoutInflater.from(mContext).
                    inflate(R.layout.item_cardview, viewGroup, false);
            viewHolder = new Holder(v);
            return viewHolder;
        } else {
            loadingLayout = LayoutInflater.from(mContext).
                    inflate(R.layout.item_loading_footer,viewGroup,false);
            mLoadingFooter = new LoadingFooter(loadingLayout);
            return mLoadingFooter;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooterItem(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (isFooterItem(position)){
            mLoadingFooter = (LoadingFooter) viewHolder;
            if (mLoadingFooter == null) {
            }
            return;
        }
        Verse verse = mVerses.get(position);
        Holder holder = (Holder)viewHolder;
        holder.timeTextView.setText(verse.getDate());
        holder.titleTextView.setText(verse.getVerseAddress());
        if (verse.getVerseContent().length()<10){
            holder.contentTextView.setText(verse.getVerseContent()+"          ");
        }else{
            holder.contentTextView.setText(verse.getVerseContent());
        }
    }


    @Override
    public int getItemCount() {
       // return mVerses == null?0:mVerses.size()+1;
        return mVerses.size()+1;
    }

    public void onLoadNewData(List<Verse> newVerses) {
        if (newVerses == null||newVerses.size() == 0) {
            checkTheFooterState();
        } else if (newVerses.size() < Constants.DEFAULT_PAGE_NUM) {
            mVerses.addAll(newVerses);
            this.notifyItemRangeInserted(mVerses.size() - 1, newVerses.size());
            checkTheFooterState();
        } else {
            mVerses.addAll(newVerses);
            this.notifyItemRangeInserted(mVerses.size()-1,newVerses.size());
            mLoadingFooter.setLoadingFooterState(LoadingFooter.STATE_LOADING);
        }
    }

    private void checkTheFooterState() {
        if (mVerses == null) {
            mLoadingFooter.setLoadingFooterState(LoadingFooter.STATE_EMPTY);
        } else {
            mLoadingFooter.setLoadingFooterState(LoadingFooter.STATE_END);
        }
    }

    public int getFooterState() {
        if (mLoadingFooter == null) {
            return mLoadingFooter.STATE_LOADING;
        }
        return mLoadingFooter.getLoadingFooterState();
    }

}
