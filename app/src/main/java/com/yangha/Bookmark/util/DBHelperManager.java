package com.yangha.Bookmark.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by pjoo on 2017. 2. 25..
 */

public class DBHelperManager extends SQLiteOpenHelper {
    public DBHelperManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);

    }

    SQLiteDatabase database;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        database = sqLiteDatabase;
        sqLiteDatabase.execSQL("Create Table BookMark {b_index integer primary key autoincrement, b_longitude double, b_latitude double, " +
                "b_title text, b_image text, b_category integer, b_content text, b_rating float, b_remark text}");
        sqLiteDatabase.execSQL("Create Table Category {c_index integer primary key autoincrement, c_title text, c_remark text}");
        initInsert("맛집");
        initInsert("모텔");
        initInsert("미용실");
        initInsert("카페");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // DATABASE 버전에 따라 ALTER문을 사용해서 DATABASE구조를 변경할 때
    }

    private void initInsert(String title) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_title", title);
        database.insert("Category", null, contentValues);
    }

    public ArrayList<String> selectCategory() {
        Cursor cursor = database.query("Category", new String[]{"c_title"}, null, null, null, null, null);
        //database.execSQL("select c_title from Category order by c_index");
        ArrayList<String> list = new ArrayList<String>();
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("c_title")));
        }
        return list;
    }
}