package vn.poly.mxmusic.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import vn.poly.mxmusic.database.Database;
import vn.poly.mxmusic.model.SongModel;

public class FavouriteDao {
    SQLiteDatabase db;
    private static final String TABLE_NAME = "Favourite";
    public static final String CREATE_FAVOURITE_TABLE = "CREATE TABLE [" + TABLE_NAME + "] (\n" +
            "[id] TEXT  NULL PRIMARY KEY,\n" +
            "[title] TEXT  NULL,\n" +
            "[albumID] TEXT  NULL,\n" +
            "[singer] TEXT  NULL,\n" +
            "[duration] TEXT  NULL,\n" +
            "[songUri] TEXT  NULL,\n" +
            ")";
    public static final String DROP_FAVOURITE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public FavouriteDao(Context context) {
        Database dbHelper = new Database(context);
        db = dbHelper.getWritableDatabase();
    }

    public void insertFavourite(SongModel songModel) {
        ContentValues values = new ContentValues();
        values.put("id", songModel.getId());
        values.put("title", songModel.getTitle());
        values.put("albumID", songModel.getAlbumID());
        values.put("singer", songModel.getSinger());
        values.put("duration", songModel.getDuration());
        values.put("songUri", songModel.getSongUri());
        db.insert(TABLE_NAME, null, values);
    }
    public void deleteFavourite(String id) {
        db.delete(TABLE_NAME, "id = ?", new String[]{id});
    }
}
