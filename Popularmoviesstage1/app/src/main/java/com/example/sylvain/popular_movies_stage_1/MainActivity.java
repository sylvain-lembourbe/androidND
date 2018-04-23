package com.example.sylvain.popular_movies_stage_1;

import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.sylvain.popular_movies_stage_1.TmdbLocalDatabase.TmdbContract;
import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbJson;
import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbMovie;
import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbUrlBuilder;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity implements ItemClickListener, LoaderManager.LoaderCallbacks<Cursor>{

    private GridView gvMoviesPosters;
    private ArrayList<TmdbMovie> moviesList;
    private TextView tv_error;

    private final static int LOADER_ID=0;
    private final static String SAVE_INSTANCE_STATE_CALLBACKS_KEY = "callbacks";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        gvMoviesPosters = findViewById(R.id.gv_movies);
        tv_error = findViewById(R.id.tv_error);

        //1er chargemement null on charge une requete par defaut
        if(checkConnexion()) {
            if (savedInstanceState == null) {
                sendRequest(TmdbUrlBuilder.POPULAR_SEGMENT);
            } else { //on charge l'ancien contenu pour ne pas avoir à relancer la requete precedente
                moviesList = savedInstanceState.getParcelableArrayList(SAVE_INSTANCE_STATE_CALLBACKS_KEY);
                if (moviesList != null) {
                    populateGridView();
                }
            }
        }else{
            gvMoviesPosters.setVisibility(View.INVISIBLE);
            tv_error.setVisibility(View.VISIBLE);
        }
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
            case R.id.menu_favorites:
                showFavorites();
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(SAVE_INSTANCE_STATE_CALLBACKS_KEY, moviesList);
    }

    @Override
    protected void onResume(){
        super.onResume();

    }
    @Override
    protected void onRestart(){
        super.onRestart();
    }

    private void showFavorites(){
        if(checkConnexion()){
            if(getSupportLoaderManager().getLoader(LOADER_ID)==null) {
                getSupportLoaderManager().initLoader(LOADER_ID, null, this);
            }else{
                getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
            }
        }else{
            gvMoviesPosters.setVisibility(View.INVISIBLE);
            tv_error.setVisibility(View.VISIBLE);
        }
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

    private void populateGridView(){
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new android.support.v4.content.AsyncTaskLoader(this){

            Cursor cursor = null;

            @Override
            protected void onStartLoading(){
                if (cursor == null) {
                    forceLoad();
                }else{
                    super.deliverResult(cursor);
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    cursor = getContentResolver().query(TmdbContract.TmdbFavorite.CONTENT_URI, null, null, null, null);
                    return cursor;
                }catch(Exception ex){
                    Log.e("Loader", "Failed to load data");
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(moviesList!=null) {
            moviesList.clear();

            while (data.moveToNext()) {
                TmdbMovie m = new TmdbMovie();
                m.setId(Integer.parseInt(data.getString(data.getColumnIndex(TmdbContract.TmdbFavorite.ID_MOVIE))));
                m.setTitle(data.getString(data.getColumnIndex(TmdbContract.TmdbFavorite.TITLE_MOVIE)));
                m.setOriginalTitle(data.getString(data.getColumnIndex(TmdbContract.TmdbFavorite.ORIGINAL_TITLE)));
                m.setPosterPath(data.getString(data.getColumnIndex(TmdbContract.TmdbFavorite.POSTER_PATH)));
                m.setReleaseDate(data.getString(data.getColumnIndex(TmdbContract.TmdbFavorite.RELEASE_DATE)));
                m.setOverview(data.getString(data.getColumnIndex(TmdbContract.TmdbFavorite.OVERVIEW)));
                m.setVoteAverage(Integer.parseInt(data.getString(data.getColumnIndex(TmdbContract.TmdbFavorite.VOTE_AVERAGE))));
                moviesList.add(m);
            }
            populateGridView();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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
            if(moviesList != null) {
                moviesList.clear();
            }
            moviesList = TmdbJson.createMoviesList(getBaseContext(),result);
            populateGridView();
        }
    }
}
