package com.jhcms.common.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jhcms.common.database.SearchDatabaseFactory;
import com.jhcms.waimaiV3.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wyj on 2017/5/5
 * TODO:
 */
public class DatabaseUtil {
    static SQLiteDatabase db;


    static {
        db = SearchDatabaseFactory.getDBInstance(MyApplication.context);
    }

    public static void insert(String s) {
        List<String> data = search();
        for (String ss : data) {
            if (ss.equals(s)) {
                return;
            }
        }
        db.execSQL("insert into SearchHistory(h_text) values ('" + s + "')");
    }

    public static void deleteAll() {
        db.execSQL("delete from SearchHistory where 1=1");
    }

    public static List<String> search() {
        Cursor c = db.rawQuery("select * from SearchHistory", null);
        List<String> data = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                String s = c.getString(c.getColumnIndex("h_text"));
                data.add(s);
            } while (c.moveToNext());
        }
        return data;
    }
}
