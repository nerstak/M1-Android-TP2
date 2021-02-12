package com.example.flickrapp;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Download ONE image on click
 */
public class GetImageOnClickListener implements View.OnClickListener {
    private ImageView imageView;
    private EditText search;
    private CheckBox caching;

    public GetImageOnClickListener(ImageView imageView, EditText search, CheckBox caching) {
        this.imageView = imageView;
        this.search = search;
        this.caching = caching;
    }

    @Override
    public void onClick(View v) {
        AsyncFlickrJSONData task = new AsyncFlickrJSONData(imageView, search.getText().toString(), caching.isChecked());
        task.execute();
    }
}
