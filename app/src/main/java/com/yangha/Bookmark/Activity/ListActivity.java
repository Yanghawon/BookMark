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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.yangha.Bookmark.Application.BookmarkResource;
import com.yangha.Bookmark.Dto.DtoBookmark;
import com.yangha.Bookmark.R;
import com.yangha.Bookmark.util.GpsInfo;

import java.util.ArrayList;

public class ListActivity extends BaseActivity implements LocationListener {
    private String[] search_spinner_item = {"가나다 순", "거리 순", "최신 순", "별점 순", "조회 순"};
    private Spinner sort_spinner;
    private ListView listview;
    GpsInfo gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        gps= new GpsInfo(this);
        FloatingActionButton search_btn = (FloatingActionButton) findViewById(R.id.list_search_btn);
        final EditText search_edt = (EditText) findViewById(R.id.list_search_edt);
        listview = (ListView) findViewById(R.id.list_view);
        sort_spinner = (Spinner) findViewById(R.id.list_search_sort_spinner);
        listview.setAdapter(new ViewAdapter(BookmarkResource.getInstance().getDBHelperManager().selectBookMarkDataAll(sort_spinner.getId(), gps.getLongitude(), gps.getLatitude())));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, search_spinner_item);
        sort_spinner.setAdapter(adapter);
        sort_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((ViewAdapter) listview.getAdapter()).setList(
                        BookmarkResource.getInstance().getDBHelperManager().
                                selectBookMarkDataAll(i, gps.getLongitude(), gps.getLatitude()));
                ((ViewAdapter) listview.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        search_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ArrayList<DtoBookmark> list = new ArrayList<DtoBookmark>();
//                list = BookmarkResource.getInstance().getDBHelperManager().selectBookMarkDataForSearch(search_edt.getText().toString());
//            }
//        });


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

    public class ViewAdapter extends BaseAdapter {
        private ArrayList<DtoBookmark> list;

        public ViewAdapter(ArrayList<DtoBookmark> list) {
            this.list = list;
        }

        public void setList(ArrayList<DtoBookmark> list) {
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item, parent, false);
            }
            final TextView tv_title = (TextView) convertView.findViewById(R.id.list_item_title);
            tv_title.setText(list.get(position).getTitle());
            TextView tv_sub = (TextView) convertView.findViewById(R.id.list_item_sub);
            switch (sort_spinner.getSelectedItemPosition()) {
                case 0:
                    tv_sub.setText(list.get(position).getContent());
                    break;
                case 1:
                    tv_sub.setText(list.get(position).getDistance() + "m");
                    break;
                case 2:
                    if ((list.get(position).getDateGap() / 60) == 0) {
                        tv_sub.setText(list.get(position).getDateGap() + "분 전");
                    } else if ((list.get(position).getDateGap() / 60) < 25) {
                        tv_sub.setText(list.get(position).getDateGap() / 60 + "시간 전");
                    } else {
                        tv_sub.setText(list.get(position).getDateGap() / (60*12*24) + "일 전");
                    }
                    break;
                case 3:
                    tv_sub.setText(list.get(position).getRating() + "점");
                    break;
                case 4:
                    tv_sub.setText(list.get(position).getCount() + "up");
                    break;
            }
            tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    relayout(RELAYOUT_DETAILACTIVITY, list.get(position).getIndex());
                }
            });
            tv_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    relayout(RELAYOUT_DETAILACTIVITY, list.get(position).getIndex());
                }
            });
            return convertView;
        }
    }
}