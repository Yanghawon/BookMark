package com.yangha.Bookmark.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

/**
 * Created by pjoo on 2017. 2. 25..
 */

public class BaseActivity extends AppCompatActivity implements LocationListener{
    public final String TAG = getClass().getName();
    public final int CAMERA_REQUEST = 10;
    public final int ALBUM_REQUEST = 11;
    /**
     * ListActivity로 이동 
     * */
    public static final int RELAYOUT_LISTACTIVITY = 1;
    /** 
     * AddActivity 로 이동 
     */
    public static final int RELAYOUT_ADDACTIVITY = 2;
    /**
     * MainActivity 로 이동 
     */
    public static final int RELAYOUT_MAINACTIVITY = 3;
    /**
     * DetailActivity 로 이동 
     */
    public static final int RELAYOUT_DETAILACTIVITY = 4;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    /**
     * relayout을 통해 Activity를 control함. 
     * @param a <br>
     *  RELAYOUT_LISTACTIVITY = 1;<br> 
     *  RELAYOUT_ADDACTIVITY = 2;<br> 
     *  RELAYOUT_MAINACTIVITY = 3;<br>
     */
    public void relayout(int a, int data){
        Intent intent = null;
        switch (a){
            case RELAYOUT_LISTACTIVITY:
                intent = new Intent(BaseActivity.this,ListActivity.class);
                break;
            case RELAYOUT_ADDACTIVITY:
                intent = new Intent(BaseActivity.this,AddActivity.class);
                break;
            case RELAYOUT_MAINACTIVITY:
                intent = new Intent(BaseActivity.this,MainActivity.class);
                break;
            case RELAYOUT_DETAILACTIVITY:
                intent = new Intent(BaseActivity.this,DetailActivity.class);
                intent.putExtra("selectedID",data);
                break;
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG,"current onLocationChanged");
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.i(TAG,"current onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.i(TAG,"current onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.i(TAG,"current onProviderDisabled");
    }

    /**
     * 외부 저장소 사용 권한 요청
     */
    private void permissionCheck(){
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
        }
    }
}
