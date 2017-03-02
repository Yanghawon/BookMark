package com.yangha.Bookmark.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yangha.Bookmark.Dto.DtoBookmark;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by pjoo on 2017. 2. 25..
 */

public class DBHelperManager extends SQLiteOpenHelper {
    private DtoBookmark mDto = new DtoBookmark();
    public double lat1;
    public double lon1;
    private String parsingDate = "yyyy-MM-dd_hh_mm_ss";
    public DBHelperManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create Table BookMark (b_index integer primary key autoincrement, b_longitude real, b_latitude real, " +
                "b_title text, b_image text, b_category integer, b_content text, b_rating real, b_remark text, b_date text, b_count integer)");
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

    /**
     *   * 처음 데이터를 만들어서 넣는 Category  * @param title  * 카테고리에 해당하는 데이터를 하나씩 입력하는 방식.
     */

    private void initInsert(String title, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_title", title);
        db.insert("Category", null, contentValues);
    }

    /**
     * 현재 입력되어 있는 Category의 데이터를 반환받음
     *
     * @return  Category에 있는 현재 데이터를 ArrayList형식으로 받음
     */
    public ArrayList<String> selectCategory() {
        Cursor cursor = getReadableDatabase().query("Category", new String[]{"c_title"}, null, null, null, null, null);
        //database.execSQL("select c_title from Category order by c_index");
        ArrayList<String> list = new ArrayList<String>();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex("c_title")));
        }
        return list;
    }

    /**
     * BookMark DB에 데이터 insert
     * W : longitude latitude 위치 바뀌어 있으니 조심
     */
    public void insertData(double longitude, double latitude, String title, String image, int category, String content, float rating, String remark) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("b_longitude", longitude);
        contentValues.put("b_latitude", latitude);
        contentValues.put("b_title", title);
        contentValues.put("b_image", image);
        contentValues.put("b_category", category);
        contentValues.put("b_content", content);
        contentValues.put("b_rating", rating);
        contentValues.put("b_remark", "0");
        contentValues.put("b_date", new SimpleDateFormat(parsingDate).format(new Date()));
        getWritableDatabase().insert("BookMark", null, contentValues);
    }

    /**
     * BoomMark DB 데이터 Select 메서드
     *
     * @param num
     * @return
     */
    public DtoBookmark selectBookMarkData(int num) {
        String sql = "select * from BookMark where b_index = " + num + ";";
        Cursor result = getReadableDatabase().rawQuery(sql, null);

        // result(Cursor 객체)가 비어 있으면 false 리턴
        if (result.moveToFirst()) {
            mDto.setIndex(num);
            mDto.setLongitude(result.getDouble(result.getColumnIndex("b_longitude")));
            mDto.setLatitude(result.getDouble(result.getColumnIndex("b_latitude")));
            mDto.setTitle(result.getString(result.getColumnIndex("b_title")));
            mDto.setImage(result.getString(result.getColumnIndex("b_image")));
            mDto.setCategory(result.getInt(result.getColumnIndex("b_category")));
            mDto.setContent(result.getString(result.getColumnIndex("b_content")));
            mDto.setRating(result.getFloat(result.getColumnIndex("b_rating")));
            mDto.setDate(result.getString(result.getColumnIndex("b_date")));
            mDto.setCount(result.getInt(result.getColumnIndex("b_count")));
        }
        return mDto;
    }

    /**
     * BookMark DB 데이터 전체 가져오기
     *
     * @return
     */
    public ArrayList<DtoBookmark> selectBookMarkDataAll(int sort, double longitude, double latitude) {
        //"가나다 순", "거리 순", "최신 순", "별점 순", "조회 순"
        lat1 = latitude;
        lon1 = longitude;
        String sql = "select * from BookMark order by b_title asc;";
        switch (sort) {
            case 3:
                sql = "select * from BookMark order by b_rating asc;";
            case 4:
                sql = "select * from BookMark order by b_count asc;";
        }

        Cursor results = getReadableDatabase().rawQuery(sql, null);

        ArrayList<DtoBookmark> list = new ArrayList<DtoBookmark>();

        results.moveToFirst();

        while (results.isAfterLast()) {
            mDto.setIndex(results.getInt(results.getColumnIndex("b_index")));
            mDto.setLongitude(results.getDouble(results.getColumnIndex("b_longitude")));
            mDto.setLatitude(results.getDouble(results.getColumnIndex("b_latitude")));
            mDto.setTitle(results.getString(results.getColumnIndex("b_title")));
            mDto.setImage(results.getString(results.getColumnIndex("b_image")));
            mDto.setCategory(results.getInt(results.getColumnIndex("b_category")));
            mDto.setContent(results.getString(results.getColumnIndex("b_content")));
            mDto.setRating(results.getFloat(results.getColumnIndex("b_rating")));
            mDto.setDate(results.getString(results.getColumnIndex("b_date")));
            mDto.setCount(results.getInt(results.getColumnIndex("b_count")));
            if(sort ==1){
                double distance = calDistance(mDto.getLatitude(), mDto.getLongitude());
                mDto.setDistance(distance);
            } else if (sort == 2) {
                try {
                    mDto.setDateGap(new SimpleDateFormat(parsingDate).parse(mDto.getDate()).compareTo(new Date()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            list.add(mDto);
        }

        if(sort ==1){
            for(int i=0;i<list.size();i++){
                for (int j=i+1;j<list.size();j++){

                }
            }
        }else if(sort==2){

        }
        return list;
    }


    public double calDistance(double lat2, double lon2){

        double theta, dist;
        theta = lon1 - lon2;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
        dist = dist * 1000.0;      // 단위  km 에서 m 로 변환

        return dist;
    }

    // 주어진 도(degree) 값을 라디언으로 변환
    private double deg2rad(double deg){
        return (double)(deg * Math.PI / (double)180d);
    }

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    private double rad2deg(double rad){
        return (double)(rad * (double)180d / Math.PI);
    }
}