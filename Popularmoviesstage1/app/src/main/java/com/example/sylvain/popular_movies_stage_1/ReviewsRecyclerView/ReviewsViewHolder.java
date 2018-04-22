package com.example.sylvain.popular_movies_stage_1.ReviewsRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sylvain.popular_movies_stage_1.R;
import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbReviews;

/**
 * Created by sylvain on 15/04/2018.
 */

public class ReviewsViewHolder extends RecyclerView.ViewHolder {

    public TextView author;
    public TextView content;
    public TextView url;


    public ReviewsViewHolder(View itemView) {
        super(itemView);

        this.author = itemView.findViewById(R.id.tv_review_author_value);
        this.content = itemView.findViewById(R.id.tv_review_content_value);
        this.url = itemView.findViewById(R.id.tv_review_url_value);
    }

    public void bindData(TmdbReviews tmdbReview){
        this.author.setText(tmdbReview.getAuthor());
        this.content.setText(tmdbReview.getContent());
        this.url.setText(tmdbReview.getUrl());
    }
}
