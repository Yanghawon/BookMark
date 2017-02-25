package com.yangha.Bookmark.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Created by pjoo on 2017. 2. 25..
 */

public class BaseActivity extends AppCompatActivity {
    public static final int RELAYOUT_LISTACTIVITY = 1;
    public static final int RELAYOUT_ADDACTIVITY = 2;
    public static final int RELAYOUT_MAINACTIVITY = 3;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

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
        finish();
    }
}
