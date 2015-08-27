package com.canaan.lockbible.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.canaan.lockbible.Model.Verse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by canaan on 2015/4/18 0018.
 */
public class DBManager {
    private SQLiteDatabase db;
    private DBHelper helper;
    private ContentValues cv;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
        cv = new ContentValues();
    }

    public void addVerse(Verse verse){
        cv.clear();
        cv.put("VERSE_ADDRESS",verse.getVerseAddress());
        cv.put("VERSE_CONTENT",verse.getVerseContent());
        cv.put("_DATE",verse.getDate());
        db.insert(DBHelper.TABLE_NAME,null,cv);
    }

    /**
     * queryVerses all verses, return list
     * @return List<Verse>
     */
    public List<Verse> queryVerses() {
        ArrayList<Verse> verses = new ArrayList<Verse>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Verse verse = new Verse();
            verse.setId(c.getInt(c.getColumnIndex("_ID")));
            verse.setVerseAddress(c.getString(c.getColumnIndex("VERSE_ADDRESS")));
            verse.setVerseContent(c.getString(c.getColumnIndex("VERSE_CONTENT")));
            verse.setDate(c.getString(c.getColumnIndex("_DATE")));
            verses.add(verse);
        }
        c.close();
        return verses;
    }

    public boolean queryVerseByDate(String date){
        boolean isExit = false;
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            if(c.getString(c.getColumnIndex("_DATE")).equals(date)){
                isExit = true;
            }
        }
        c.close();
       return isExit;
    }

    public String queryVerseAddressByDate(String date){
        String verseAddress = "";
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            if(c.getString(c.getColumnIndex("_DATE")).equals(date)){
                verseAddress = c.getString(c.getColumnIndex("VERSE_ADDRESS"));
            }
        }
        c.close();
        return verseAddress;
    }

    public String queryVerseContentByDate(String date){
        String verseContent = "";
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            if(c.getString(c.getColumnIndex("_DATE")).equals(date)){
                verseContent = c.getString(c.getColumnIndex("VERSE_CONTENT"));
            }
        }
        c.close();
        return verseContent;
    }

    public String queryTodayVerseAddress(){
        String verseAddress = "";
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            verseAddress = c.getString(c.getColumnIndex(DBHelper.COLLUM_VERSE_ADDRESS));
        }
        c.close();
        return verseAddress;
    }

    public String queryTodayVerseContent(){
        String verseContent = "";
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            verseContent = c.getString(c.getColumnIndex(DBHelper.COLLUM_VERSE_CONTENT));
        }
        c.close();
        return verseContent;
    }

    /**
     * queryVerses all verses, return cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM "+DBHelper.TABLE_NAME, null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }

    /**
     * add all verses
     * @param verses
     */
    public void addList(List<Verse> verses){
        for (Verse verse:verses){
            cv.clear();
            cv.put("VERSE_ADDRESS",verse.getVerseAddress());
            cv.put("VERSE_CONTENT",verse.getVerseContent());
            cv.put("_DATE",verse.getDate());
            db.insert(DBHelper.TABLE_NAME,null,cv);
        }
    }

    public void clearTable(List<Verse> verses){
//        String[] addressList = new String[verses.size()];
//        for (int i = 0;i<verses.size();i++){
//            addressList[i] = verses.get(i).getVerseAddress()+"";
//        }
//        Log.e("DBManager","size-->"+addressList.length);
//        Log.e("DBManager","1"+addressList[0]);
//        db.delete("VERSE_LIST","VERSE_ADDRESS = ?",addressList);
        db.delete(DBHelper.TABLE_NAME,null,null);
    }
}
