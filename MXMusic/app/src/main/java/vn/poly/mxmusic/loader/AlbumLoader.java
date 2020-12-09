package vn.poly.mxmusic.loader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mxmusic.model.AlbumModel;

public class AlbumLoader {
    public List<AlbumModel> getAllAlbum(Context context) {
        List<AlbumModel> list = new ArrayList<>();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projection = {
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
            cursor.close();
        }
        return list;
    }
}
