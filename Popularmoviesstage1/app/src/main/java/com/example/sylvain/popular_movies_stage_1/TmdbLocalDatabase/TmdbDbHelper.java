package com.example.sylvain.popular_movies_stage_1.TmdbLocalDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sylvain on 15/04/2018.
 */

public class TmdbDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoriteMovies.db";
    private static final int DATABASE_VERSION = 2;

    public TmdbDbHelper(Context context){
        super (context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATION_STRING = "CREATE TABLE " +
                TmdbContract.TmdbFavorite.TABLE_NAME + "(" +
                TmdbContract.TmdbFavorite._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TmdbContract.TmdbFavorite.ID_MOVIE + " TEXT NOT NULL, " +
                TmdbContract.TmdbFavorite.TITLE_MOVIE + " TEXT NOT NULL, " +
                TmdbContract.TmdbFavorite.ORIGINAL_TITLE + " TEXT NOT NULL, " +
                TmdbContract.TmdbFavorite.POSTER_PATH + " TEXT NOT NULL, " +
                TmdbContract.TmdbFavorite.RELEASE_DATE + " TEXT NOT NULL, " +
                TmdbContract.TmdbFavorite.OVERVIEW + " TEXT NOT NULL, " +
                TmdbContract.TmdbFavorite.VOTE_AVERAGE + " TEXT NOT NULL" +
                ");";
        db.execSQL(SQL_CREATION_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TmdbContract.TmdbFavorite.TABLE_NAME);
            onCreate(db);
        }
    }
}
