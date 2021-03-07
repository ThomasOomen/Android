package com.example.androideindopdracht;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    ArrayList cocktailData = new ArrayList<>();
    CocktailFragment fragment = new CocktailFragment(this, cocktailData);
    WebService webService = new WebService(fragment);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultDrink = pref.getString("ingredient", "DEFAULT");

        SearchView searchView = (SearchView) findViewById(R.id.searchView);

        if(defaultDrink != "DEFAULT"){
            searchView.setQuery(defaultDrink, true);
            webService.jsonParse(defaultDrink, mQueue);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                webService.jsonParse(query, mQueue);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main, fragment);
        fragmentTransaction.commit();
    }
}