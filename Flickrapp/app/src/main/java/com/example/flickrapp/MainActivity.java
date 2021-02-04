package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonGetImage = (Button) findViewById(R.id.button_get_image);
        buttonGetImage.setOnClickListener(
                // Image listener
                new GetImageOnClickListener(
                        (ImageView) findViewById(R.id.image_view),
                        ((EditText) findViewById(R.id.search_bar)).getText().toString()
                ));

        Button buttonSendToList = (Button) findViewById(R.id.button_sendToList);
        buttonSendToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listActivity = new Intent(getApplicationContext(), ListActivity.class);
                // Sending search value
                listActivity.putExtra("search", ((EditText) findViewById(R.id.search_bar)).getText().toString());
                startActivity(listActivity);
            }
        });
    }
}