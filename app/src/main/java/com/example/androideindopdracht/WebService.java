package com.example.androideindopdracht;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WebService<mQueue> {

    CocktailFragment fragment;
    ArrayList<Cocktail> cocktailData = new ArrayList<>();

    public WebService(CocktailFragment fragment) {
        this.fragment = fragment;
    }

    public void jsonParse(String query, RequestQueue mQueue) {
        String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + query;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("drinks");
                    cocktailData.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject drinks = jsonArray.getJSONObject(i);

                        String strDrink = drinks.getString("strDrink");
                        String strCategory = drinks.getString("strCategory");
                        String strInstructions = drinks.getString("strInstructions");
//                        String imageSource = drinks.getString("strDrinkThumb");

                        Cocktail cocktail = new Cocktail(strDrink,
                                strCategory,
                                strInstructions
//                        imageSource
                        );
                        cocktailData.add(cocktail);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                fragment.setCocktailData(cocktailData);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}
