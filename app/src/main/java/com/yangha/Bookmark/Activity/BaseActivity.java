package com.yangha.Bookmark.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pjoo on 2017. 2. 25..
 */

public class BaseActivity extends AppCompatActivity {
    public static final int RELAYOUT_LISTACTIVITY = 1;
    public static final int RELAYOUT_ADDACTIVITY = 2;
    public static final int RELAYOUT_MAINACTIVITY = 3;
    public void relayout(int a){
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
        }
        startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}