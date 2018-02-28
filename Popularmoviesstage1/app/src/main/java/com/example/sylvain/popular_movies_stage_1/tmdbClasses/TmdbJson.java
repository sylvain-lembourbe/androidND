package com.example.sylvain.popular_movies_stage_1.tmdbClasses;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.example.sylvain.popular_movies_stage_1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("DefaultFileTemplate")
public class TmdbJson {

    public static List<TmdbMovie> createMoviesList(Context context, JSONObject jsonObject){

        List<TmdbMovie> mList = new ArrayList<>();

        Resources res = context.getResources();
        try {
            if (jsonObject.has(res.getString(R.string.results))) {

                //create movie objects
                JSONArray jsonMovies = jsonObject.getJSONArray(res.getString(R.string.results));

                for(int i=0;i<jsonMovies.length();i++) {
                    TmdbMovie objMovie = new TmdbMovie();

                    JSONObject objJsonMovie = (JSONObject) jsonMovies.get(i);


                    if(objJsonMovie.has(res.getString(R.string.title))) {
                        objMovie.setTitle(objJsonMovie.getString(res.getString(R.string.title)));
                    }
                    if(objJsonMovie.has(res.getString(R.string.original_title))) {
                        objMovie.setOriginalTitle(objJsonMovie.getString(res.getString(R.string.original_title)));
                    }
                    if(objJsonMovie.has(res.getString(R.string.overview))) {
                        objMovie.setOverview(objJsonMovie.getString(res.getString(R.string.overview)));
                    }
                    if(objJsonMovie.has(res.getString(R.string.poster_path))) {
                        objMovie.setPosterPath(objJsonMovie.getString(res.getString(R.string.poster_path)));
                    }

                    if(objJsonMovie.has(res.getString(R.string.release_date))) {
                        objMovie.setReleaseDate(objJsonMovie.getString(res.getString(R.string.release_date)));
                    }
                    if(objJsonMovie.has(res.getString(R.string.vote_average))) {
                        objMovie.setVoteAverage(objJsonMovie.getInt(res.getString(R.string.vote_average)));
                    }

                    mList.add(objMovie);
                }
            }
        }catch(JSONException ex){
            Log.e(TmdbJson.class.getName(),ex.getMessage());
        }
        return mList;
    }
}
