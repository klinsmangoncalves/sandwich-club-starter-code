package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final ImageView ingredientsIv = findViewById(R.id.image_iv);

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
                .placeholder(R.drawable.progress_animation)
                .into(ingredientsIv, new Callback() {
                    @Override
                    public void onSuccess() {
                        int imageHeight = (int) getResources().getDimension(R.dimen.sandwich_image);
                        ingredientsIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ingredientsIv.getLayoutParams().height = imageHeight;
                    }

                    @Override
                    public void onError() {
                        ingredientsIv.setImageResource(R.drawable.onloaderror);
                    }
                });

        setTitle(sandwich.getMainName());
        onStop();
        onRestart();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView placeOriginTextView = findViewById(R.id.origin_tv);
        TextView alsoKnownTextView = findViewById(R.id.also_known_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);

        placeOriginTextView.setText(sandwich.getPlaceOfOrigin());

        StringBuffer sb = new StringBuffer();
        for (String name : sandwich.getAlsoKnownAs()) {
            sb.append(name + ", ");
        }
        alsoKnownTextView.setText(sb.length() <= 0 ? "" : sb.substring(0, sb.length() - 2));

        descriptionTextView.setText(sandwich.getDescription());

        sb = new StringBuffer();
        for (String name : sandwich.getIngredients()) {
            sb.append(name + "\n");
        }

        ingredientsTextView.setText(sb.toString());
    }
}
