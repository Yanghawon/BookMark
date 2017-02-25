package com.yangha.Bookmark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MainActivity extends AppCompatActivity {
    private static String API_KEY = "17ab215709e639add24c6b924c031c70";
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab1 = (FloatingActionButton)findViewById(R.id.fab_list);
        fab2 = (FloatingActionButton)findViewById(R.id.fab_add);

        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey(API_KEY);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        mapView.setMapViewEventListener(new MapView.MapViewEventListener() {
            @Override
            public void onMapViewInitialized(MapView mapView) {
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);
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
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
    }
}
