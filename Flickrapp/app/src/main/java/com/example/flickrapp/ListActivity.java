package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        MyAdapter myAdapter = new MyAdapter(this);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(myAdapter);

        AsyncFlickrJSONDataForList asyncJSON = new AsyncFlickrJSONDataForList(myAdapter);
        asyncJSON.execute();
    }

    public class MyAdapter extends BaseAdapter {
        Context context;
        Vector<String> vector = new Vector<>();

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return vector.size();
        }

        @Override
        public Object getItem(int position) {
            return vector.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false);
            }

            String data = (String) getItem(position);
            TextView urlView = convertView.findViewById(R.id.text_view_url);
            urlView.setText(data);

            return convertView;
        }

        /**
         * Add url to list
         * @param url Url to add
         */
        public void add(String url) {
            vector.add(url);
        }
    }
}