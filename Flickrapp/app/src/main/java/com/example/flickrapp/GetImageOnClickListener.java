package com.example.flickrapp;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Download ONE image on click
 */
public class GetImageOnClickListener implements View.OnClickListener {
    private ImageView imageView;
    private EditText search;

    public GetImageOnClickListener(ImageView imageView, EditText search) {
        this.imageView = imageView;
        this.search = search;
    }

    @Override
    public void onClick(View v) {
        AsyncFlickrJSONData task = new AsyncFlickrJSONData(imageView, search.getText().toString());
        task.execute();
    }
}
