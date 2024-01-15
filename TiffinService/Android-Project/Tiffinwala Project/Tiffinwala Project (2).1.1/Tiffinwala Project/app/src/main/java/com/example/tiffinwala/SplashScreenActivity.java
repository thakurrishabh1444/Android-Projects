package com.example.tiffinwala;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long GIF_DURATION = 1500; // Adjust this value as needed (in milliseconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("file:///android_asset/gif_display.html");

        // Wait for the GIF to finish playing and then start the MainActivity
        new CountDownTimer(GIF_DURATION, GIF_DURATION) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Do nothing while waiting for the GIF to complete
            }

            @Override
            public void onFinish() {
                // Start the MainActivity
                Intent mainIntent = new Intent(SplashScreenActivity.this, Login.class);
                startActivity(mainIntent);
                finish(); // Optional: Close the SplashScreenActivity after navigating to MainActivity
            }
        }.start();
    }
}
