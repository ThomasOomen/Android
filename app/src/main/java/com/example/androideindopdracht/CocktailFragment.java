package com.example.androideindopdracht;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CocktailFragment extends Fragment
{
    RecyclerView recyclerView;
    ArrayList cocktailData;
    CocktailAdapter cocktailAdapter;
    Context context;
    public CocktailFragment(Context context, ArrayList cocktailData)
    {
        this.cocktailData = cocktailData;
        this.context = context;
    }

    public CocktailFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_cocktail, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        cocktailAdapter = new CocktailAdapter(cocktailData, context);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManagerWrapper(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(cocktailAdapter);
        return view;
    }

    public void setCocktailData(ArrayList cocktailData){
        this.cocktailData = cocktailData;
        cocktailAdapter.setCocktailData(cocktailData);
    }
}