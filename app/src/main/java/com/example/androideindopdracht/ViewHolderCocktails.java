package com.example.androideindopdracht;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderCocktails extends RecyclerView.ViewHolder
{
    ImageView imageView;
    TextView textView;

    public ViewHolderCocktails(@NonNull View itemView)
    {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView);
        textView = itemView.findViewById(R.id.textView);
    }
}