package vn.poly.mxmusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import vn.poly.mxmusic.R;
import vn.poly.mxmusic.adapter.SongAdapter;
import vn.poly.mxmusic.loader.SongLoader;

public class SingerDetailActivity extends AppCompatActivity {
    TextView tvSingerDetailName, tvSingerDetailNumber;
    ListView lvSingerDetailSong;
    SongAdapter songAdapter;
    SongLoader songLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide toolbar
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).hide();
        setContentView(R.layout.activity_singer_detail);
        tvSingerDetailName = findViewById(R.id.tvSingerDetailName);
        tvSingerDetailNumber = findViewById(R.id.tvSingerDetailNumber);
        lvSingerDetailSong = findViewById(R.id.lvSingerDetailSong);
        songLoader = new SongLoader();

        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("singer");
        String singerID = bundle.getString("singerID", "");
        String singerName = bundle.getString("singerName", "");
        String singerSongNumber = bundle.getString("singerSongNumber", "");

        tvSingerDetailName.setText(singerName);
        tvSingerDetailNumber.setText(singerSongNumber + " bài hát");

        songAdapter = new SongAdapter(getApplicationContext(), songLoader.getAllSong(getApplicationContext(),
                MediaStore.Audio.Media.IS_MUSIC + "=1" + " and " + MediaStore.Audio.Media.ARTIST_ID + "=" + Long.parseLong(singerID)));
        lvSingerDetailSong.setAdapter(songAdapter);
    }
}