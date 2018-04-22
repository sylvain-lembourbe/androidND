package com.example.sylvain.popular_movies_stage_1.TmdbContentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.sylvain.popular_movies_stage_1.TmdbLocalDatabase.TmdbContract;
import com.example.sylvain.popular_movies_stage_1.TmdbLocalDatabase.TmdbDbHelper;

/**
 * Created by sylvain on 15/04/2018.
 */

public class TmdbContentProvider extends ContentProvider{

    public static final int FAVORITES = 100;
    public static final int FAVORITE_WITH_ID = 101;

    private TmdbDbHelper tmdbDbHelper;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        tmdbDbHelper = new TmdbDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = tmdbDbHelper.getReadableDatabase();

        Cursor cursor = null;

        int match = sUriMatcher.match(uri);
        switch (match){
            case FAVORITES:
                cursor = db.query(TmdbContract.TmdbFavorite.TABLE_NAME,null,null,null, null,null,null);
                break;
            case FAVORITE_WITH_ID:
                String sel = TmdbContract.TmdbFavorite.ID_MOVIE + "=?";
                String[] argSel = new String[] {uri.getLastPathSegment()};
                cursor = db.query(TmdbContract.TmdbFavorite.TABLE_NAME,null,sel,argSel,null,null,null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = tmdbDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnedUri;
        switch (match){
            case FAVORITES:
                long id = db.insert(TmdbContract.TmdbFavorite.TABLE_NAME, null, values);
                if(id>0){
                    returnedUri = ContentUris.withAppendedId(TmdbContract.TmdbFavorite.CONTENT_URI, id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return returnedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = tmdbDbHelper.getWritableDatabase();
        String id = uri.getLastPathSegment();

        int match = sUriMatcher.match(uri);

        int row;
        switch (match){
            case FAVORITE_WITH_ID:
                String sel = TmdbContract.TmdbFavorite.ID_MOVIE +"=?";
                String[] selargs = new String[]{uri.getLastPathSegment()};
                row = db.delete(TmdbContract.TmdbFavorite.TABLE_NAME, sel,selargs);

                if (row >0 ) {

                }else{
                    throw new android.database.SQLException("Failed to delete row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return row;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(TmdbContract.AUTHORITY, TmdbContract.path_favMovies, FAVORITES);
        uriMatcher.addURI(TmdbContract.AUTHORITY, TmdbContract.path_favMovies + "/#", FAVORITE_WITH_ID);

        return uriMatcher;
    }



}
