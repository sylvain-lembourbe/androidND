package com.example.sylvain.popular_movies_stage_1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbJson;
import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbMovie;
import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbUrlBuilder;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity implements ItemClickListener{

    private GridView gvMoviesPosters;
    private List<TmdbMovie> moviesList;
    private TextView tv_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gvMoviesPosters = findViewById(R.id.gv_movies);
        tv_error = findViewById(R.id.tv_error);

        sendRequest(TmdbUrlBuilder.POPULAR_SEGMENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_popular:
                sendRequest(TmdbUrlBuilder.POPULAR_SEGMENT);
                break;
            case R.id.menu_top_rated:
                sendRequest(TmdbUrlBuilder.TOP_RATED_SEGMENT);
                break;
        }
        return true;
    }

    private void sendRequest(String selectedSegment){
        URL url = TmdbUrlBuilder.createURL(selectedSegment);
        if(checkConnexion()) {
            gvMoviesPosters.setVisibility(View.VISIBLE);
            tv_error.setVisibility(View.INVISIBLE);
            new AsyncTmdbData().execute(url);
        }else{
            gvMoviesPosters.setVisibility(View.INVISIBLE);
            tv_error.setVisibility(View.VISIBLE);
        }
    }

    private void populateGridView(JSONObject result){
        moviesList = TmdbJson.createMoviesList(this,result);

        try{
                CustomArrayAdapter arrayAdapter = new CustomArrayAdapter(this,R.id.gv_movies,moviesList,this);
                gvMoviesPosters.setAdapter(arrayAdapter);
            }catch (Exception ex){
                Log.e("ERREUR IMAGEVIEW",ex.getMessage());
            }
    }

    @Override
    public void onItemClick(int itemIndex) {
        TmdbMovie currentMovie = moviesList.get(itemIndex);

        Intent detailIntent = new Intent(MainActivity.this,DetailActivity.class);
        detailIntent.putExtra("currentMovie", currentMovie);

        startActivity(detailIntent);
    }

    private boolean checkConnexion(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null;
    }

     class AsyncTmdbData extends AsyncTask<URL,Void,JSONObject>{

        @Override
        protected JSONObject doInBackground(URL... urls) {
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) urls[0].openConnection();
                InputStream in = httpURLConnection.getInputStream();
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");
                if(scanner.hasNext()){
                    return new JSONObject(scanner.next());
                }else{
                    return null;
                }
            }
            catch (JSONException | IOException ex){
                Log.e(getLocalClassName(),ex.getMessage());
                return null;
            } finally {
                if(httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

        }

        protected void onPostExecute(JSONObject result){
            populateGridView(result);
        }
    }
}
