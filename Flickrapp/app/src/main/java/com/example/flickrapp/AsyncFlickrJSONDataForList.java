package com.example.flickrapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Download JSON for list of images
 */
public class AsyncFlickrJSONDataForList extends AsyncFlickr {
    private final ListActivity.MyAdapter myAdapter;

    public AsyncFlickrJSONDataForList(ListActivity.MyAdapter myAdapter, String search, Boolean cache) {
        this.myAdapter = myAdapter;
        this.cache = cache;
        urlParameters = search;
        urlBasis = "https://www.flickr.com/services/feeds/photos_public.gne?tags=";
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        Log.i("Karsto", String.valueOf(jsonObject));
        if(jsonObject != null) {
            try {
                for(int i = 0; i < jsonObject.names().length(); i++) {
                    String url = jsonObject.getJSONArray("items").getJSONObject(i).getJSONObject("media").getString("m");
                    myAdapter.add(url);
                    Log.i("Karsto", "Adding to adapter url: " + url);
                }
                myAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
