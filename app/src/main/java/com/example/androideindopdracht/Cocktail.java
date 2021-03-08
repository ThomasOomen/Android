package com.example.androideindopdracht;

import com.android.volley.toolbox.ImageLoader;

public class Cocktail
{
    String strDrink;
    String strCategory;
    String strInstructions;
    String imageSourceString;
    ImageLoader image;
    Cocktail(
            String strDrink,
            String strCategory,
            String strInstructions,
            String imageSourceString,
            ImageLoader imageContainer)
    {
        this.strDrink = strDrink;
        this.strCategory = strCategory;
        this.strInstructions = strInstructions;
        this.imageSourceString = imageSourceString;
        this.image = imageContainer;
    }
}