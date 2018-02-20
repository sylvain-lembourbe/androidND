package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView tv_descriptionContent;
    private TextView tv_placeOfOrigin;
    private ListView lv_alsoKnownAs;
    private ListView lv_ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        tv_descriptionContent = findViewById(R.id.description_tv);
        tv_placeOfOrigin = findViewById(R.id.origin_tv);
        lv_alsoKnownAs = findViewById(R.id.also_known_lv);
        lv_ingredients = findViewById(R.id.ingredients_lv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }


        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        tv_descriptionContent.setText(sandwich.getDescription());
        tv_placeOfOrigin.setText(sandwich.getPlaceOfOrigin());

        //get AKAS
        ListAdapter akaAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sandwich.getAlsoKnownAs());
        lv_alsoKnownAs.setAdapter(akaAdapter);

        //get ingredients list
        ListAdapter ingredientsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sandwich.getIngredients());
        lv_ingredients.setAdapter(ingredientsAdapter);
    }
}
