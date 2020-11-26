package com.example.mxmusic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mxmusic.dao.FavouriteSongDao;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context) {
        super(context, "mxmusic.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FavouriteSongDao.CREATE_SONG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(FavouriteSongDao.DROP_SONG_TABLE);
    }
}
