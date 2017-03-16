package com.yangha.Bookmark.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.util.Log;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static android.os.Environment.DIRECTORY_PICTURES;

@TargetApi(Build.VERSION_CODES.N)
public class AddActivity extends BaseActivity implements LocationListener {
    private FloatingActionButton listAddBtn;
    private Button photoAddBtn;
    private Spinner spinner;
    private ImageView image;
    private Uri uri;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        photoAddBtn = (Button) findViewById(R.id.add_photo_btn);
        photoAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AddActivity.this);
                dialog.setTitle("사진 앨범?");
                dialog.setPositiveButton("카메라", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //카메라 요청.
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }
                });
                dialog.setNegativeButton("앨범", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                        startActivityForResult(intent, ALBUM_REQUEST);
                    }
                });
                dialog.show();
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
                        gps.getLongitude(), gps.getLatitude(), title.getText().toString(), uri.getPath(), category.getSelectedItemPosition(), content.getText().toString(), rating.getRating(), "0");
                Toast.makeText(getApplicationContext(), "데이터가 입력되었습니다.", Toast.LENGTH_SHORT).show();
                relayout(RELAYOUT_MAINACTIVITY, 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        relayout(RELAYOUT_MAINACTIVITY, 0);
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

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
        if (requestCode == CAMERA_REQUEST) {
            //카메라에서 찍은 데이터를 가지고 옴.
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Log.i(TAG, "file save : " + fileSave(imageBitmap));
            image.setImageBitmap(imageBitmap);
        } else if (requestCode == ALBUM_REQUEST) {
            Bitmap image_bitmap = null;
            try {
                image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //배치해놓은 ImageView에 set
            image.setImageBitmap(image_bitmap);
        }
    }

    /**
     * 파일 저장용 함수
     *
     * @param imageBitmap 비트맵 객체를 넣으면 파일로 저장.
     */
    @TargetApi(Build.VERSION_CODES.N)
    private String fileSave(Bitmap imageBitmap) {
        Log.i(TAG, "file Save log");
        File file = null;
        try {
            //외부 저장소 갤리러 경로 가져오기(bookmark 디렉토리를 만들어주기)
            String path = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).getAbsolutePath() + "/bookmark/";
            //파일명 생성 날짜+시간
            String filename = dateFormat.format(new Date()) + ".png";
            File dir = new File(path);
            file = new File(path + filename);
            Log.i(TAG, "file dir make path : " + dir.mkdirs());
            //외부 저장소가 정상적으로 연결되어 있는지 확인(기기별 문제점이 있을 수 있어서 미리 체크)
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
                //외부 저장소가 정상적으로 연결되어 있으면 파일을 만들어줌(이미지 생성 전에 빈 파일 만들기)
                if (!file.exists() && file.createNewFile()) ;
            //빈 파일과 이미지를 넣어줄 binary outputstream에 연결.
            FileOutputStream outputStream = new FileOutputStream(file);
            //이미지를 만들어서 넣어주는 작업
            if (imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream))
                Log.i(TAG, "make file");

            //갤러리에 넣기
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            uri = Uri.fromFile(file);
            mediaScanIntent.setData(uri);
            this.sendBroadcast(mediaScanIntent);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return file != null ? file.getAbsolutePath() : "null";
        }
    }

}
