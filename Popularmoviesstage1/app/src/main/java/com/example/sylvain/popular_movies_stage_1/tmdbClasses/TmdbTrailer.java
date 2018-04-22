package com.example.sylvain.popular_movies_stage_1.tmdbClasses;

import android.net.Uri;

import org.json.JSONObject;

/**
 * Created by sylvain on 10/04/2018.
 */

public class TmdbTrailer {

    private final static String BASE_URI_YOUTUBE = "http://www.youtube.com/watch?v=";

    private String id;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private String size;
    private String type;

    public TmdbTrailer(){

    }

    public void setId(String _id){
        this.id = _id;
    }
    public void setIso639(String _iso){
        this.iso_639_1 = _iso;
    }
    public void setIso3166(String _iso){
        this.iso_3166_1 = _iso;
    }
    public void setKey(String _key){
        this.key = _key;
    }
    public void setName(String _name){
        this.name = _name;
    }
    public void setSite(String _site){
        this.site = _site;
    }
    public void setSize(String _size){
        this.size = _size;
    }
    public void setType(String _type){
        this.type = _type;
    }

    public String getId(){
        return this.id;
    }
    public String getIso639(){
        return this.iso_639_1;
    }
    public String getIso3166(){
        return this.iso_3166_1;
    }
    public String getKey(){
        return this.key;
    }
    public String getName(){
        return this.name;
    }
    public String getSite(){
        return this.site;
    }
    public String getSize(){
        return this.size;
    }
    public String getType(){
        return this.type;
    }

    public Uri getTrailerUri(){
        return Uri.parse(BASE_URI_YOUTUBE + key);
    }
}
