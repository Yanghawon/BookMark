package com.yangha.Bookmark.Activity;

import android.os.Bundle;

import com.yangha.Bookmark.R;

public class ListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        relayout(RELAYOUT_MAINACTIVITY);
    }
}
