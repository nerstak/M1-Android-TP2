package com.example.flickrapp;

import android.view.View;
import android.widget.ImageView;

public class GetImageOnClickListener implements View.OnClickListener {
    private ImageView imageView;
    private String search;

    public GetImageOnClickListener(ImageView imageView, String search) {
        this.imageView = imageView;
        this.search = search;
    }

    @Override
    public void onClick(View v) {
        AsyncFlickrJSONData task = new AsyncFlickrJSONData(imageView, search);
        task.execute();
    }
}
