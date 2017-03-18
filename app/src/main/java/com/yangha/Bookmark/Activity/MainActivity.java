package com.yangha.Bookmark.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.yangha.Bookmark.Application.BookmarkResource;
import com.yangha.Bookmark.Dto.DtoBookmark;
import com.yangha.Bookmark.R;
import com.yangha.Bookmark.util.GpsInfo;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MapView.CurrentLocationEventListener, MapView.POIItemEventListener {

    private static String API_KEY = "17ab215709e639add24c6b924c031c70";
    private FloatingActionButton fab1, fab2, fab_menuDown, fab_menu1, fab_menu2, fab_menu3, fab_menu4;
    private TextView menu1_tv, menu2_tv, menu3_tv, menu4_tv, cell_tv;
    private GpsInfo gps;
    private MapView mapView;
    private MapPOIItem marker;
    private MapCircle circle1, circle2;
    int index;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab1 = (FloatingActionButton) findViewById(R.id.fab_list);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_menuDown = (FloatingActionButton) findViewById(R.id.fab_menuDown);
        fab_menu1 = (FloatingActionButton) findViewById(R.id.fab_menu1);
        fab_menu2 = (FloatingActionButton) findViewById(R.id.fab_menu2);
        fab_menu3 = (FloatingActionButton) findViewById(R.id.fab_menu3);
        fab_menu4 = (FloatingActionButton) findViewById(R.id.fab_menu4);
        menu1_tv = (TextView) findViewById(R.id.menu1_tv);
        menu2_tv = (TextView) findViewById(R.id.menu2_tv);
        menu3_tv = (TextView) findViewById(R.id.menu3_tv);
        menu4_tv = (TextView) findViewById(R.id.menu4_tv);

        marker = new MapPOIItem();
        gps = new GpsInfo(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapView = new MapView(this);
        mapView.setDaumMapApiKey(API_KEY);
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        mapViewContainer.addView(mapView);
        marker = new MapPOIItem();
        gps = new GpsInfo(this);
        circle1 = new MapCircle(
                MapPoint.mapPointWithGeoCoord(gps.getLatitude(), gps.getLongitude()), // center
                100, // radius
                Color.argb(40, 0, 0, 0), // 원의 색상
                Color.argb(0, 0, 0, 0) // 원 안의 색상
        );
        circle2 = new MapCircle(
                MapPoint.mapPointWithGeoCoord(gps.getLatitude(), gps.getLongitude()), // center
                200, // radius
                Color.argb(40, 0, 0, 0), // 원의 색상
                Color.argb(0, 0, 0, 0) // 원 안의 색상
        );
        mapView.setCurrentLocationEventListener(this);
        mapView.setMapViewEventListener(new MapView.MapViewEventListener() {
            @Override
            public void onMapViewInitialized(MapView mapView) {
                Log.i(TAG, "onMapViewInitialized");
                handler.sendEmptyMessage(1);
                //MapView가 사용가능 한 상태가 되었음을 알려준다.
                //onMapViewInitialized()가 호출된 이후에 MapView 객체가 제공하는 지도 조작 API들을 사용할 수 있다.
            }

            @Override
            public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
                //지도 중심 좌표가 이동한 경우 호출된다.
                Log.i(TAG, "onMapViewCenterPointMoved");
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onMapViewZoomLevelChanged(MapView mapView, int i) {
                //지도 확대/축소 레벨이 변경된 경우 호출된다.
                Log.i(TAG, "onMapViewZoomLevelChanged");
            }

            @Override
            public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
                //사용자가 지도 위를 터치한 경우 호출된다.
                Log.i(TAG, "onMapViewSingleTapped");
            }

            @Override
            public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
                //사용자가 지도 위 한 지점을 더블 터치한 경우 호출된다.
                Log.i(TAG, "onMapViewDoubleTapped");
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
        fab_menu1.setVisibility(View.INVISIBLE);
        fab_menu2.setVisibility(View.INVISIBLE);
        fab_menu3.setVisibility(View.INVISIBLE);
        fab_menu4.setVisibility(View.INVISIBLE);
        menu1_tv.setVisibility(View.INVISIBLE);
        menu2_tv.setVisibility(View.INVISIBLE);
        menu3_tv.setVisibility(View.INVISIBLE);
        menu4_tv.setVisibility(View.INVISIBLE);
        fab_menuDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = new AlphaAnimation(0, 1);
                animation.setDuration(1000);
                if (flag == false) {
                    fab_menu1.setVisibility(View.VISIBLE);
                    fab_menu2.setVisibility(View.VISIBLE);
                    fab_menu3.setVisibility(View.VISIBLE);
                    fab_menu4.setVisibility(View.VISIBLE);
                    menu1_tv.setVisibility(View.VISIBLE);
                    menu2_tv.setVisibility(View.VISIBLE);
                    menu3_tv.setVisibility(View.VISIBLE);
                    menu4_tv.setVisibility(View.VISIBLE);
                    fab_menu1.setAnimation(animation);
                    fab_menu2.setAnimation(animation);
                    fab_menu3.setAnimation(animation);
                    fab_menu4.setAnimation(animation);
                    menu1_tv.setAnimation(animation);
                    menu2_tv.setAnimation(animation);
                    menu3_tv.setAnimation(animation);
                    menu4_tv.setAnimation(animation);

                    flag = true;
                } else {
                    fab_menu1.setVisibility(View.INVISIBLE);
                    fab_menu2.setVisibility(View.INVISIBLE);
                    fab_menu3.setVisibility(View.INVISIBLE);
                    fab_menu4.setVisibility(View.INVISIBLE);
                    menu1_tv.setVisibility(View.INVISIBLE);
                    menu2_tv.setVisibility(View.INVISIBLE);
                    menu3_tv.setVisibility(View.INVISIBLE);
                    menu4_tv.setVisibility(View.INVISIBLE);

                    flag = false;
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mapView.removeAllCircles();
                    mapView.removeAllPOIItems();
                    View cell_view1 = getLayoutInflater().inflate(R.layout.cell_item_view,null);
                    ((TextView)cell_view1.findViewById(R.id.cell_item_tv)).setText("현재위치");
                    (cell_view1.findViewById(R.id.cell_item_star)).setVisibility(View.VISIBLE);
                    marker.setCustomCalloutBalloon(cell_view1);
                    marker.setTag(0);
                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(gps.getLatitude(), gps.getLongitude()));
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                    mapView.setMapCenterPoint(marker.getMapPoint(), true);
                    mapView.addPOIItem(marker);
                    mapView.setZoomLevel(0, true);
                    // gps기준 서클 설정
                    circle1.setTag(100);
                    mapView.addCircle(circle1);
                    circle2.setTag(200);
                    mapView.addCircle(circle2);
                    ArrayList<DtoBookmark> bookmark = BookmarkResource.getInstance().getDBHelperManager().selectBookMarkDataAll(0, 0, 0);
                    for (int i = 0; i < bookmark.size(); i++) {
                        MapPOIItem marker2 = new MapPOIItem();
                        marker2.setTag(bookmark.get(i).getIndex());
                        marker2.setItemName(bookmark.get(i).getTitle());

                        Log.i(TAG, bookmark.get(i).getTitle() + " " + bookmark.get(i).getLatitude() + " " + bookmark.get(i).getLongitude());
                        marker2.setMapPoint(MapPoint.mapPointWithGeoCoord(bookmark.get(i).getLatitude(),
                                bookmark.get(i).getLongitude()));
                        marker2.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                        marker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        mapView.addPOIItem(marker2);
                    }
                    mapView.setPOIItemEventListener(MainActivity.this);
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
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

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        Log.i(TAG, "onCurrentLocationUpdate");
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
        Log.i(TAG, "onCurrentLocationDeviceHeadingUpdate");
    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {
        Log.i(TAG, "onCurrentLocationUpdateFailed");
    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {
        Log.i(TAG, "onCurrentLocationUpdateCancelled");
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        handler.sendEmptyMessage(1);
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        // 사용자가 MapView 에 등록된 POI Item 아이콘(마커)를 터치한 경우 호출된다
        Log.i(TAG, "onPOIItemSelected"+mapPOIItem.getItemName());
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        // 사용자가 마커의 말풍선을 클릭했을 때 호출?
        Log.i(TAG, "onCalloutBalloonOfPOIItemTouched"+mapPOIItem.getItemName());
        index = mapPOIItem.getTag();
        if (index != 0){
            relayout(RELAYOUT_DETAILACTIVITY,index);
        }
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        Log.i(TAG, "onCalloutBalloonOfPOIItemTouched"+mapPOIItem.getItemName());
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    private class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;
        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.cell_item_view, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            ((ImageView) mCalloutBalloon.findViewById(R.id.cell_item_star)).setImageResource(R.drawable.ic_star_rate_black_18dp);
            ((TextView) mCalloutBalloon.findViewById(R.id.cell_item_tv)).setText(poiItem.getItemName());
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }
}
