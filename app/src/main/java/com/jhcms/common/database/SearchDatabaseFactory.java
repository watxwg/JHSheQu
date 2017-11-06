package com.jhcms.common.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Wyj on 2017/4/10.
 */
public class SearchDatabaseFactory {

    static SearchHistoryDbHelp dbHelp;

    static SearchHistoryDbHelp getDbHelp(Context context){
        if(dbHelp == null) {
            dbHelp = new SearchHistoryDbHelp(context, "search.db", null, 1);
        }
        return dbHelp;
    }

    public static SQLiteDatabase getDBInstance(Context context){
        return getDbHelp(context).getWritableDatabase();
    }
}
