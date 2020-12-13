package vn.poly.mxmusic.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mxmusic.database.Database;
import vn.poly.mxmusic.model.SongModel;

public class FavouriteDao {
    SQLiteDatabase db;
    private static final String TABLE_NAME = "Favourite";
    public static final String CREATE_FAVOURITE_TABLE = "CREATE TABLE ["+ TABLE_NAME +"] (\n" +
            "[id] TEXT  NOT NULL PRIMARY KEY\n" +
            ")";
    public static final String DROP_FAVOURITE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public FavouriteDao(Context context) {
        Database dbHelper = new Database(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<String> getAllFavourite() {
        List<String> favouriteList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String id = cursor.getString(0);

            cursor.moveToNext();
            favouriteList.add(id);
        }
        cursor.close();
        return favouriteList;
    }

    public void insertFavourite(String id) {
        ContentValues values = new ContentValues();
        values.put("id", id);
        db.insert(TABLE_NAME, null, values);
    }

    public void deleteFavourite(String id) {
        db.delete(TABLE_NAME, "id = ?", new String[]{id});
    }
}
