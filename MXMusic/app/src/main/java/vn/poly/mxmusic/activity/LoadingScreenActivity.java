package vn.poly.mxmusic.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import vn.poly.mxmusic.R;

public class LoadingScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide toolbar
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).hide();
        setContentView(R.layout.activity_loading_screen);
        //Start screen
        Intent intent = new Intent(LoadingScreenActivity.this, MainActivity.class);
        new Thread(() -> {
            try {
                Thread.sleep(1111);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(intent);
            finish();
        }).start();
    }
}