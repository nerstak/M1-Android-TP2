package com.example.flickrapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Download JSON for one image
 */
public class AsyncFlickrJSONData extends AsyncFlickr {
    private final WeakReference<ImageView> imageViewWeakReference;

    public AsyncFlickrJSONData(ImageView imageView, String search, Boolean cache) {
        this.imageViewWeakReference = new WeakReference<ImageView>(imageView);
        this.cache = cache;
        urlParameters = search;
        urlBasis = "https://www.flickr.com/services/feeds/photos_public.gne?tags=";
    }


    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        Log.i("Karsto", String.valueOf(jsonObject));
        AsyncBitmapDownloader asyncBitmapDownloader = new AsyncBitmapDownloader(imageViewWeakReference);
        if(jsonObject != null) {
            try {
                String url = jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("media").getString("m");
                asyncBitmapDownloader.execute(url);
                Log.i("Karsto", url);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
