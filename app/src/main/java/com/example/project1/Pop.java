/**
 Author : Mohamed Conde
 Due Date: 1/24/2020
 Description: A This activitity pops a layout or window to show the user more details



 **/


package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Pop extends AppCompatActivity {
    TextView imagedetails;
    ImageView theImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        imagedetails = findViewById(R.id.popTextView);
        theImage = findViewById(R.id.popImage);
        Intent imageInfo = getIntent();
        imagedetails.setText(imageInfo.getStringExtra("name"));
        String s = imageInfo.getStringExtra("name");
        Toast toast= Toast. makeText(getApplicationContext(),"image detail retrieved",Toast. LENGTH_SHORT);
        toast.show();
        theImage.setImageResource(imageInfo.getIntExtra("image",0));

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.8));
    }
}
