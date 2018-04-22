package com.example.sylvain.popular_movies_stage_1.tmdbClasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

import java.util.List;


public class TmdbMovie implements Parcelable{
    private int id;

    private String title;
    private String original_title;

    private String poster_path;
    private String backdrop_path;

    private String release_date;

    private String overview;

    private int vote_average;

    private List<TmdbTrailer> trailer_list;
    private List<TmdbReviews> reviews_list;

    private boolean isFavorite;

    public TmdbMovie(){

    }

    private TmdbMovie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        original_title = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        release_date = in.readString();
        overview = in.readString();
        vote_average = in.readInt();
    }

    public static final Creator<TmdbMovie> CREATOR = new Creator<TmdbMovie>() {
        @Override
        public TmdbMovie createFromParcel(Parcel in) {
            return new TmdbMovie(in);
        }

        @Override
        public TmdbMovie[] newArray(int size) {
            return new TmdbMovie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(original_title);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeString(release_date);
        parcel.writeString(overview);
        parcel.writeInt(vote_average);
    }


    public void setId(int _id){
        this.id = _id;
    }

    public void setTitle(String _title){
        this.title = _title;
    }

    public void setOriginalTitle(String _originalTitle){
        this.original_title = _originalTitle;
    }

    public void setPosterPath(String _posterPath){
        this.poster_path = _posterPath;
        Log.i(TmdbMovie.class.getName(),this.poster_path);
    }

    public void setReleaseDate(String _releaseDate){
        this.release_date = _releaseDate;
    }
    public void setOverview(String _overview){
        this.overview = _overview;
    }
    public void setVoteAverage(int _voteAverage){
        this.vote_average = _voteAverage;
    }

    public void setTrailer_list(JSONObject jsonObj){
        this.trailer_list = TmdbJson.createTrailerList(jsonObj);
    }

    public void setReviews_list(JSONObject jsonObj){
        this.reviews_list = TmdbJson.createReviewsList(jsonObj);
    }

    public void setIsFavorite(Boolean _isFav){
        this.isFavorite = _isFav;
    }


    public int getId() { return this.id; }
    public String getTitle(){
        return this.title;
    }
    public String getOriginalTitle(){
        return this.original_title;
    }
    public String getPosterPath(){
        return this.poster_path;
    }

    public String getReleaseDate(){
        return this.release_date;
    }
    public String getOverview(){
        return this.overview;
    }
    public int getVoteAverage(){
        return this.vote_average;
    }
    public List<TmdbTrailer> getTrailer_list(){
        return this.trailer_list;
    }
    public List<TmdbReviews> getReviews_list() { return this.reviews_list; }

    public boolean getIsFavorite(){
        return this.isFavorite;
    }

}

