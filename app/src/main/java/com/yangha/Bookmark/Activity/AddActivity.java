package com.yangha.Bookmark.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.yangha.Bookmark.Application.BookmarkResource;
import com.yangha.Bookmark.R;
import com.yangha.Bookmark.util.GpsInfo;

import java.util.ArrayList;

public class AddActivity extends BaseActivity {

    private GpsInfo gps;
    private IntroActivity intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button photoAdd = (Button) findViewById(R.id.photoAdd);
        Button listAdd = (Button) findViewById(R.id.list_add);
        Spinner spinner = (Spinner) findViewById(R.id.add_spinner);

        spinner.setAdapter(new CategoryAdapter(BookmarkResource.getInstance().getDBHelperManager().selectCategory()));

        listAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        relayout(RELAYOUT_MAINACTIVITY);
    }
    private class CategoryAdapter extends BaseAdapter{
        private ArrayList<String> list;
        public CategoryAdapter(ArrayList<String> list){
            this.list = list;
        }
        @Override
        public int getCount() {
            return  list.size();
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
            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.spinner_item,parent, false);
            }
            TextView tv = (TextView)convertView.findViewById(R.id.spinner_item);
            tv.setText(list.get(position));
            return convertView;
        }
    }
}