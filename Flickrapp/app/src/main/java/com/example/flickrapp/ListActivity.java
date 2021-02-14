package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.Vector;

/**
 * Activity containing a list of images
 */
public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Linking listView & adapter
        MyAdapter myAdapter = new MyAdapter(this);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(myAdapter);

        // Intent
        String search = "";
        Boolean caching = false;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            search = new String(extras.getString("search"));
            caching = extras.getBoolean("caching");
        }

        // Calling API
        AsyncFlickrJSONDataForList asyncJSON = new AsyncFlickrJSONDataForList(myAdapter, search, caching);
        asyncJSON.execute();
    }

    /**
     * Adapter for list of items
     */
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
                //convertView = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false);
                convertView = LayoutInflater.from(context).inflate(R.layout.bitmaplayout, parent, false);
            }
            // Get a RequestQueue
            RequestQueue queue = MySingleton.getInstance(parent.getContext()).getRequestQueue();

            // Get data and layout
            String data = (String) getItem(position);
            ImageView imageView = convertView.findViewById(R.id.bitmap_image_view);

            // Generating request
            ImageRequest request = new ImageRequest(
                    data,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            imageView.setImageBitmap(response);

                        }}
                    , 0,
                    0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ListActivity.this,"Some Thing Goes Wrong", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();

                    }});

            // Adding request to queue
            queue.add(request);


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