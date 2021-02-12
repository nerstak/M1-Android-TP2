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
public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject> {
    private final ListActivity.MyAdapter myAdapter;
    private final String search;
    private final Boolean cache;


    public AsyncFlickrJSONDataForList(ListActivity.MyAdapter myAdapter, String search, Boolean cache) {
        this.myAdapter = myAdapter;
        this.search = search;
        this.cache = cache;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL("https://www.flickr.com/services/feeds/photos_public.gne?tags=" + search + "&format=json&nojsoncallback=1");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setUseCaches(cache);
            try {
                return handleResult(new BufferedInputStream(urlConnection.getInputStream()));
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Actions on result
     * @param bufferedInputStream Input stream
     * @return JSON Object retrieved
     */
    private JSONObject handleResult(BufferedInputStream bufferedInputStream) {
        String s = readStream(bufferedInputStream);

        // Json and display
        try {
            return new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Read stream and convert it to string
     * @param is Stream
     * @return String
     */
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
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
