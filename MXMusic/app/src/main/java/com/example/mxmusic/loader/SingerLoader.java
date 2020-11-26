package com.example.mxmusic.loader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.mxmusic.model.SingerModel;

import java.util.ArrayList;
import java.util.List;

public class SingerLoader {
    public List<SingerModel> getAllSinger(Context context) {
        List<SingerModel> list = new ArrayList<>();
        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                String countSong = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
                SingerModel singerModel = new SingerModel(id, name, countSong);
                list.add(singerModel);
            } while (cursor.moveToNext());
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

}
