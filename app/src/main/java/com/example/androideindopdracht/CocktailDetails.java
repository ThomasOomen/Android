package com.example.androideindopdracht;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class CocktailDetails extends AppCompatActivity {
    private String name, category, instructions, imageSource;
    private int STORAGE_PERMISSION_CODE = 1;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_details);

        TextView textViewName = (TextView)findViewById(R.id.cocktail_name);
        TextView textViewCategory = (TextView)findViewById(R.id.cocktail_category);
        TextView textViewInstructions = (TextView)findViewById(R.id.cocktail_instructions);

        Button buttonRequest = findViewById(R.id.button);
        ToggleButton editTextField = findViewById(R.id.edit_tekst);
        editTextField.setText("Wijzig instructies");

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(CocktailDetails.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    getImage();
                } else {
                    requestStoragePermission();
                }
            }
        });

        editTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                boolean on = ((ToggleButton) v).isChecked();
                if (on) {
                    textViewInstructions.setEnabled(true);
                    editTextField.setText("Stop wijzig instructies");
                } else {
                    textViewInstructions.setEnabled(false);
                    editTextField.setText("Wijzig instructies");

                }
            }
        });

        this.name = getIntent().getStringExtra("COCKTAIL_NAME");
        this.category = getIntent().getStringExtra("COCKTAIL_CATEGORY");
        this.instructions = getIntent().getStringExtra("COCKTAIL_INSTRUCTION");
        this.imageSource = getIntent().getStringExtra("COCKTAIL_IMAGESOURCE");

        this.mQueue = Volley.newRequestQueue(this);
        getImageLoader(imageSource, mQueue);

        textViewName.setText(name);
        textViewCategory.setText(category);
        textViewInstructions.setText(instructions);
    }

    private void getImageLoader(String imageSource, RequestQueue mQueue) {
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
        setImage(imageSource, imageLoader);
    }

    private void setImage(String imageSource, ImageLoader imageLoader){
        NetworkImageView imageViewImage = findViewById(R.id.cocktail_image);
        imageViewImage.setImageUrl(imageSource, imageLoader);
    }

    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("Permission needed to gain access to gallery")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(
                                    CocktailDetails.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                getImage();
            } else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Pick Image"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 1){
            ImageView imageView = findViewById(R.id.imageView_details);

            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}