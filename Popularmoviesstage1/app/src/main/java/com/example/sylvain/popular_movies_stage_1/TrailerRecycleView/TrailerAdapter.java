package com.example.sylvain.popular_movies_stage_1.TrailerRecycleView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sylvain.popular_movies_stage_1.R;
import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbTrailer;

import java.util.List;

/**
 * Created by sylvain on 15/04/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailersViewHolder> {

    private int nbViews;
    //utilisé pour envoyer les données au vh
    private List<TmdbTrailer>mTrailerList;


    public TrailerAdapter(List<TmdbTrailer>trailerList){
        nbViews = trailerList.size();
        this.mTrailerList = trailerList;
    }

    @Override
    public TrailersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //recupération du layout qui servira de vue au rv
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.trailer_layout,parent,false);

        //passage de la vue au vh
        TrailersViewHolder trailersViewHolder = new TrailersViewHolder(view);
        return trailersViewHolder;
    }

    @Override
    public void onBindViewHolder(TrailersViewHolder holder, int position) {
        holder.bindData(mTrailerList.get(position));
    }

    @Override
    public int getItemCount() {
        return nbViews;
    }
}
