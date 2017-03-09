package com.yangha.Bookmark.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.yangha.Bookmark.Application.BookmarkResource;
import com.yangha.Bookmark.R;
import com.yangha.Bookmark.util.GpsInfo;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MainActivity extends BaseActivity implements LocationListener {

    private static String API_KEY = "17ab215709e639add24c6b924c031c70";
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private GpsInfo gps;
    private MapView mapView;
    private MapPOIItem marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab1 = (FloatingActionButton) findViewById(R.id.fab_list);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_add);

        gps = new GpsInfo(this);

        mapView = new MapView(this);
        mapView.setDaumMapApiKey(API_KEY);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // gps기준 서클 설정
        MapCircle circle1 = new MapCircle(
                MapPoint.mapPointWithGeoCoord(gps.getLatitude(), gps.getLongitude()), // center
                100, // radius
                Color.argb(40, 0, 0, 0), // 원의 색상
                Color.argb(0, 0, 0, 0) // 원 안의 색상
        );
        circle1.setTag(100);
        mapView.addCircle(circle1);

        MapCircle circle2 = new MapCircle(
                MapPoint.mapPointWithGeoCoord(gps.getLatitude(), gps.getLongitude()), // center
                200, // radius
                Color.argb(40, 0, 0, 0), // strokeColor
                Color.argb(0, 0, 0, 0) // fillColor
        );
        circle2.setTag(200);
        mapView.addCircle(circle2);


        mapView.setMapViewEventListener(new MapView.MapViewEventListener() {
            @Override
            public void onMapViewInitialized(MapView mapView) {
                handler.sendMessage(handler.obtainMessage(1, gps.getLocation()));
                handler.sendMessage(handler.obtainMessage(2));
                //MapView가 사용가능 한 상태가 되었음을 알려준다.
                //onMapViewInitialized()가 호출된 이후에 MapView 객체가 제공하는 지도 조작 API들을 사용할 수 있다.
            }

            @Override
            public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
                //지도 중심 좌표가 이동한 경우 호출된다.
            }

            @Override
            public void onMapViewZoomLevelChanged(MapView mapView, int i) {
                //지도 확대/축소 레벨이 변경된 경우 호출된다.
            }

            @Override
            public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
                //사용자가 지도 위를 터치한 경우 호출된다.
            }

            @Override
            public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
                //사용자가 지도 위 한 지점을 더블 터치한 경우 호출된다.
            }

            @Override
            public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
                // 사용자가 지도 위 한 지점을 길게 누른 경우(long press) 호출된다.
            }

            @Override
            public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
                // 사용자가 지도 드래그를 시작한 경우 호출된다.
            }

            @Override
            public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
                // 사용자가 지도 드래그를 끝낸 경우 호출된다.
            }

            @Override
            public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
                // 지도의 이동이 완료된 경우 호출된다
            }
        });

        mapView.setPOIItemEventListener(new MapView.POIItemEventListener() {
            @Override
            public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
                // 사용자가 MapView 에 등록된 POI Item 아이콘(마커)를 터치한 경우 호출된다
            }

            @Override
            public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

            }

            @Override
            public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

            }

            @Override
            public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relayout(RELAYOUT_LISTACTIVITY, 0);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relayout(RELAYOUT_ADDACTIVITY, 0);
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        handler.sendMessage(handler.obtainMessage(1, location));
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Location location = (Location) msg.obj;
                    marker = new MapPOIItem();
                    marker.setItemName("현재 위치");
                    marker.setTag(0);
                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(location.getLatitude(), location.getLongitude()));
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                    mapView.setMapCenterPoint(marker.getMapPoint(), true);
                    mapView.addPOIItem(marker);
                    mapView.setZoomLevel(0, true);
                    break;
                case 2:
                    for (int i = 1; BookmarkResource.getInstance().getDBHelperManager().selectBookMarkData(i)==null ; i++) {
                        marker = new MapPOIItem();
                        marker.setItemName(BookmarkResource.getInstance().getDBHelperManager().selectBookMarkData(i).getTitle());
                        Log.i(getLocalClassName(), "aa" + BookmarkResource.getInstance().getDBHelperManager().selectBookMarkData(i).getTitle());
                        Log.i(getLocalClassName(), "bb" + BookmarkResource.getInstance().getDBHelperManager().selectBookMarkData(i).getLongitude());
                        Log.i(getLocalClassName(), "cc" + BookmarkResource.getInstance().getDBHelperManager().selectBookMarkData(i).getLatitude());
                        marker.setTag(1);
                        marker.setMapPoint(MapPoint.mapPointWithCONGCoord(BookmarkResource.getInstance().getDBHelperManager().selectBookMarkData(i).getLatitude(),
                                BookmarkResource.getInstance().getDBHelperManager().selectBookMarkData(i).getLongitude()));
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        mapView.addPOIItem(marker);
                    }
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("정말 종료하시겠습니까?");
        dialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
