package com.example.sylvain.popular_movies_stage_1.tmdbClasses;

/**
 * Created by sylvain on 12/04/2018.
 */

public class TmdbReviews {

    private String id;
    private String author;
    private String content;
    private String url;


    public void setId(String _id){
        this.id = _id;
    }
    public void setAuthor(String _author){
        this.author = _author;
    }
    public void setContent(String _content){
        this.content = _content;
    }
    public void setUrl(String _url){
        this.url = _url;
    }

    public String getId(){
        return this.id;
    }
    public String getAuthor(){
        return this.author;
    }
    public String getContent(){
        return this.content;
    }
    public String getUrl(){
        return this.url;
    }
}
