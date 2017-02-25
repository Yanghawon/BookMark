package com.yangha.Bookmark.Application;

import android.app.Application;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yangha.Bookmark.util.DBHelperManager;

/**
 * Created by pjoo on 2017. 2. 25..
 */

public class BookmarkResource extends Application {
    private String TAG = getClass().getName();
    private DBHelperManager mDbHelperManager;
    private static BookmarkResource resource;

    @Override
    public void onCreate() {
        super.onCreate();
        mDbHelperManager = new DBHelperManager(this, "BookmarkDB", null, 1, mDbHandler);
        resource = this;
    }

    DatabaseErrorHandler mDbHandler = new DatabaseErrorHandler() {

        @Override
        public void onCorruption(SQLiteDatabase sqLiteDatabase) {
            Log.e(TAG, "DB error");
        }
    };

    /** 
     * DataBase 매니져를 가져와서 작업할 수 있는 Instance를 반환한다 
     * @return 
     * DataBaseHelper 관련 매니져. 
     * */
    public static BookmarkResource getInstance() {
        return resource;
    }

    /** 
     * Application으로 모든 리소스 관리하는 class 
     * @return 
     * Resource를 관리하는 Instance 
     * */
    public DBHelperManager getDBHelperManager() {
        return mDbHelperManager;
    }
}
