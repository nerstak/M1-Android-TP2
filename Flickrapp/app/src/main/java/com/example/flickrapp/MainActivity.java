package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_caching);
        EditText searchBar = (EditText) findViewById(R.id.search_bar);
        Button buttonGetImage = (Button) findViewById(R.id.button_get_image);
        buttonGetImage.setOnClickListener(
                // Image listener
                new GetImageOnClickListener(
                        (ImageView) findViewById(R.id.image_view),
                        searchBar,
                        checkBox
                ));


        Button buttonSendToList = (Button) findViewById(R.id.button_sendToList);
        buttonSendToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listActivity = new Intent(getApplicationContext(), ListActivity.class);
                // Sending search value
                listActivity.putExtra("search", ((EditText) findViewById(R.id.search_bar)).getText().toString());
                listActivity.putExtra("caching", checkBox.isChecked());
                startActivity(listActivity);
            }
        });



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Location localisation = manager.getLastKnownLocation("gps");
            Log.i("Karsto", "Latitude " + localisation.getLatitude());
            Log.i("Karsto", "Longitude " + localisation.getLongitude());
        } else {
            Log.i("Karsto", "Permission error");
        }
    }
}