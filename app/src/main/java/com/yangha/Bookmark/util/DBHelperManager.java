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


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create Table BookMark (b_index integer primary key autoincrement, b_longitude real, b_latitude real, " +
                "b_title text, b_image text, b_category integer, b_content text, b_rating real, b_remark text)");
        sqLiteDatabase.execSQL("Create Table Category (c_index integer primary key autoincrement, c_title text, c_remark text)");
        initInsert("맛집", sqLiteDatabase);
        initInsert("모텔", sqLiteDatabase);
        initInsert("미용실", sqLiteDatabase);
        initInsert("카페", sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // DATABASE 버전에 따라 ALTER문을 사용해서 DATABASE구조를 변경할 때
    }
    /**  * 처음 데이터를 만들어서 넣는 Category  * @param title  * 카테고리에 해당하는 데이터를 하나씩 입력하는 방식.  */

    private void initInsert(String title, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_title", title);
        db.insert("Category", null, contentValues);
    }
    /**  * 현재 입력되어 있는 Category의 데이터를 반환받음  * @return  * Category에 있는 현재 데이터를 ArrayList형식으로 받음  */
    public ArrayList<String> selectCategory() {
        Cursor cursor = getReadableDatabase().query("Category", new String[]{"c_title"}, null, null, null, null, null);
        //database.execSQL("select c_title from Category order by c_index");
        ArrayList<String> list = new ArrayList<String>();
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("c_title")));
        }
        return list;
    }
}