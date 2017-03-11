package com.yangha.Bookmark.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.yangha.Bookmark.Application.BookmarkResource;
import com.yangha.Bookmark.Dto.DtoBookmark;
import com.yangha.Bookmark.R;

import java.util.ArrayList;


public class DetailActivity extends BaseActivity {
    private EditText edt_title, edt_content;
    private RatingBar rating;
    private Spinner spinner_category;
    private ImageView imageView;
    private FloatingActionButton share_btn, edit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        DtoBookmark mDto = BookmarkResource.getInstance().getDBHelperManager().selectBookMarkData(intent.getIntExtra("selectedID",0));

        edt_title = (EditText)findViewById(R.id.detail_title);
        edt_title.setText(mDto.getTitle());
        edt_title.setFocusable(false);
        edt_title.setClickable(false);

        edt_content = (EditText)findViewById(R.id.detail_content);
        edt_content.setText(mDto.getContent());
        edt_content.setFocusable(false);
        edt_content.setClickable(false);

        rating = (RatingBar)findViewById(R.id.detail_ratingbar);
        rating.setRating(mDto.getRating());
        rating.setFocusable(false);
        rating.setClickable(false);

        spinner_category = (Spinner) findViewById(R.id.detail_spinner);
        spinner_category.setAdapter(new CategoryAdapter(BookmarkResource.getInstance().getDBHelperManager().selectCategory()));
        spinner_category.setSelection(mDto.getCategory());
        spinner_category.setFocusable(false);
        spinner_category.setClickable(false);

        share_btn = (FloatingActionButton)findViewById(R.id.detail_share_btn);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        edit_btn = (FloatingActionButton)findViewById(R.id.detail_edit_btn);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_content.setText("");
                edt_content.setFocusable(true);
                edt_content.setClickable(true);

                rating.setRating(2.5f);
                rating.setFocusable(false);
                rating.setClickable(false);

                spinner_category.setAdapter(new CategoryAdapter(BookmarkResource.getInstance().getDBHelperManager().selectCategory()));
                spinner_category.setSelection(0);
                spinner_category.setFocusable(false);
                spinner_category.setClickable(false);

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
}