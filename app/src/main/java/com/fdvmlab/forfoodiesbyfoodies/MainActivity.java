package com.fdvmlab.forfoodiesbyfoodies;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.fdvmlab.foodbyfoodiesforfoodies.R;
import com.fdvmlab.forfoodiesbyfoodies.models.User;
import com.fdvmlab.forfoodiesbyfoodies.models.UserRole;
import com.fdvmlab.forfoodiesbyfoodies.views.Login;
import com.fdvmlab.forfoodiesbyfoodies.views.ReviewsList;
import com.fdvmlab.forfoodiesbyfoodies.views.SignUp;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    //Auth
    FirebaseAuth mAuth = null;
    FirebaseUser mUser = null;
    //Drawer toggle
    ActionBarDrawerToggle drawerToggle = null;
    DrawerLayout drawerLayout = null;
    //Current user
    private User mCurrentUser = new User("Nan", "manil@mail.com", "123456", UserRole.ADMIN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();


        //init drawer layout
        drawerLayout = findViewById(R.id.drawerLayout);


        //Initialize the toggle button
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);

        //add listener and sync state
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //show the toggle icon on action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set navigation item select listener
        NavigationView navigationView = findViewById(R.id.drawerNavigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationViewListener());

        //Nav header
        View navHeaderView = navigationView.getHeaderView(0);

        TextView navHFN = navHeaderView.findViewById(R.id.tvNavDrawerHeaderFullName);
        //navHFN.setText(mAuth.getCurrentUser().getDisplayName());

        TextView navHEA = navHeaderView.findViewById(R.id.tvNavDrawerHeaderEmailAddress);
        //navHEA.setText(mAuth.getCurrentUser().getEmail());

        ImageView navHPP = navHeaderView.findViewById(R.id.imgNavDrawerUserProfilePicture);
        //Picasso.get().load(mAuth.getCurrentUser().getPhotoUrl()).fit().into(navHPP);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Fetch the user's details from database


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Add select listener to toggle menu icon
        super.onOptionsItemSelected(item);
        return drawerToggle.onOptionsItemSelected(item);
    }

    /**
     * Listener for navigation view items
     */
    private class NavigationViewListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.menu_item_reviews:
                    // see users reviews
                    drawerLayout.closeDrawer(Gravity.LEFT);

                    startActivity(new Intent(getApplicationContext(), ReviewsList.class));
                    break;

                case R.id.menu_item_add_new_user:
                    // create new user
                    drawerLayout.closeDrawer(Gravity.LEFT);

                    startActivity(new Intent(getApplicationContext(), SignUp.class)
                            .putExtra("USER_ROLE", UserRole.CRITIC)
                            .putExtra("ADMIN", (Serializable) mCurrentUser));
                    break;

                case R.id.menu_item_logout:
                    // sign up and finish terminate activity

                    drawerLayout.closeDrawer(Gravity.LEFT);

                    if (mUser != null) mAuth.signOut();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                    break;

                default:
                    return false;
            }
            return true;
        }
    }
}