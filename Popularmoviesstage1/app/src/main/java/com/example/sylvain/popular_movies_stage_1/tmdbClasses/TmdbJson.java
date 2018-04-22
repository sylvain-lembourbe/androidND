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

    public static ArrayList<TmdbMovie> createMoviesList(Context context, JSONObject jsonObject){

        ArrayList<TmdbMovie> mList = new ArrayList<>();

        Resources res = context.getResources();
        try {
            if (jsonObject.has(res.getString(R.string.results))) {

                //create movie objects
                JSONArray jsonMovies = jsonObject.getJSONArray(res.getString(R.string.results));

                for(int i=0;i<jsonMovies.length();i++) {
                    TmdbMovie objMovie = new TmdbMovie();

                    JSONObject objJsonMovie = (JSONObject) jsonMovies.get(i);

                    if(objJsonMovie.has("id")){
                        objMovie.setId(objJsonMovie.getInt("id"));
                    }
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


    public static List<TmdbTrailer> createTrailerList(JSONObject jsonObject){
        List<TmdbTrailer> mList = new ArrayList<>();

        try {
            if(jsonObject.has("results")) {
                JSONArray jsonTrailers = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonTrailers.length(); i++) {
                    TmdbTrailer objTrailer = new TmdbTrailer();

                    JSONObject objJsonTrailer = (JSONObject) jsonTrailers.get(i);

                    if (objJsonTrailer.has("id")) {
                        objTrailer.setId(objJsonTrailer.getString("id"));
                    }
                    if (objJsonTrailer.has("iso_639_1")) {
                        objTrailer.setIso639(objJsonTrailer.getString("iso_639_1"));
                    }
                    if (objJsonTrailer.has("iso_3166_1")) {
                        objTrailer.setIso3166(objJsonTrailer.getString("iso_3166_1"));
                    }
                    if (objJsonTrailer.has("key")) {
                        objTrailer.setKey(objJsonTrailer.getString("key"));
                    }
                    if (objJsonTrailer.has("name")) {
                        objTrailer.setName(objJsonTrailer.getString("name"));
                    }
                    if (objJsonTrailer.has("site")) {
                        objTrailer.setSite(objJsonTrailer.getString("site"));
                    }
                    if (objJsonTrailer.has("size")) {
                        objTrailer.setSize(objJsonTrailer.getString("size"));
                    }
                    if (objJsonTrailer.has("type")) {
                        objTrailer.setType(objJsonTrailer.getString("type"));
                    }
                    mList.add(objTrailer);
                }
            }
        }
        catch(JSONException ex){
            Log.e(TmdbJson.class.getSimpleName(),ex.getMessage());
        }
        return mList;

    }

    public static List<TmdbReviews> createReviewsList(JSONObject jsonObject){
        List<TmdbReviews> mList = new ArrayList<>();

        try{
            if(jsonObject.has("results")){
                JSONArray jsonReviews = jsonObject.getJSONArray("results");

                for(int i=0;i<jsonReviews.length();i++){
                    TmdbReviews objReview = new TmdbReviews();
                    JSONObject objJsonReview = (JSONObject) jsonReviews.get(i);

                    if(objJsonReview.has("id")){
                        objReview.setId(objJsonReview.getString("id"));
                    }
                    if(objJsonReview.has("author")){
                        objReview.setAuthor(objJsonReview.getString("author"));
                    }
                    if(objJsonReview.has("content")){
                        objReview.setContent(objJsonReview.getString("content"));
                    }
                    if(objJsonReview.has("url")){
                        objReview.setUrl(objJsonReview.getString("url"));
                    }

                    mList.add(objReview);
                }
            }
        }catch(JSONException ex){
            Log.e(TmdbJson.class.getSimpleName(),ex.getMessage());
        }
        return mList;
    }
}
