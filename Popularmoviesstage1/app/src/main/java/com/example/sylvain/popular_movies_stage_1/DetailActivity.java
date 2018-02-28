package com.example.sylvain.popular_movies_stage_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbMovie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView img_poster = findViewById(R.id.imgv_poster);
        TextView tv_title = findViewById(R.id.tv_title_value);
        TextView tv_original_title = findViewById(R.id.tv_original_title_value);
        TextView tv_release_date = findViewById(R.id.tv_release_date_value);
        TextView tv_vote = findViewById(R.id.tv_vote_average_value);
        TextView tv_overview = findViewById(R.id.tv_overview_value);

        Intent movieIntent = getIntent();
        if(movieIntent.hasExtra("currentMovie")) {
            TmdbMovie currentMovie = movieIntent.getExtras().getParcelable("currentMovie");

            Picasso.with(this).load(currentMovie.getPosterPath()).into(img_poster);

            tv_title.setText(currentMovie.getTitle());
            tv_original_title.setText(currentMovie.getOriginalTitle());
            tv_release_date.setText(currentMovie.getReleaseDate());
            tv_vote.setText(String.valueOf(currentMovie.getVoteAverage()));
            tv_overview.setText(currentMovie.getOverview());
        }

    }

}
