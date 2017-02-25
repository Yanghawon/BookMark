package com.yangha.Bookmark.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yangha.Bookmark.R;
import com.yangha.Bookmark.util.GpsInfo;

public class AddActivity extends BaseActivity {

    private GpsInfo gps;
    private IntroActivity intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button photoAdd = (Button) findViewById(R.id.photoAdd);
        Button listAdd = (Button) findViewById(R.id.list_add);

        listAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GpsInfo(AddActivity.this);
                if (gps.isGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Toast.makeText(getApplicationContext(), "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude, Toast.LENGTH_LONG).show();

                    Log.i(getLocalClassName(), latitude + "," + longitude);
                } else {
                    // GPS 를 사용할수 없으므로
                    intro.showSettingsAlert();
                }
            }
        });
    }

}