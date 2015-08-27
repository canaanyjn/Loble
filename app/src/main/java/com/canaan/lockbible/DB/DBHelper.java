package com.canaan.lockbible.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.litepal.tablemanager.Connector;

/**
 * Created by canaan on 2015/4/18 0018.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "verse.db";
    private static final int VERSION = 1;
    public static final String COLLUM_VERSE_ADDRESS = "VERSE_ADDRESS";
    public static final String COLLUM_VERSE_CONTENT = "VERSE_CONTENT";

    public static final String TABLE_NAME = "VERSE_LIST";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE "+TABLE_NAME+" ("+
                                "_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLLUM_VERSE_ADDRESS+" TEXT,"+
                COLLUM_VERSE_CONTENT+" TEXT,"+
                                " _DATE TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
