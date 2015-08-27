package com.canaan.lockbible.Model;

import com.avos.avoscloud.AVObject;

/**
 * Created by canaan on 2015/4/6 0006.
 */
public class Verse extends AVObject {
    private String verseAddress;
    private String verseContent;
    private String date;
    private int id;
    private String TAG;

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public String getVerseAddress() {
        return verseAddress;
    }

    public void setVerseAddress(String verseAddress) {
        this.verseAddress = verseAddress;
    }

    public String getVerseContent() {
        return verseContent;
    }

    public void setVerseContent(String verseContent) {
        this.verseContent = verseContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
