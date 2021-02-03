package com.example.flickrapp;

import android.view.View;
import android.widget.ImageView;

public class GetImageOnClickListener implements View.OnClickListener {
    private ImageView imageView;

    public GetImageOnClickListener(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void onClick(View v) {
        AsyncFlickrJSONData task = new AsyncFlickrJSONData(imageView);
        task.execute();
    }
}
