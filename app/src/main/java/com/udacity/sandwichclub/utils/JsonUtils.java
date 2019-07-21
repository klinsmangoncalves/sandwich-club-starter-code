package com.udacity.sandwichclub.utils;


import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        /* Sandwich name information, contains name and how it is also known */
        final String FIELD_NAME = "name";
        final String FIELD_MAIN_NAME = "mainName";
        final String FIELD_ALSO_KNOWN_AS = "alsoKnownAs";

        final String FIELD_PLACE_OF_ORIGIN = "placeOfOrigin";

        /* Sandwich description */
        final String FIELD_DESCRIPTION = "description";

        /* Image link */
        final String FIELD_IMAGE = "image";

        /* Ingredients names */
        final String FIELD_INGREDIENTS = "ingredients";

        String mainName;
        List<String> alsoKnownAs = new ArrayList<String>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<String>();;

        try {
            /* Reads the json string and adds it to a JSONObject */
            JSONObject sandwichJson = new JSONObject(json);

            /* Gets the json name */
            JSONObject sandwichName = sandwichJson.getJSONObject(FIELD_NAME);
            mainName = sandwichName.getString(FIELD_MAIN_NAME);
            JSONArray alsoKnowJsonArray = sandwichName.getJSONArray(FIELD_ALSO_KNOWN_AS);

            /* Iterate through array to add the also known names */
            for (int i = 0; i < alsoKnowJsonArray.length(); i++){
               alsoKnownAs.add(alsoKnowJsonArray.getString(i));
            }

            /* Gets  other fields which all types are string*/
            placeOfOrigin = sandwichJson.getString(FIELD_PLACE_OF_ORIGIN);
            description = sandwichJson.getString(FIELD_DESCRIPTION);
            image = sandwichJson.getString(FIELD_IMAGE);

            /* Gets and iterate through array to add the ingredients */
            JSONArray ingredientsJsonArray = sandwichJson.getJSONArray(FIELD_INGREDIENTS);
            for (int i = 0; i < ingredientsJsonArray.length(); i++){
                ingredients.add(ingredientsJsonArray.getString(i));
            }

            /* Create a new object and return it */
            Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
