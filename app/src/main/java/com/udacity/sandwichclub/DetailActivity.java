package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mSandwichIv;
    private TextView mAlsoKnownLabel;
    private TextView mAlsoKnownTv;
    private TextView mDescriptionLabel;
    private TextView mDescriptionTv;
    private TextView mOriginLabel;
    private TextView mOriginTv;
    private TextView mIngredientsLabel;
    private TextView mIngredientTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mIngredientTv = findViewById(R.id.ingredients_tv);
        mSandwichIv = findViewById(R.id.image_iv);
        mAlsoKnownLabel = findViewById(R.id.also_known_label);
        mAlsoKnownTv = findViewById(R.id.also_known_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mOriginLabel = findViewById(R.id.origin_label);
        mOriginTv = findViewById(R.id.origin_tv);

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

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Populate "image_iv"
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mSandwichIv);

        // Populate "also_known_tv"
        // if there is any information in "also known as"
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            // create a string from an array
            StringBuilder stringAlsoKnownAs = new StringBuilder();
            stringAlsoKnownAs.append(sandwich.getAlsoKnownAs().get(0));

            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                stringAlsoKnownAs.append(", ");
                stringAlsoKnownAs.append(sandwich.getAlsoKnownAs().get(i));
            }
            mAlsoKnownTv.setText(stringAlsoKnownAs.toString());
        }
        // if there is no information in "also known as"
        else {
            mAlsoKnownTv.setVisibility(View.GONE);
            mAlsoKnownLabel.setVisibility(View.GONE);
        }

        // Populate "description_tv"
        if (sandwich.getDescription().isEmpty()) {
            mDescriptionTv.setVisibility(View.GONE);
            mDescriptionLabel.setVisibility(View.GONE);
        } else {
            mDescriptionTv.setText(sandwich.getDescription());
            mDescriptionTv.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }

        // Populate "origin_tv"
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mOriginTv.setVisibility(View.GONE);
            mOriginLabel.setVisibility(View.GONE);
        } else {
            mOriginTv.setText(sandwich.getPlaceOfOrigin());
        }

        // Populate "ingredients_tv"
        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            // create a string from an array
            StringBuilder stringIngredients = new StringBuilder();
            stringIngredients.append("\u2713" + " ");
            stringIngredients.append(sandwich.getIngredients().get(0));

            for (int i = 1; i < sandwich.getIngredients().size(); i++) {
                stringIngredients.append("\n");
                stringIngredients.append("\u2713" + " ");
                stringIngredients.append(sandwich.getIngredients().get(i));
            }
            mIngredientTv.setText(stringIngredients.toString());
        }

    }
}
