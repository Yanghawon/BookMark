package com.yangha.Bookmark.Application;

import android.app.Application;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yangha.Bookmark.util.DBHelperManager;

/**
 * Created by pjoo on 2017. 2. 25..
 */

public class BookmarkResource extends Application{
    private String TAG = getClass().getName();
    private DBHelperManager mDbHelperManager;
    @Override
    public void onCreate() {
        super.onCreate();
        mDbHelperManager = new DBHelperManager(this, "BookmarkDB",null,1,mDbHandler);
    }
    DatabaseErrorHandler mDbHandler = new DatabaseErrorHandler(){

        @Override
        public void onCorruption(SQLiteDatabase sqLiteDatabase) {
            Log.e(TAG, "DB error");
        }
    };

    public DBHelperManager getDBHelperManager(){
        return mDbHelperManager;
    }

}
