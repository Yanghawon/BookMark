package com.yangha.Bookmark.Activity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yangha.Bookmark.Application.BookmarkResource;
import com.yangha.Bookmark.Dto.DtoBookmark;
import com.yangha.Bookmark.R;
import com.yangha.Bookmark.util.GpsInfo;

import java.util.ArrayList;

public class ListActivity extends BaseActivity implements LocationListener{
    private String[] search_spinner_item = {"가나다 순", "거리 순", "최신 순", "별점 순", "조회 순"};
    private Spinner sort_spinner;
    GpsInfo gps = new GpsInfo(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Button search_btn = (Button) findViewById(R.id.list_search_btn);
        EditText search_edt = (EditText) findViewById(R.id.list_search_edt);
        ListView view = (ListView) findViewById(R.id.list_view);
        sort_spinner = (Spinner) findViewById(R.id.list_search_sort_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, search_spinner_item);
        sort_spinner.setAdapter(adapter);

        sort_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (sort_spinner.getId()) {
                    case 0:

                }
            }
        });

        view.setAdapter(new ViewAdapter(BookmarkResource.getInstance().getDBHelperManager().selectBookMarkDataAll(sort_spinner.getId(),gps.getLongitude(),gps.getLatitude())));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        relayout(RELAYOUT_MAINACTIVITY);
    }

    @Override
    public void onLocationChanged(Location location) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    public class ViewAdapter extends BaseAdapter {
        private ArrayList<DtoBookmark> list;

        public ViewAdapter(ArrayList<DtoBookmark> list) {
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
                convertView = inflater.inflate(R.layout.list_item, parent, false);
            }
            TextView tv_title = (TextView) convertView.findViewById(R.id.list_item_title);
            TextView tv_sub = (TextView) convertView.findViewById(R.id.list_item_sub);
            //"가나다 순", "거리 순", "최신 순", "별점 순", "조회 순"
            switch (sort_spinner.getId()) {
                case 0:
                    tv_title.setText(list.get(position).getTitle());
                    tv_sub.setText(list.get(position).getDate());
            }
            return convertView;
        }
    }
}