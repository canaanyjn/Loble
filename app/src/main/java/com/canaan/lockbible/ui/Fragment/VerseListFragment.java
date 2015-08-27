package com.canaan.lockbible.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.canaan.lockbible.ui.Adapter.LoadingFooter;
import com.canaan.lockbible.ui.Adapter.VerseListAdapter;
import com.canaan.lockbible.Constants.Constants;
import com.canaan.lockbible.Model.Verse;
import com.canaan.lockbible.R;
import com.canaan.lockbible.Tools.DateUtils;
import com.canaan.lockbible.Tools.Log;
import com.canaan.lockbible.Tools.SharedPreferenUtils;
import com.melnykov.fab.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by canaan on 2015/4/6 0006.
 */
public class VerseListFragment extends BaseFragment {
    @InjectView(R.id.fragment_main_rv) RecyclerView mRecycleView;
    @InjectView(R.id.fab) FloatingActionButton mFAB;
    @InjectView(R.id.fragment_list_SR) SwipeRefreshLayout swipeRefreshLayout;
    private MaterialDialog mDialog;
    private int current_page = 0;
    private boolean isLoading = false;

    private VerseListAdapter adapter;
    private LinearLayoutManager listManager;
    private List<Verse> mVerses = new ArrayList<>();

    private static final String TAG = VerseListFragment.class.getSimpleName();
    private static final int ITEM_COUNT_PER_PAGE = 10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_verselist,container,false);
        ButterKnife.inject(this, v);

        init();
        initDialog();
        setClicks();
        Log.e(TAG, "onCreateView");

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
    }

//    private void findViews(View v){
//        mRecycleView = (RecyclerView)v.findViewById(R.id.fragment_main_rv);
//        mFAB = (FloatingActionButton)v.findViewById(R.id.fab);
//    }

    private void init(){
        listManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(listManager);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setHasFixedSize(true);
        getNewDatas();
        adapter = new VerseListAdapter(getActivity(), mVerses);
        mRecycleView.setAdapter(adapter);

        getActivity().setTitle("经文列表");

        swipeRefreshLayout.setColorSchemeResources(R.color.theme_primary_light
                , R.color.theme_accent_color
                , R.color.theme_primary_dark);
    }

    private void getNewDatas() {
        //mVerses = mDbManager.queryVerses();
        //Log.e(TAG,"mVerse size--->"+mVerses.size());
        //if (mVerses.size()==0) {
        isLoading = true;
        AVQuery<AVObject> query = new AVQuery<AVObject>("VerseList");
        query.whereEqualTo("TAG", "1")
            .orderByDescending("count")
            .setSkip(current_page * ITEM_COUNT_PER_PAGE)
            .limit(ITEM_COUNT_PER_PAGE)
            .findInBackground(new FindCallback<AVObject>() {
                public void done(List<AVObject> verses, AVException e) {
                    if (e == null) {
                        if (verses == null)
                            return;
                        List<Verse> newVerses = new ArrayList<Verse>();
                        for (int i = 0; i < verses.size(); i++) {
                            //newVerses.addAll((ArrayList) verses);
                            Verse verse = new Verse();
                            verse.setDate(verses.get(i).getString("date"));
                            verse.setVerseAddress(verses.get(i).getString("verseAddress"));
                            verse.setVerseContent(verses.get(i).getString("verseContent"));
                            Log.e(TAG, "i-->" + i + ",verseA-->" + verses.get(i).getString("verseAddress"));
                            newVerses.add(verse);
                            mDbManager.addVerse(verse);
                        }
                        //mVerses = newVerses;
                        //adapter.setVerses(mVerses);
                        //mRecycleView.setAdapter(adapter);
                        adapter.onLoadNewData(newVerses);
                        Log.e(TAG, "verses size--->" + verses.size());
                        swipeRefreshLayout.setRefreshing(false);
                        current_page ++;
                        isLoading = false;
                    } else {
                        Log.d(TAG,getResources().getString(R.string.close_lock_screen));
                        isLoading = false;
                    }
                }
            });
        //}

    }

    private void initDialog(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.add_dialog_layout,null);
        final MaterialEditText addAddressEdittext = (MaterialEditText)v.findViewById(R.id.dialog_add_verse_address_et);
        final MaterialEditText addContentEdittext = (MaterialEditText)v.findViewById(R.id.dialog_add_verse_content_et);

        mDialog = new MaterialDialog(getActivity())
                .setTitle("增加经文")
                .setContentView(v)
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (addAddressEdittext.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "请输入经节", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (addContentEdittext.getText().toString().equals("")){
                            Toast.makeText(getActivity(),"请输入经文",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Verse verse = new Verse();
                        verse.setDate(DateUtils.getDate());
                        verse.setVerseAddress(addAddressEdittext.getText().toString());
                        verse.setVerseContent(addContentEdittext.getText().toString());
                        mDbManager.addVerse(verse);
                        mVerses.add(verse);
                        adapter.notifyDataSetChanged();

                        SharedPreferenUtils.saveString(getActivity(), Constants.TAG_LAST_VERSE_ADDRESS,
                                addAddressEdittext.getText().toString());
                        SharedPreferenUtils.saveString(getActivity(), Constants.TAG_LAST_VERSE_CONTENT,
                                addContentEdittext.getText().toString());
                        mDialog.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addAddressEdittext.setText("");
                        addContentEdittext.setText("");
                        mDialog.dismiss();
                    }
                });

    }

    private void setClicks(){
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getActivity(),AddVerseActivity.class);
//                startActivityForResult(i,100);
                mDialog.show();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                loadPage();
            }
        });
        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mVerses.size()==0){
                    swipeRefreshLayout.setEnabled(false);
                }else {
                    swipeRefreshLayout.setEnabled(true);
                }

                int lastVisibleItemPosition = listManager.findLastVisibleItemPosition();
                int itemCount = listManager.getItemCount();

                if (!isLoading && adapter.getFooterState() != LoadingFooter.STATE_END
                        && lastVisibleItemPosition > itemCount -3 && dy > 0) {
                    getNewDatas();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==1){
            getNewDatas();
            adapter.notifyDataSetChanged();
        }
    }

    private void loadPage(){
        mDbManager.clearTable(mVerses);
        //mVerses.clear();
        getNewDatas();
    }

}
