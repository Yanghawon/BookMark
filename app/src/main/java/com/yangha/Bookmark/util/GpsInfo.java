package com.yangha.Bookmark.util;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;

import com.yangha.Bookmark.Activity.AddActivity;
import com.yangha.Bookmark.Activity.BaseActivity;
import com.yangha.Bookmark.Activity.MainActivity;

public class GpsInfo extends Service {
    private final BaseActivity mContext;
    private LocationListener mListner;
    // GPS 상태값
    boolean isGetLocation = false;

    Location location;
    double lat; // 위도
    double lon; // 경도

    // 최소 GPS 정보 업데이트 거리 10미터
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    // 최소 GPS 정보 업데이트 시간 밀리세컨이므로 5초
    private static final long MIN_TIME_BW_UPDATES = 1000 * 5 * 1;

    protected LocationManager locationManager;

    public GpsInfo(BaseActivity context) {
        this.mContext = context;
        if (context instanceof MainActivity){
            this.mListner = (MainActivity)context;
        }else if(context instanceof AddActivity){
            this.mListner = (AddActivity)context;
        }
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            } else
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, mListner);

            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    // 위도 경도 저장
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                }
            }

            if (location == null) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, mListner);
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    /**
     * GPS 종료
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }else
                locationManager.removeUpdates(mListner);
        }
    }

    /**
     * 위도값을 가져옵니다.
     */

    public double getLatitude() {
        if (location != null) {
            lat = location.getLatitude();
        }
        return lat;
    }

    /**
     * 경도값을 가져옵니다.
     */
    public double getLongitude() {
        if (location != null) {
            lon = location.getLongitude();
        }
        return lon;
    }

    /**
     * GPS 나 wife 정보가 켜져있는지 확인합니다.
     */
    public boolean isGetLocation() {
        return this.isGetLocation;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void setmListner(LocationListener mListner) {
        this.mListner = mListner;
    }
}