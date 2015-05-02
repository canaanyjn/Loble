package com.canaan.lockbible.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canaan.lockbible.Model.Verse;
import com.canaan.lockbible.R;

import java.util.List;

/**
 * Created by canaan on 2015/4/6 0006.
 */
public class VerseListAdapter extends RecyclerView.Adapter<VerseListAdapter.Holder> {

    private Context mContext;
    private List<Verse> mVerses;

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

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_cardview, viewGroup, false);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int i) {
        Verse verse = mVerses.get(mVerses.size()-i-1);
        viewHolder.timeTextView.setText(verse.getDate());
        viewHolder.titleTextView.setText(verse.getVerseAddress());
        if (verse.getVerseContent().length()<10){
            viewHolder.contentTextView.setText(verse.getVerseContent()+"          ");
        }else{
            viewHolder.contentTextView.setText(verse.getVerseContent());
        }
    }

    @Override
    public int getItemCount() {
        return mVerses == null?0:mVerses.size();
    }

}
