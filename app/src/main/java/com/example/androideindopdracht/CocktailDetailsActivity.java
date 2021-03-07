package com.example.androideindopdracht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

public class CocktailDetailsActivity extends AppCompatActivity {



    public void CreateDetails(Context context, Cocktail cocktailInfo){
        Intent intent = new Intent(context, CocktailDetails.class);
        intent.putExtra("COCKTAIL_NAME", cocktailInfo.strDrink);
        intent.putExtra("COCKTAIL_CATEGORY", cocktailInfo.strCategory);
        intent.putExtra("COCKTAIL_INSTRUCTION", cocktailInfo.strInstructions);
        intent.putExtra("COCKTAIL_IMAGE", cocktailInfo.imageSource);
        context.startActivity(intent);
    }
}