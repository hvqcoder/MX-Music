package vn.poly.mxmusic.activity;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import vn.poly.mxmusic.R;
import vn.poly.mxmusic.adapter.SongAdapter;
import vn.poly.mxmusic.loader.SongLoader;

public class AlbumDetailActivity extends AppCompatActivity {
    ImageView ivAlbumDetail;
    TextView tvAlbumDetailName, tvAlbumDetailNumber;
    ListView lvAlbumDetailSong;
    SongAdapter songAdapter;
    SongLoader songLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide toolbar
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).hide();
        setContentView(R.layout.activity_album_detail);

        ivAlbumDetail = findViewById(R.id.ivAlbumDetail);
        tvAlbumDetailName = findViewById(R.id.tvAlbumDetailName);
        tvAlbumDetailNumber = findViewById(R.id.tvAlbumDetailNumber);
        lvAlbumDetailSong = findViewById(R.id.lvAlbumDetailSong);

        songLoader = new SongLoader();

        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("album");
        String albumID = bundle.getString("albumID", "");
        String albumName = bundle.getString("albumName", "");
        String albumSongNumber = bundle.getString("albumSongNumber", "");

        tvAlbumDetailName.setText(albumName);
        tvAlbumDetailNumber.setText(albumSongNumber + " bài hát");
        final Uri albumUri = Uri.parse("content://media/external/audio/albumart");
        Uri uri = ContentUris.withAppendedId(albumUri, Long.parseLong(albumID));
        Picasso.get().load(uri)
                .fit()
                .centerCrop()
                .error(R.drawable.logobackgroup)
                .into(ivAlbumDetail);

        songAdapter = new SongAdapter(getApplicationContext(), songLoader.getAllSong(getApplicationContext(),
                MediaStore.Audio.Media.IS_MUSIC + "=1" + " and " + MediaStore.Audio.Media.ALBUM_ID + "=" + Long.parseLong(albumID)));
        lvAlbumDetailSong.setAdapter(songAdapter);
    }
}