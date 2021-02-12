package com.example.flickrapp;

import android.os.AsyncTask;

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
 * Mother class for Flicker API
 */
public abstract class AsyncFlickr  extends AsyncTask<String, Void, JSONObject> {
    protected Boolean cache;
    protected String urlBasis;
    protected String urlParameters = "";

    /**
     * Actions on result
     * @param bufferedInputStream Input stream
     * @return JSON Object retrieved
     */
    protected JSONObject handleResult(BufferedInputStream bufferedInputStream) {
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
    protected String readStream(InputStream is) {
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
    protected JSONObject doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL( urlBasis + urlParameters + "&format=json&nojsoncallback=1");
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
}
