package com.yangha.Bookmark.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import com.yangha.Bookmark.Dto.DtoBookmark;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by pjoo on 2017. 2. 25..
 */

public class DBHelperManager extends SQLiteOpenHelper {
    private final String TAG =getClass().getName();
    private DtoBookmark mDto = new DtoBookmark();
    public double lat1;
    public double lon1;
    private DtoBookmark temp;
    private String parsingDate = "yyyy년MM월dd일 hh시mm분";

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

        Log.i(TAG, "insert data :"+getWritableDatabase().insert("BookMark", null, contentValues));
    }

    /**
     * BoomMark DB 데이터 Select 메서드
     *
     * @param num
     * @return
     */
    public DtoBookmark selectBookMarkData(int num) {
        Log.i(TAG,"selectBookMarkData"+num);
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
        // 데이터가 다 보이지 않음 왜 그럴까
        // 첫번째 데이터가 보이지 않음
        lat1 = latitude;
        lon1 = longitude;
        String sql = "select * from BookMark order by b_title asc;";
        switch (sort) {
            case 3:
                sql = "select * from BookMark order by b_rating desc;";
                break;
            case 4:
                sql = "select * from BookMark order by b_count desc;";
                break;
        }
        Cursor results = getReadableDatabase().rawQuery(sql, null);

        ArrayList<DtoBookmark> list = new ArrayList<DtoBookmark>();

        while (results.moveToNext()) {
            DtoBookmark dto = new DtoBookmark();
            dto.setIndex(results.getInt(results.getColumnIndex("b_index")));
            dto.setLongitude(results.getDouble(results.getColumnIndex("b_longitude")));
            dto.setLatitude(results.getDouble(results.getColumnIndex("b_latitude")));
            dto.setTitle(results.getString(results.getColumnIndex("b_title")));
            dto.setImage(results.getString(results.getColumnIndex("b_image")));
            dto.setCategory(results.getInt(results.getColumnIndex("b_category")));
            dto.setContent(results.getString(results.getColumnIndex("b_content")));
            dto.setRating(results.getFloat(results.getColumnIndex("b_rating")));
            dto.setDate(results.getString(results.getColumnIndex("b_date")));
            dto.setCount(results.getInt(results.getColumnIndex("b_count")));
            if (sort == 1) {
                double distance = calcDistance(lat1, lon1, dto.getLatitude(), dto.getLongitude());
                dto.setDistance(distance);
            } else if (sort == 2) {
                try {
                    dto.setDateGap((new SimpleDateFormat(parsingDate).parse(new SimpleDateFormat(parsingDate).format(new Date())).getTime() - new SimpleDateFormat(parsingDate).parse(dto.getDate()).getTime()) / (60 * 1000));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            list.add(dto);
        }

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i+1; j < list.size(); j++) {
                if((sort==1 && list.get(i).getDistance() > list.get(j).getDistance())||
                        (sort==2 && list.get(i).getDateGap() > list.get(j).getDateGap())){
                    temp = list.get(j);
                    list.set(j, list.get(i));
                    list.set(i, temp);
                }
            }
        }
        Log.i(getDatabaseName(), "" + list.size());
        return list;
    }

    //    public double calDistance(double lat2, double lon2) {
//
//        double theta, dist;
//        theta = lon1 - lon2;
//        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
//                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//
//        dist = dist * 60 * 1.1515;
//        dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
//        dist = dist * 1000.0;      // 단위  km 에서 m 로 변환
//
//        // return이 이상해
//        return dist;
//    }
    public double calcDistance(double lat1, double lon1, double lat2, double lon2) {
        double distance;

        Location locationA = new Location("point A");

        locationA.setLatitude(lat1);
        locationA.setLongitude(lon1);

        Location locationB = new Location("point B");

        locationB.setLatitude(lat2);
        locationB.setLongitude(lon2);

        distance = locationA.distanceTo(locationB);

        return distance;
    }

    /*
    // 주어진 도(degree) 값을 라디언으로 변환
    private double deg2rad(double deg) {
        return (double) (deg * Math.PI / (double) 180d);
    }

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    private double rad2deg(double rad) {
        return (double) (rad * (double) 180d / Math.PI);
    }
    */
    public ArrayList<DtoBookmark> selectBookMarkDataForSearch(String what) {
        ArrayList<DtoBookmark> list = new ArrayList<DtoBookmark>();

        String sql = "select * from BookMark where b_title like %" + what + "%;";
        Cursor result = getReadableDatabase().rawQuery(sql, null);
        while (result.moveToFirst()) {
            DtoBookmark dto = new DtoBookmark();

            dto.setIndex(result.getInt(result.getColumnIndex("b_index")));
            dto.setLongitude(result.getDouble(result.getColumnIndex("b_longitude")));
            dto.setLatitude(result.getDouble(result.getColumnIndex("b_latitude")));
            dto.setTitle(result.getString(result.getColumnIndex("b_title")));
            dto.setImage(result.getString(result.getColumnIndex("b_image")));
            dto.setCategory(result.getInt(result.getColumnIndex("b_category")));
            dto.setContent(result.getString(result.getColumnIndex("b_content")));
            dto.setRating(result.getFloat(result.getColumnIndex("b_rating")));
            dto.setDate(result.getString(result.getColumnIndex("b_date")));
            dto.setCount(result.getInt(result.getColumnIndex("b_count")));

            list.add(dto);
        }
        return list;
    }
    public void updateBookMarkData(DtoBookmark dto){

        ContentValues contentValues = new ContentValues();
        contentValues.put("b_image", dto.getImage());
        contentValues.put("b_category", dto.getCategory());
        contentValues.put("b_content", dto.getContent());
        contentValues.put("b_rating", dto.getRating());

    }
}