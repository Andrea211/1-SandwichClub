package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // A list of JSON keys
    private final static String KEY_NAME = "name";
    private final static String KEY_MAIN_NAME = "mainName";
    private final static String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String KEY_DESCRIPTION = "description";
    private final static String KEY_IMAGE = "image";
    private final static String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        try {
            // Initialize JSONObject from JSON string
            JSONObject sandwichJsonObject = new JSONObject(json);

            // Enter the main "name" object
            JSONObject sandwichName = sandwichJsonObject.getJSONObject(KEY_NAME);

            // Get the "main name" String from "name"
            String sandwichMainName = sandwichName.getString(KEY_MAIN_NAME);

            // Get the "also known as" Array from "name"
            JSONArray arrayAlsoKnownAs = sandwichName.getJSONArray(KEY_ALSO_KNOWN_AS);
            List<String> sandwichAlsoKnownAs = convertToList(arrayAlsoKnownAs);

            // Get the "place of origin" String of a sandwich from JSON
            String sandwichPlaceOfOrigin = sandwichJsonObject.getString(KEY_PLACE_OF_ORIGIN);

            // Get the "description" String of a sandwich from JSON
            String sandwichDescription = sandwichJsonObject.getString(KEY_DESCRIPTION);

            // Get the "image" String of a sandwich from JSON
            String sandwichImage = sandwichJsonObject.getString(KEY_IMAGE);

            // Get the "ingredients" Array of a sandwich from JSON
            JSONArray arrayIngredients = sandwichJsonObject.getJSONArray(KEY_INGREDIENTS);
            List<String> sandwichIngredients = convertToList(arrayIngredients);

            return new Sandwich(sandwichMainName, sandwichAlsoKnownAs, sandwichPlaceOfOrigin, sandwichDescription, sandwichImage, sandwichIngredients);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> convertToList(JSONArray jsonArray) throws JSONException {
        List<String> listOfStrings = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            listOfStrings.add(jsonArray.getString(i));
        }

        return listOfStrings;
    }
}
