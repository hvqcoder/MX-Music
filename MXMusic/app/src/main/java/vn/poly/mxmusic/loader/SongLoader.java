package vn.poly.mxmusic.loader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mxmusic.model.SongModel;

public class SongLoader {
    public List<SongModel> getAllSong(Context context, String selection) {
        List<SongModel> list = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.COMPOSER,
                MediaStore.Audio.Media.ALBUM_ARTIST,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST_ID
        };

        Cursor cursor = context.getContentResolver().query(uri, projection, selection, null,
                MediaStore.Audio.Media.TITLE + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String songUri = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String size = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                String year = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
                String composer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
                String albumArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ARTIST));
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String albumID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ARTIST));
                String singerID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                SongModel songModel = new SongModel(id, title, singer, album, duration, songUri, size, year, composer, albumArtist, displayName, albumID, singerID);
                list.add(songModel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
}
