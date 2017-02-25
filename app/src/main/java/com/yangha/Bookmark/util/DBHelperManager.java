package com.yangha.Bookmark.util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pjoo on 2017. 2. 25..
 */

public class DBHelperManager extends SQLiteOpenHelper{
    public DBHelperManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create Table BookMark {b_index integer primary key autoincrement, b_longitude double, b_latitude double, " +
                "b_title text, b_image text, b_category integer, b_content text, b_rating float, b_remark text}");
        sqLiteDatabase.execSQL("Create Table Category {c_index integer primary key autoincrement, c_title text, c_remark text}");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
