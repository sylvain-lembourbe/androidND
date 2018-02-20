package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich currentSandwich = new Sandwich();
        //create jsonObject
        try {
            JSONObject sandwichJson = new JSONObject(json);

            //populate SandwichObject
            JSONObject sandwichNameJson = sandwichJson.getJSONObject("name");
            currentSandwich.setMainName(sandwichNameJson.getString("mainName"));

            List<String> akaList = new ArrayList<String>();
            JSONArray akaArray = sandwichNameJson.getJSONArray("alsoKnownAs");
            for(int i=0;i<akaArray.length();i++){
                akaList.add(akaArray.getString(i));
            }
            currentSandwich.setAlsoKnownAs(akaList);

            currentSandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));
            currentSandwich.setDescription(sandwichJson.getString("description"));
            currentSandwich.setImage(sandwichJson.getString("image"));

            List<String> ingredientsList = new ArrayList<String>();
            JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
            for(int i=0;i<ingredientsArray.length();i++){
                ingredientsList.add(ingredientsArray.getString(i));
            }
            currentSandwich.setIngredients(ingredientsList);



        }catch(JSONException e){
            Log.e(Sandwich.class.getName(),"Error while parsing JSON");
        }

        return currentSandwich;

    }
}
