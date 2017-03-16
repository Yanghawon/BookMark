package com.yangha.Bookmark.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
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
import com.yangha.Bookmark.Dto.DtoBookmark;
import com.yangha.Bookmark.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static android.os.Environment.DIRECTORY_PICTURES;


@TargetApi(Build.VERSION_CODES.N)
public class DetailActivity extends BaseActivity {
    private EditText edt_title, edt_content;
    private RatingBar rating;
    private Spinner spinner_category;
    private ImageView imageView;
    private FloatingActionButton share_btn, edit_btn, ok_btn;
    private Button detail_photo_btn;
    private Uri uri;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");

    Intent intent = getIntent();
    DtoBookmark mDto = BookmarkResource.getInstance().getDBHelperManager().selectBookMarkData(intent.getExtras().getInt("selectedID"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.i(TAG,""+intent.getIntExtra("selectedID",0));

        edt_title = (EditText) findViewById(R.id.detail_title);
        edt_title.setText(mDto.getTitle());
        edt_title.setFocusable(false);
        edt_title.setClickable(false);

        edt_content = (EditText) findViewById(R.id.detail_content);
        edt_content.setText(mDto.getContent());
        edt_content.setFocusable(false);
        edt_content.setClickable(false);

        rating = (RatingBar) findViewById(R.id.detail_ratingbar);
        rating.setRating(mDto.getRating());
        rating.setFocusable(false);
        rating.setClickable(false);

        spinner_category = (Spinner) findViewById(R.id.detail_spinner);
        spinner_category.setAdapter(new CategoryAdapter(BookmarkResource.getInstance().getDBHelperManager().selectCategory()));
        spinner_category.setSelection(mDto.getCategory());
        spinner_category.setFocusable(false);
        spinner_category.setClickable(false);

        imageView = (ImageView) findViewById(R.id.detail_photo);
        Bitmap bitmap = BitmapFactory.decodeFile(mDto.getImage());
        imageView.setImageBitmap(bitmap);

        detail_photo_btn = (Button) findViewById(R.id.detail_photo_btn);
        detail_photo_btn.setFocusable(false);
        detail_photo_btn.setClickable(false);

        ok_btn = (FloatingActionButton) findViewById(R.id.detail_OK_btn);

        detail_photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
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

        share_btn = (FloatingActionButton) findViewById(R.id.detail_share_btn);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        edit_btn = (FloatingActionButton) findViewById(R.id.detail_edit_btn);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_content.setText("");
                edt_content.setFocusable(true);
                edt_content.setClickable(true);

                rating.setRating(2.5f);
                rating.setFocusable(true);
                rating.setClickable(true);

                spinner_category.setAdapter(new CategoryAdapter(BookmarkResource.getInstance().getDBHelperManager().selectCategory()));
                spinner_category.setSelection(0);
                spinner_category.setFocusable(true);
                spinner_category.setClickable(true);

                detail_photo_btn.setFocusable(true);
                detail_photo_btn.setClickable(true);

                edit_btn.setVisibility(View.INVISIBLE);
                ok_btn.setVisibility(View.VISIBLE);
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
                        dialog.setPositiveButton("편집 완료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDto.setContent(edt_content.getText().toString());
                                mDto.setRating(rating.getRating());
                                mDto.setCategory(spinner_category.getSelectedItemPosition());
                                mDto.setImage(uri.getPath());
                                BookmarkResource.getInstance().getDBHelperManager().updateBookMarkData(mDto);
                                Toast.makeText(getBaseContext(), "편집이 성공적으로 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        relayout(RELAYOUT_LISTACTIVITY, 0);
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
            imageView.setImageBitmap(imageBitmap);
        } else if (requestCode == ALBUM_REQUEST) {
            Bitmap image_bitmap = null;
            try {
                image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //배치해놓은 ImageView에 set
            imageView.setImageBitmap(image_bitmap);
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