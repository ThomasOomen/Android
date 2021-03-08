package com.example.androideindopdracht;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WebService<mQueue> {

    private CocktailFragment fragment;
    private ArrayList<Cocktail> cocktailData = new ArrayList<>();
    private Context context;

    public WebService(CocktailFragment fragment, Context context) {
        this.fragment = fragment;
        this.context = context;
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
                        String imageSourceString = drinks.getString("strDrinkThumb");

                        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
                            private final LruCache<String, Bitmap> cache = new LruCache<>(20);
                            @Override
                            public Bitmap getBitmap(String url) {
                                return cache.get(url);
                            }

                            @Override
                            public void putBitmap(String url, Bitmap bitmap) {
                                cache.put(url, bitmap);
                            }
                        });

                        Cocktail cocktail = new Cocktail(
                                strDrink,
                                strCategory,
                                strInstructions,
                                imageSourceString,
                                imageLoader
                        );
                        cocktailData.add(cocktail);
                    }
                } catch (JSONException e) {
                    new AlertDialog.Builder(context)
                            .setTitle("Geen resultaat")
                            .setMessage("De ingevulde bestaat niet")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
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
