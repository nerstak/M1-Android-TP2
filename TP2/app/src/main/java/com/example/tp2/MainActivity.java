package com.example.tp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button authenticate
        Button buttonQuit = (Button) findViewById(R.id.button_authenticate);
        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = ((TextView) findViewById(R.id.edit_login_id)).getText().toString();
                String password = ((TextView) findViewById(R.id.edit_password_id)).getText().toString();

                urlConnection(login, password);
            }
        });
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

    /**
     * Instantiate thread dealing with authentication
     * @param login Login given by user
     * @param password Password given by user
     */
    private void urlConnection(String login, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("https://httpbin.org/basic-auth/bob/sympa");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    String payload = login + ":" + password;
                    String basicAuth = "Basic " + Base64.encodeToString(payload.getBytes(), Base64.NO_WRAP);
                    urlConnection.setRequestProperty("Authorization", basicAuth);
                    try {
                        handleResult(new BufferedInputStream(urlConnection.getInputStream()));
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Actions on results
     * @param in Input stream
     */
    private void handleResult(InputStream in) {
        String s = readStream(in);

        // Json and display
        try {
            JSONObject jsonObject = new JSONObject(s);
            runOnUiThread(new RunnableResult(jsonObject.getString("authenticated")));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Logging
        Log.i("Karsto", s);
    }

    /**
     * Set UI result
     */
    private class RunnableResult implements Runnable {
        private final String s;

        public RunnableResult(String s) {
            super();
            this.s = s;
        }

        @Override
        public void run() {
            TextView resultTextView = (TextView) findViewById(R.id.result_id);
            resultTextView.setText(s);
        }
    }
}