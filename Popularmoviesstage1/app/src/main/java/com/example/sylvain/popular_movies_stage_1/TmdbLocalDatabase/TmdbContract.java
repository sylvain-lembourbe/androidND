package com.example.sylvain.popular_movies_stage_1.TmdbLocalDatabase;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sylvain on 15/04/2018.
 */

public class TmdbContract {

    //uris for content provider
    public static final String AUTHORITY = "com.example.sylvain.popular_movies_stage_1";
    public static final Uri BASE_CONTENT = Uri.parse("content://" + AUTHORITY);
    public static final String path_favMovies = "favorites";

    public static final class TmdbFavorite implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT.buildUpon().appendPath(path_favMovies).build();

        public static final String TABLE_NAME = "favorites";
        public static final String ID_MOVIE = "id_movie";
        public static final String TITLE_MOVIE = "title_movie";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String POSTER_PATH = "poster_path";
        public static final String RELEASE_DATE = "release_date";
        public static final String OVERVIEW = "overview";
        public static final String VOTE_AVERAGE = "vote_average";


    }

}
