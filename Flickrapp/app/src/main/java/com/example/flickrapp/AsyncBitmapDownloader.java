package com.example.flickrapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewWeakReference;

    public AsyncBitmapDownloader(WeakReference<ImageView> imageViewWeakReference) {
        this.imageViewWeakReference = imageViewWeakReference;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            Bitmap bm = null;
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                bm = BitmapFactory.decodeStream(in);
            } finally {
                urlConnection.disconnect();
            }

            return bm;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(imageViewWeakReference != null) {
            ImageView imageView = imageViewWeakReference.get();

            if(imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
