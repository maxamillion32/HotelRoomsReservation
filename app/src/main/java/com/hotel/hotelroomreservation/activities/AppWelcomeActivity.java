package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hotel.hotelroomreservation.R;
import com.wang.avi.AVLoadingIndicatorView;

public class AppWelcomeActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_welcome);

        AVLoadingIndicatorView avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        startAnim(avi);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(AppWelcomeActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void startAnim(AVLoadingIndicatorView avi) {
        avi.show();
    }
}
