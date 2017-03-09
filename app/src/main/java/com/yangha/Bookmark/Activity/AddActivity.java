package com.yangha.Bookmark.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.yangha.Bookmark.Application.BookmarkResource;
import com.yangha.Bookmark.R;
import com.yangha.Bookmark.util.GpsInfo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class AddActivity extends BaseActivity implements LocationListener {

    private FloatingActionButton listAddBtn;
    private Button photoAddBtn;
    private Spinner spinner;
    private final int CAMERA_REQUEST = 10;
    private final int ALBUM_REQUEST = 11;
    private Uri uri;
    private ImageView image;
    private SimpleDateFormat dateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        }
        photoAddBtn = (Button) findViewById(R.id.add_photo_btn);
        photoAddBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //카메라 요청.
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });
        listAddBtn = (FloatingActionButton) findViewById(R.id.add_list_btn);
        spinner = (Spinner) findViewById(R.id.add_spinner);
        image = (ImageView) findViewById(R.id.add_photo);
        spinner.setAdapter(new CategoryAdapter(BookmarkResource.getInstance().getDBHelperManager().selectCategory()));

        listAddBtn.setOnClickListener(new View.OnClickListener() {
            GpsInfo gps = new GpsInfo(AddActivity.this);
            EditText title = (EditText) findViewById(R.id.add_title);
            EditText content = (EditText) findViewById(R.id.add_content);

            Spinner category = (Spinner) findViewById(R.id.add_spinner);
            RatingBar rating = (RatingBar) findViewById(R.id.add_ratingbar);

            @Override
            public void onClick(View v) {
//                lat 37.484269037° lng 126.9296760126°
                BookmarkResource.getInstance().getDBHelperManager().insertData(
                        gps.getLongitude(),gps.getLatitude(), title.getText().toString(), uri.getPath(), category.getSelectedItemPosition(), content.getText().toString(), rating.getRating(), "0");
                Toast.makeText(getApplicationContext(), "데이터가 입력되었습니다.", Toast.LENGTH_SHORT).show();
                relayout(RELAYOUT_MAINACTIVITY,0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        relayout(RELAYOUT_MAINACTIVITY,0);
    }

    @Override
    public void onLocationChanged(Location location) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    private class CategoryAdapter extends BaseAdapter {
        private ArrayList<String> list;

        public CategoryAdapter(ArrayList<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.spinner_item, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.spinner_item);
            tv.setText(list.get(position));
            return convertView;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST){
            //카메라에서 찍은 데이터를 가지고 옴.
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }else if(requestCode==ALBUM_REQUEST){

        }
    }

    /**
     * 파일 저장용 함수
     * @param imageBitmap
     * 비트맵 객체를 넣으면 파일로 저장.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fileSave(Bitmap imageBitmap){
        try {
            FileOutputStream outputStream = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"temp"+new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date())+".jpeg");
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,40,outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}