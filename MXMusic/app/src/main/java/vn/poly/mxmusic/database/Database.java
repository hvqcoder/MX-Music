package vn.poly.mxmusic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import vn.poly.mxmusic.dao.FavouriteDao;


public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context) {
        super(context, "mxmusic.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FavouriteDao.CREATE_FAVOURITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(FavouriteDao.DROP_FAVOURITE_TABLE);
    }
}
