package com.example.sylvain.popular_movies_stage_1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbMovie;
import com.example.sylvain.popular_movies_stage_1.tmdbClasses.TmdbUrlBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;


//1 implements onclicklistener
class CustomArrayAdapter extends ArrayAdapter<TmdbMovie> implements View.OnClickListener{

    private final List<TmdbMovie> moviesList;
    private final Context context;

    //2 reference à l'interface
    private final ItemClickListener itemClickListener;

    //3 modification du constructeur
    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull List<TmdbMovie> objects, ItemClickListener _itemClickListener) {
        super(context, resource, objects);
        this.moviesList = objects;
        this.context = context;
        //4 valoriser la reference
        this.itemClickListener = _itemClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        ImageView imageView = (ImageView) convertView;
        if (imageView==null){
            imageView = new ImageView(context);
        }
        String posterPath =  TmdbUrlBuilder.createImageUrl(moviesList.get(position).getPosterPath()).toString();
        Picasso.with(context).load(posterPath).into(imageView);

        //5 Brancher le listener du click
        imageView.setOnClickListener(this);
        //6 Stocker l'id de la vue pour retrouver dans la list des films
        imageView.setTag(position);
        imageView.setAdjustViewBounds(true);
        return imageView;
    }

    //6 implementer la fonction click
    @Override
    public void onClick(View view) {
        //index in movieList is the same as the view
        int itemIndex = Integer.valueOf(view.getTag().toString());

        itemClickListener.onItemClick(itemIndex);
    }
}
