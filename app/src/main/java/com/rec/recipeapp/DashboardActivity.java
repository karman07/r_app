package com.rec.recipeapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.rec.recipeapp.Feed.FeedFragment;
import com.rec.recipeapp.Search.SearchFragment;

public class DashboardActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        //Loading Default Fragment
        loadFragment(new FeedFragment());

        bottomNav.setOnItemSelectedListener(this);
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    // Using Bottom Navigation Bar to Change Fragment
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:
                fragment = new FeedFragment();
                break;

            case R.id.search:
                fragment = new SearchFragment();
                break;

            case R.id.add:

                break;

            case R.id.menu:

                break;

            case R.id.profile:

                break;
        }

        return loadFragment(fragment);

    }
}


