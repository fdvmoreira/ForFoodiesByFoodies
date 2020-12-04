package com.fdvmlab.forfoodiesbyfoodies.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fdvmlab.foodbyfoodiesforfoodies.R;
import com.fdvmlab.forfoodiesbyfoodies.MainActivity;

public class FoodPlaceList extends AppCompatActivity {


    private int foodPlaceType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_place_list);

        //get food place
        try {
            foodPlaceType = (int) getIntent().getExtras().get(MainActivity.FOOD_PLACE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // init views
        findViewById(R.id.fbtnActivityFoodPlaceListAddNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddFoodPlace.class).putExtra("FOOD_PLACE_TYPE", foodPlaceType));
            }
        });
    }
}