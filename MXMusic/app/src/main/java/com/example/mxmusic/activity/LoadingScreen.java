package com.example.mxmusic.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mxmusic.MainActivity;
import com.example.mxmusic.R;

public class LoadingScreen extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        //Ân toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_loading_screen);
        //start screen
        intent = new Intent(LoadingScreen.this, MainActivity.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2222);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        }).start();
    }
}