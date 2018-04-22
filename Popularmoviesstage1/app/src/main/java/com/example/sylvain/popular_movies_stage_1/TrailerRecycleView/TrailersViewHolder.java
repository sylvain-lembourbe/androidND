package com.example.sylvain.popular_movies_stage_1.TrailerRecycleView;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sylvain.popular_movies_stage_1.R;
import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbTrailer;

import java.util.List;

/**
 * Created by sylvain on 15/04/2018.
 */

public class TrailersViewHolder extends RecyclerView.ViewHolder{

    public TextView tv_name;
    public TextView tv_language;
    public TextView tv_type;
    public TextView tv_size;
    public TextView tv_site;

    public TrailersViewHolder(View itemView) {
        super(itemView);

        tv_name = itemView.findViewById(R.id.tv_trailer_name_value);
        tv_language = itemView.findViewById(R.id.tv_trailer_language_value);
        tv_type = itemView.findViewById(R.id.tv_trailer_type_value);
        tv_site = itemView.findViewById(R.id.tv_trailer_site_value);
        tv_size = itemView.findViewById(R.id.tv_trailer_size_value);
    }

    public void bindData(TmdbTrailer tmdbTrailer){
        tv_name.setText(tmdbTrailer.getName());
        tv_site.setText(tmdbTrailer.getSite());
        tv_type.setText(tmdbTrailer.getType());
        tv_size.setText(tmdbTrailer.getSize());
        tv_language.setText(tmdbTrailer.getIso639() + "-" + tmdbTrailer.getIso3166());

    }

}
