package com.example.sylvain.popular_movies_stage_1.tmdbClasses;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class TmdbUrlBuilder {


    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/";

    private static final String IMAGE_SIZE = "w185";

    public static final String POPULAR_SEGMENT = "popular";
    public static final String TOP_RATED_SEGMENT = "top_rated";

    private static final String API_KEY = "api_key";

    //API KEY HERE
    private static final String API_NUMBER_STRING = "";

    public static URL createURL(String segment){
        Uri requestUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(segment)
                .appendQueryParameter(API_KEY,API_NUMBER_STRING)
                .build();

        URL url = null;
        try{
            url = new URL(requestUri.toString());
        }
        catch (MalformedURLException ex){
            Log.e(TmdbUrlBuilder.class.getSimpleName(),ex.getMessage());
        }
        return url;
    }

    public static URL createImageUrl(String imgSegment){
        Uri requestUri = Uri.parse(BASE_URL_IMAGE).buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendEncodedPath(imgSegment)
                .build();

        URL url = null;
        try{
            url = new URL(requestUri.toString());
        }catch (MalformedURLException ex){
            Log.e(TmdbUrlBuilder.class.getSimpleName(),ex.getMessage());
        }
        return url;
    }

}
