package com.example.mxmusic.loader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.mxmusic.model.AlbumModel;
import com.example.mxmusic.model.SingerModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumLoader {
    public List<AlbumModel> getAllAlbum(Context context) {
        List<AlbumModel> list = new ArrayList<>();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String countSong = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
                AlbumModel albumModel = new AlbumModel(id, name, countSong);
                list.add(albumModel);
            } while (cursor.moveToNext());
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }
}
