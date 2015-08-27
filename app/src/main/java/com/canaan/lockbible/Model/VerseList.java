package com.canaan.lockbible.Model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by canaan on 2015/4/19 0019.
 */
@AVClassName(VerseList.VERSE_LIST)
public class VerseList extends AVObject {
    static final String VERSE_LIST = "VerseList";

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
