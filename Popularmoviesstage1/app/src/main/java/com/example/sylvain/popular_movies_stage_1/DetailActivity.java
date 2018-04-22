package com.example.sylvain.popular_movies_stage_1;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sylvain.popular_movies_stage_1.ReviewsRecyclerView.ReviewAdapter;
import com.example.sylvain.popular_movies_stage_1.TmdbLocalDatabase.TmdbContract;
import com.example.sylvain.popular_movies_stage_1.TrailerRecycleView.TrailerAdapter;
import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbMovie;
import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbUrlBuilder;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import android.support.v4.app.LoaderManager;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    TmdbMovie currentMovie;

    TrailerAdapter trailerAdapter;
    private RecyclerView mRecyclerViewTrailers;

    ReviewAdapter reviewAdapter;
    private RecyclerView mRecyclerViewReviews;

    private ImageButton imageButton;

    private final static int LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageButton = findViewById(R.id.btn_favorite);

        ImageView img_poster = findViewById(R.id.imgv_poster);
        TextView tv_title = findViewById(R.id.tv_title_value);
        TextView tv_original_title = findViewById(R.id.tv_original_title_value);
        TextView tv_release_date = findViewById(R.id.tv_release_date_value);
        TextView tv_vote = findViewById(R.id.tv_vote_average_value);
        TextView tv_overview = findViewById(R.id.tv_overview_value);

        Intent movieIntent = getIntent();
        if(movieIntent.hasExtra("currentMovie")) {

            currentMovie = movieIntent.getExtras().getParcelable("currentMovie");

            if(currentMovie.getPosterPath()!=null) {
                String imgURL = TmdbUrlBuilder.createImageUrl(currentMovie.getPosterPath()).toString();
                Picasso.with(this).load(imgURL).into(img_poster);
            }
            tv_title.setText(currentMovie.getTitle());
            tv_original_title.setText(currentMovie.getOriginalTitle());
            tv_release_date.setText(currentMovie.getReleaseDate());
            tv_vote.setText(String.valueOf(currentMovie.getVoteAverage()));
            tv_overview.setText(currentMovie.getOverview());

        }

        //appel de la liste des trailers
        URL urlTrailers = TmdbUrlBuilder.createURL(TmdbUrlBuilder.VIDEOS_SEGMENT,currentMovie.getId());
        //appel de la liste des reviews
        URL urlReviews = TmdbUrlBuilder.createURL(TmdbUrlBuilder.REVIEWS_SEGMENT,currentMovie.getId());
        //lancement de la tache async
        new AsyncTmdbTrailers().execute(urlTrailers,urlReviews);

        //création du layout manager
        mRecyclerViewTrailers = findViewById(R.id.rv_trailers);
        LinearLayoutManager linearLayoutManagerTrailers = new LinearLayoutManager(this);
        mRecyclerViewTrailers.setLayoutManager(linearLayoutManagerTrailers);
        mRecyclerViewTrailers.setHasFixedSize(false);

        mRecyclerViewReviews = findViewById(R.id.rv_reviews);
        LinearLayoutManager linearLayoutManagerReviews = new LinearLayoutManager(this);
        mRecyclerViewReviews.setLayoutManager(linearLayoutManagerReviews);
        mRecyclerViewReviews.setHasFixedSize(false);

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
    }

    public void playTrailer(View view){
        int index = mRecyclerViewTrailers.getChildLayoutPosition((View)view.getParent());

        Intent videoIntent = new Intent(Intent.ACTION_VIEW, currentMovie.getTrailer_list().get(index).getTrailerUri());
        if(videoIntent.resolveActivity(getPackageManager())!=null) {
            startActivity(videoIntent);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADER_ID,null,this);
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
                    cursor = getContentResolver().query(ContentUris.withAppendedId(TmdbContract.TmdbFavorite.CONTENT_URI, currentMovie.getId()), null, null, null, null);
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
        currentMovie.setIsFavorite(data.getCount()>0);
        if(currentMovie.getIsFavorite()){
            imageButton.setImageResource(R.drawable.ic_favorite_black_24dp);
        }else{
            imageButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    //tache async pour récupération des trailers et des reviews
    class AsyncTmdbTrailers extends AsyncTask<URL,Void,JSONObject[]> {
        @Override
        protected JSONObject[] doInBackground(URL... urls) {

            JSONObject[] jTableau = new JSONObject[2];
            for(int i=0;i<urls.length;i++){
               jTableau[i] = scanData(urls[i]);
            }
            return jTableau;

        }
        protected void onPostExecute(JSONObject[] result){

            currentMovie.setTrailer_list(result[0]);
            currentMovie.setReviews_list(result[1]);

            trailerAdapter = new TrailerAdapter(currentMovie.getTrailer_list());
            mRecyclerViewTrailers.setAdapter(trailerAdapter);

            reviewAdapter = new ReviewAdapter(currentMovie.getReviews_list());
            mRecyclerViewReviews.setAdapter(reviewAdapter);
        }

        private JSONObject scanData(URL url){

            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) {
                    return new JSONObject(scanner.next());
                } else {
                    return null;
                }
            }catch (JSONException | IOException ex){
                Log.e(getLocalClassName(), ex.getMessage());
                return null;
            }finally {
                if(httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
            }
        }
    }

    //add or remove favorite
    public void addToFavorite(View view){
        if(!currentMovie.getIsFavorite()){
            //add fav
            ContentValues cv = new ContentValues();
            cv.put(TmdbContract.TmdbFavorite.ID_MOVIE, currentMovie.getId());
            cv.put(TmdbContract.TmdbFavorite.TITLE_MOVIE, currentMovie.getTitle());
            cv.put(TmdbContract.TmdbFavorite.ORIGINAL_TITLE, currentMovie.getOriginalTitle());
            cv.put(TmdbContract.TmdbFavorite.POSTER_PATH, currentMovie.getPosterPath());
            cv.put(TmdbContract.TmdbFavorite.RELEASE_DATE, currentMovie.getReleaseDate());
            cv.put(TmdbContract.TmdbFavorite.OVERVIEW, currentMovie.getOverview());
            cv.put(TmdbContract.TmdbFavorite.VOTE_AVERAGE, currentMovie.getVoteAverage());

            Uri uri = getContentResolver().insert(TmdbContract.TmdbFavorite.CONTENT_URI, cv);
            if (uri != null) {
                Toast.makeText(this, "Movie added to favorites", Toast.LENGTH_LONG).show();
                showFavIcon();
            }
        }
        else {
            //remove fav
            Uri deleteUri = TmdbContract.TmdbFavorite.CONTENT_URI.buildUpon().appendPath(Integer.toString(currentMovie.getId())).build();
            int rowDeleted = getContentResolver().delete(deleteUri, null, null);
            if (rowDeleted > 0) {
                Toast.makeText(this, "Movie removed from favorites", Toast.LENGTH_LONG).show();
                showFavIcon();
            }
        }
    }

    public void showFavIcon(){
        getSupportLoaderManager().restartLoader(LOADER_ID,null,this);
    }

}
