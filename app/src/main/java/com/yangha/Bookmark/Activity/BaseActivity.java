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
    /**
     * ListActivity로 이동 
     * */
    public static final int RELAYOUT_LISTACTIVITY = 1;
    /** 
     * AddActivity 로 이동 
     */
    public static final int RELAYOUT_ADDACTIVITY = 2;
    /**
     * MainActivity 로 이동 
     */
    public static final int RELAYOUT_MAINACTIVITY = 3;
    /**
     * DetailActivity 로 이동 
     */
    public static final int RELAYOUT_DETAILACTIVITY = 4;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    /**
     * relayout을 통해 Activity를 control함. 
     * @param a <br>
     *  RELAYOUT_LISTACTIVITY = 1;<br> 
     *  RELAYOUT_ADDACTIVITY = 2;<br> 
     *  RELAYOUT_MAINACTIVITY = 3;<br>
     */
    public void relayout(int a, int data){
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
            case RELAYOUT_DETAILACTIVITY:
                intent = new Intent(BaseActivity.this,DetailActivity.class);
                intent.putExtra("selectedID",data);
                break;
        }
        startActivity(intent);
        finish();
    }
}
