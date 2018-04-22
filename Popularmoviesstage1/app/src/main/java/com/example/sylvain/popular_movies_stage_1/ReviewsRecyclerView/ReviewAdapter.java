package com.example.sylvain.popular_movies_stage_1.ReviewsRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sylvain.popular_movies_stage_1.R;
import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbReviews;

import java.util.List;

/**
 * Created by sylvain on 15/04/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewsViewHolder> {

    private int nbVues;
    private List<TmdbReviews>tmdbReviewsList;

    public ReviewAdapter(List<TmdbReviews>reviewsList){
        this.tmdbReviewsList = reviewsList;
        nbVues = reviewsList.size();
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recupération du layout qui servira de vue au rv
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.review_layout,parent,false);

        //passage de la vue au vh
        ReviewsViewHolder reviewsViewHolder = new ReviewsViewHolder(view);
        return reviewsViewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        holder.bindData(this.tmdbReviewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return nbVues;
    }
}
