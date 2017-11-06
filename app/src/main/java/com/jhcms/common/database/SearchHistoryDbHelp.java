package com.jhcms.common.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wyj on 2017/4/10.
 */
public class SearchHistoryDbHelp extends SQLiteOpenHelper {

    static final String CREATE_DB = "create table SearchHistory(" +
            "h_id integer primary key autoincrement," +
            "h_text text)";

    public SearchHistoryDbHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
