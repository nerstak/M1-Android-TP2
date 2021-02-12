package com.example.flickrapp;

import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class AsyncFlickrLocation extends AsyncFlickr
{
    private final WeakReference<ImageView> imageViewWeakReference;

    public AsyncFlickrLocation(ImageView imageView, Boolean cache, Double lat, Double lon, String apiKey) {
        this.imageViewWeakReference = new WeakReference<>(imageView);
        this.cache = cache;
        urlBasis = "https://api.flickr.com/services/rest/?method=flickr.photos.search&license=4";
        urlParameters = "&api_key=" + apiKey +
                "&has_geo=1" +
                "&lat=" + lat +
                "&lon=" + lon +
                "&per_page=1";
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        Log.i("Karsto", String.valueOf(jsonObject));
        AsyncBitmapDownloader asyncBitmapDownloader = new AsyncBitmapDownloader(imageViewWeakReference);
        if(jsonObject != null) {
            try {
                // Getting photo object
                JSONObject picture = jsonObject.getJSONObject("photos").getJSONArray("photo").getJSONObject(0);

                // Setting parameter used to compose the URL
                String serverID = picture.getString("server");
                String id = picture.getString("id");
                String secret = picture.getString("secret");
                String sizeSuffix = "b";

                // Creating the URL
                String url = "https://live.staticflickr.com/" + serverID +
                            "/" + id +
                            "_" + secret +
                            "_" + sizeSuffix + ".jpg";

                // Downloading the image
                asyncBitmapDownloader.execute(url);
                Log.i("Karsto", url);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
