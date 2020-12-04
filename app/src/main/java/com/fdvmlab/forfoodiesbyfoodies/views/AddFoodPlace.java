package com.fdvmlab.forfoodiesbyfoodies.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fdvmlab.foodbyfoodiesforfoodies.R;
import com.fdvmlab.forfoodiesbyfoodies.MainActivity;

public class AddFoodPlace extends AppCompatActivity {

    // type: restaurant or street food stall
    private int foodPlaceType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_place);

        //get food place type
        try {
            foodPlaceType = (int) getIntent().getExtras().get(MainActivity.FOOD_PLACE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //save button
        // init views
        findViewById(R.id.btnAddFoodPlaceSaveFoodPlace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start activity to create new food place and pass type as extra

            }
        });
    }
}