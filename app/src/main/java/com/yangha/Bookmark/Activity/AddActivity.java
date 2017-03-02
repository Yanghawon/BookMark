package com.yangha.Bookmark.Activity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
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

import com.yangha.Bookmark.Application.BookmarkResource;
import com.yangha.Bookmark.R;
import com.yangha.Bookmark.util.GpsInfo;

import java.util.ArrayList;

public class AddActivity extends BaseActivity implements LocationListener {

    private Button photoAddBtn, listAddBtn;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        photoAddBtn = (Button) findViewById(R.id.add_photo_btn);
        listAddBtn = (Button) findViewById(R.id.add_list_btn);
        spinner = (Spinner) findViewById(R.id.add_spinner);

        spinner.setAdapter(new CategoryAdapter(BookmarkResource.getInstance().getDBHelperManager().selectCategory()));

        listAddBtn.setOnClickListener(new View.OnClickListener() {
            GpsInfo gps = new GpsInfo(AddActivity.this);
            EditText title = (EditText) findViewById(R.id.add_title);
            EditText content = (EditText) findViewById(R.id.add_content);
            ImageView image = (ImageView) findViewById(R.id.add_photo);
            Spinner category = (Spinner) findViewById(R.id.add_spinner);
            RatingBar rating = (RatingBar) findViewById(R.id.add_ratingbar);

            @Override
            public void onClick(View v) {
                BookmarkResource.getInstance().getDBHelperManager().insertData(
                        gps.getLongitude(), gps.getLatitude(), title.getText().toString(), "image", category.getSelectedItemPosition(), content.getText().toString(), rating.getRating(), "0");
                Toast.makeText(getApplicationContext(), "데이터가 입력되었습니다.", Toast.LENGTH_SHORT).show();
                relayout(RELAYOUT_MAINACTIVITY);
            }
        });
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