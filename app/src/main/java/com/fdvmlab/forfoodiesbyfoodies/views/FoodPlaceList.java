package com.fdvmlab.forfoodiesbyfoodies.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fdvmlab.foodbyfoodiesforfoodies.R;
import com.fdvmlab.forfoodiesbyfoodies.MainActivity;
import com.fdvmlab.forfoodiesbyfoodies.models.FoodPlace;
import com.fdvmlab.forfoodiesbyfoodies.models.FoodPlaceAdapter;

import java.util.ArrayList;
import java.util.List;

public class FoodPlaceList extends AppCompatActivity {

    // type: restaurant or street food stall
    private int foodPlaceType = 0;

    // list of food place
    private List<FoodPlace> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_place_list);

        //get food place type
        try {
            foodPlaceType = (int) getIntent().getExtras().get(MainActivity.FOOD_PLACE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // init button and add listener
        findViewById(R.id.fbtnActivityFoodPlaceListAddNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start activity to create new food place and pass type as extra
                startActivity(new Intent(getApplicationContext(), AddFoodPlace.class).putExtra("FOOD_PLACE_TYPE", foodPlaceType));
            }
        });

        // Add dummy data to list
        /**list.add(new Restaurant("Zayani",
         new Address(395,"Simpson Road","UB7 9HU","Strat"),
         "789 45789 7845",
         "",
         "45cvDSCvftt665dsEEF456adGFGH98sc"));*/

        //set adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvFoodPlaceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FoodPlaceAdapter(list));
    }
}