package com.example.androideindopdracht;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;

public class ViewHolderCocktails extends RecyclerView.ViewHolder
{
    NetworkImageView img;
    TextView textView;

    public ViewHolderCocktails(@NonNull View itemView)
    {
        super(itemView);

        img = (NetworkImageView)itemView.findViewById(R.id.network_img_view);
        textView = itemView.findViewById(R.id.textView);
    }
}