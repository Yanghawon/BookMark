package com.yangha.Bookmark.Activity;

import android.os.Bundle;

import com.yangha.Bookmark.R;

public class DetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        relayout(RELAYOUT_MAINACTIVITY);
    }
}