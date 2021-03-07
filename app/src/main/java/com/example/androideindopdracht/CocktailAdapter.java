package com.example.androideindopdracht;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CocktailAdapter extends RecyclerView.Adapter<ViewHolderCocktails> {
    ArrayList cocktailData;
    Context context;

    public CocktailAdapter(ArrayList cocktailData , Context context) {
        this.cocktailData = cocktailData;
        this.context = context;
    }

    public void setCocktailData(ArrayList cocktailData){
        this.cocktailData = cocktailData;
        notifyItemRangeChanged(0, cocktailData.size());
    }

    @NonNull
    @Override
    public ViewHolderCocktails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_display, parent, false);
        return new ViewHolderCocktails(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCocktails holder, int position) {
        Cocktail cocktail = (Cocktail) cocktailData.get(position);
        holder.textView.setText(cocktail.strDrink);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CocktailDetailsActivity cocktailDetailsActivity = new CocktailDetailsActivity();
                Cocktail cocktailInfo = (Cocktail) cocktailData.get(position);
                cocktailDetailsActivity.CreateDetails(context, cocktailInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cocktailData.size();
    }
}